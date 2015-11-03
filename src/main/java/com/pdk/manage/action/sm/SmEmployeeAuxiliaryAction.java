package com.pdk.manage.action.sm;

import com.github.pagehelper.PageInfo;
import com.pdk.manage.common.wapper.sm.SmEmployeeAuxiliaryDataTableQueryArgWapper;
import com.pdk.manage.common.wapper.sm.SmEmployeeDataTableQueryArgWapper;
import com.pdk.manage.common.wapper.sm.SmEmployeeRefDataTableQueryArgWapper;
import com.pdk.manage.dto.sm.EmployeeAuxiliaryJson;
import com.pdk.manage.dto.sm.EmployeeJson;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.sm.Employee;
import com.pdk.manage.model.sm.EmployeeAuxiliary;
import com.pdk.manage.service.sm.EmployeeAuxiliaryService;
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
 * Created by liuhm on 2015/10/15
 */
@Controller
@RequestMapping("/sm")
public class SmEmployeeAuxiliaryAction {
    private static final Logger log = LoggerFactory.getLogger(SmEmployeeAuxiliaryAction.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeAuxiliaryService employeeAuxiliaryService;

    @RequestMapping("/sm_employee_auxiliary/{employeeId}")
    public String EmployeeAuxiliary(@PathVariable String employeeId, Map<String, Object> map) {
        map.put("employee", new EmployeeJson(0, employeeService.get(employeeId)));

        return "sm/sm_employee_auxiliary";
    }

    @ModelAttribute
    public void getEmployee(@RequestParam(value = "id", required = false) String id, Map<String, Object> map) {
        if (StringUtils.isNotEmpty(id)) {
            map.put("employeeAuxiliary", employeeAuxiliaryService.get(id));
        }
    }

    @RequestMapping(value = "/sm_employee_auxiliary", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(String[] roleIds, String employeeId) {
        Map<String, Object> result = new HashMap<>();
        try {
            employeeAuxiliaryService.saveAuxiliaryService(roleIds, employeeId);

            result.put("result", "success");
        } catch (BusinessException e) {
            result.put("result", "error");
            result.put("errorMsg", "保存失败！");
            log.error("保存失败！", e);
        }

        return result;
    }

    @RequestMapping(value = "/sm_employee_auxiliary", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> batchDelete(@RequestParam("ids") String[] ids, @RequestParam(value = "ts", required = false) Long[] ts) {
        Map<String, Object> result = new HashMap<>();

        List<EmployeeAuxiliary> auxiliaryList = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            EmployeeAuxiliary role = new EmployeeAuxiliary();
            role.setId(ids[i]);
            if(ts != null && ts[i] != null) {
                role.setTs(new Date(ts[i]));
            }

            auxiliaryList.add(role);
        }

        try {
            employeeAuxiliaryService.delete(auxiliaryList);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        }

        return result;
    }

    @RequestMapping("/sm_employee_auxiliary/table_data/{employeeId}")
    @ResponseBody
    public Map<String, Object> list(SmEmployeeAuxiliaryDataTableQueryArgWapper arg, @PathVariable String employeeId) {
        Map<String, Object> result = new HashMap<>();
        PageInfo<EmployeeAuxiliary> pageInfo = null;
        pageInfo = employeeAuxiliaryService.selectAuxiliaryByEmployeeId(arg.getSearchText(), arg.getPageNum(), arg.getLength(), arg.getOrderStr(), employeeId);

        List<EmployeeAuxiliaryJson> data = new ArrayList<>();

        int index = 1;
        for (EmployeeAuxiliary employeeAuxiliary : pageInfo.getList()) {
            data.add(new EmployeeAuxiliaryJson(index, employeeAuxiliary));
            index++;
        }

        result.put("draw", arg.getDraw());
        result.put("recordsTotal", pageInfo.getTotal());
        result.put("recordsFiltered", pageInfo.getTotal());
        result.put("data", data);

        return result;
    }
}