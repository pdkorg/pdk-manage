package com.pdk.manage.action.sm;

import com.pdk.manage.dto.sm.EmployeeJson;
import com.pdk.manage.dto.sm.FuncDto;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.sm.Employee;
import com.pdk.manage.service.sm.EmployeeService;
import com.pdk.manage.service.sm.SmEmployeeReviewService;
import com.pdk.manage.util.CommonConst;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuhm on 2015/9/2
 */
@Controller
@RequestMapping("/sm")
public class SmEmployeeDetailAction {
    private static final Logger log = LoggerFactory.getLogger(SmEmployeeDetailAction.class);
    private static final String ACTION_TYPE_ADD = "ADD";

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private SmEmployeeReviewService employeeReviewService;

    @Autowired
    private HttpSession session;

    @Autowired
    private String adminRoleId;

    @ModelAttribute
    public void getEmployee(@RequestParam(value = "id", required = false) String id, Map<String, Object> map) {
        if (StringUtils.isNotEmpty(id)) {
            map.put("employee", employeeService.get(id));
        }
    }

    @RequestMapping("/sm_employee_detail/{id}")
    public String EmployeeDetail(@PathVariable("id") String id, Map<String, Object> map, boolean canEdit) {
        Employee employee = null;

        if ( ACTION_TYPE_ADD.equals(id) ) {
            employee = new Employee();
        } else {
            employee = employeeService.getEmployeeById(id);
            employee.setScore(employeeReviewService.getEmployeeReview(id));
        }

        map.put("employee", new EmployeeJson(0, employee));
        map.put("canEdit", canEdit);

        map.put("hasPermession", hasPermession());

        return canEdit ? "sm/sm_employee_detail_edit" : "sm/sm_employee_detail";
    }

    private boolean hasPermession() {
        List<FuncDto> permissionFuncs = (List<FuncDto>) session.getAttribute(CommonConst.SESSION_ATTR_KEY_PERMISSION_FUNCS);
        for (FuncDto func : permissionFuncs) {
            for ( FuncDto chldFunc : func.getChildren() ) {
                if ("USER".equals(chldFunc.getCode())) {
                    return true;
                }
            }
        }

        return false;
    }

    @RequestMapping(value = "/sm_employee_detail", method = RequestMethod.POST)
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
            if ( StringUtils.isEmpty(employee.getHeaderImg()) ) {
                employee.setHeaderImg(null);
            }

            if (StringUtils.isEmpty(employee.getId())) {
                employee.setPassword(DigestUtils.md5Hex(employee.getPassword()));
                employeeService.save(employee);
            } else {
                updatePassword(employee);
                employeeService.update(employee);

                Employee loginEmployee = (Employee) session.getAttribute("user");
                if ( loginEmployee.getId().equals(employee.getId()) ) {
                    loginEmployee.setHeaderImg(employee.getHeaderImg());
                }
            }

            result.put("employeeId", employee.getId());
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
        }

        return result;
    }

    private void updatePassword(Employee employee) {
        Employee dbEmployee = employeeService.get(employee.getId());
        if (!employee.getPassword().equals(dbEmployee.getPassword())) { // 如果密码有修改则重新设置密码
            employee.setPassword(DigestUtils.md5Hex(employee.getPassword()));
        }
    }

    private boolean isCodeRepeat(Employee employee) {
        return employeeService.isCodeRepeat(employee);
    }
}