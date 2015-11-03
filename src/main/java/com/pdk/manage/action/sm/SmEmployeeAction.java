package com.pdk.manage.action.sm;

import com.github.pagehelper.PageInfo;
import com.pdk.manage.common.wapper.sm.SmEmployeeDataTableQueryArgWapper;
import com.pdk.manage.common.wapper.sm.SmEmployeeRefDataTableQueryArgWapper;
import com.pdk.manage.dto.sm.EmployeeJson;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.sm.Employee;
import com.pdk.manage.service.sm.EmployeeService;
import com.pdk.manage.util.DBConst;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

/**
 * Created by hubo on 2015/8/10
 */
@Controller
@RequestMapping("/sm")
public class SmEmployeeAction {
    private static final Logger log = LoggerFactory.getLogger(SmEmployeeAction.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/password/edit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> modifyPassword(String password, String newPassword, String confirmPassword) {

        Map<String, Object> result = new HashMap<>();

        Employee e = (Employee) session.getAttribute("user");

        boolean checked = true;

        String errorMsg = "";

        if (!DigestUtils.md5Hex(password).equals(e.getPassword())) {
            errorMsg = "当前密码输入错误!";
            checked = false;
        }

        if (!newPassword.equals(confirmPassword)) {
            errorMsg = "两次密码输入不一致!";
            checked = false;
        }

        if (checked) {
            employeeService.modifyPassword(e.getId(), DigestUtils.md5Hex(newPassword));
        }

        result.put("success", checked);
        result.put("errorMsg", errorMsg);

        return result;

    }

    @RequestMapping("/sm_employee")
    public String index() {
        return "sm/sm_employee";
    }

    @RequestMapping("/sm_employee/on_add")
    public String onAdd() {
        return "sm/sm_employee_add";
    }

    @ModelAttribute
    public void getEmployee(@RequestParam(value = "id", required = false) String id, Map<String, Object> map) {
        if (StringUtils.isNotEmpty(id)) {
            map.put("employee", employeeService.get(id));
        }
    }

    @RequestMapping(value = "/sm_employee/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> get(@PathVariable("id") String id) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", "success");

        EmployeeJson json = new EmployeeJson(1, employeeService.getEmployeeById(id));
        result.put("data", json);
        return result;
    }

    @RequestMapping(value = "/sm_employee", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@Valid Employee employee, Errors errors, Map<String, Object> map) {

        Map<String, Object> result = new HashMap<>();

        if (errors.getErrorCount() > 0) {
            result.put("result", "error");
            for (FieldError err : errors.getFieldErrors()) {
                map.put("err_" + err.getField(), err.getDefaultMessage());
            }
            return result;
        }

        if (isCodeRepeat(employee)) {
            result.put("result", "error");
            result.put("errorMsg", "人员编码重复，请修改后保存!");
            return result;
        }

        try {
            employee.setPassword(DigestUtils.md5Hex(employee.getPassword()));
            employeeService.save(employee);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
        }

        return result;
    }

    @RequestMapping(value = "/sm_employee", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> update(@Valid Employee employee, Errors errors, Map<String, Object> map) {

        Map<String, Object> result = new HashMap<>();

        if (errors.getErrorCount() > 0) {
            result.put("result", "error");
            for (FieldError err : errors.getFieldErrors()) {
                map.put("err_" + err.getField(), err.getDefaultMessage());
            }
            return result;
        }

        if (isCodeRepeat(employee)) {
            result.put("result", "error");
            result.put("errorMsg", "人员编码重复，请修改后保存!");
            return result;
        }

        try {
            updatePassword(employee);
            employeeService.update(employee);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        }

        return result;
    }

    private void updatePassword(Employee employee) {
        Employee dbEmployee = employeeService.get(employee.getId());
        if (!employee.getPassword().equals(dbEmployee.getPassword())) { // 如果密码有修改则重新设置密码
            employee.setPassword(DigestUtils.md5Hex(employee.getPassword()));
        }
    }

    @RequestMapping(value = "/sm_employee", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> batchDelete(@RequestParam("ids") String[] ids, @RequestParam(value = "ts", required = false) Long[] ts) {

        Map<String, Object> result = new HashMap<>();

        List<Employee> employeeList = new ArrayList<>();

        for (int i = 0; i < ids.length; i++) {
            Employee employee = new Employee();
            employee.setId(ids[i]);
            if (ts != null && ts[i] != null) {
                employee.setTs(new Date(ts[i]));
            }
            employeeList.add(employee);
        }

        try {
            employeeService.delete(employeeList);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/sm_employee/status/{status}", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> updateStatus(@PathVariable Short status, @RequestParam("ids") String[] ids, @RequestParam(value = "ts", required = false) Long[] ts) {

        Map<String, Object> result = new HashMap<>();

        List<Employee> employeeList = new ArrayList<>();

        for (int i = 0; i < ids.length; i++) {
            Employee employee = new Employee();
            employee.setId(ids[i]);
            if (ts != null && ts[i] != null) {
                employee.setTs(new Date(ts[i]));
            }
            employeeList.add(employee);
        }

        try {
            if (status == DBConst.STATUS_ENABLE) {
                employeeService.enable(employeeList);
            } else {
                employeeService.disable(employeeList);
            }
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        }

        return result;
    }

    @RequestMapping("/sm_employee/table_data")
     @ResponseBody
     public Map<String, Object> list(SmEmployeeDataTableQueryArgWapper arg) {
        Map<String, Object> result = new HashMap<>();
        PageInfo<Employee> pageInfo = null;

        String[] roleIds = null;
        if (!StringUtils.isEmpty(arg.getQryRoleIds())) {
            roleIds = arg.getQryRoleIds().split(";");
        }

        try {
            pageInfo = employeeService.mySelectLikePage(arg.getSearchText(), arg.getPageNum(), arg.getLength(), arg.getOrderStr(), arg.getQryCode(), arg.getQryName(), roleIds, arg.getQryOrgId());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        List<EmployeeJson> data = new ArrayList<>();

        int index = 1;
        for (Employee employee : pageInfo.getList()) {
            data.add(new EmployeeJson(index, employee));
            index++;
        }

        result.put("draw", arg.getDraw());
        result.put("recordsTotal", pageInfo.getTotal());
        result.put("recordsFiltered", pageInfo.getTotal());
        result.put("data", data);

        return result;
    }

    @RequestMapping("/sm_employee/table_data/position/{positionId}")
    @ResponseBody
    public Map<String, Object> refList(SmEmployeeRefDataTableQueryArgWapper arg, @PathVariable String positionId) {
        Map<String, Object> result = new HashMap<>();
        PageInfo<Employee> pageInfo = null;

        try {
            pageInfo = employeeService.mySelectLikePageByPositionId(arg.getSearchText(), arg.getPageNum(), arg.getLength(), arg.getOrderStr(), positionId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        List<EmployeeJson> data = new ArrayList<>();

        int index = 1;
        for (Employee employee : pageInfo.getList()) {
            data.add(new EmployeeJson(index, employee));
            index++;
        }

        result.put("draw", arg.getDraw());
        result.put("recordsTotal", pageInfo.getTotal());
        result.put("recordsFiltered", pageInfo.getTotal());
        result.put("data", data);

        return result;
    }

    private boolean isCodeRepeat(Employee employee) {
        return employeeService.isCodeRepeat(employee);
    }
}