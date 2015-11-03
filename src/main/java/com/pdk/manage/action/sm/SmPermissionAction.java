package com.pdk.manage.action.sm;

import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.sm.PermissionFunc;
import com.pdk.manage.service.FuncService;
import com.pdk.manage.service.sm.PermissionFuncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by liuhm on 2015/8/15
 */
@Controller
@RequestMapping("/sm")
public class SmPermissionAction {
    private static final Logger log = LoggerFactory.getLogger(SmPermissionAction.class);

    @Autowired
    FuncService funcService;

    @Autowired
    PermissionFuncService pfService;

    @RequestMapping("/sm_permission")
    public String index() {
        return "sm/sm_permission";
    }

    @RequestMapping("/sm_permission/data_func_tree")
    @ResponseBody
    public Map<String, Object> getFuncTreeList(String roleId) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", "success");
        result.put("menus", funcService.getFuncTreeList());
        return result;
    }

    @RequestMapping("/sm_permission/permission_func_list/{roleId}")
    @ResponseBody
    public Map<String, Object> getPermissionFuncList(@PathVariable String roleId) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", "success");

        List<String> funcIds = new ArrayList<>();
        List<PermissionFunc> funcs = pfService.getByRoleId(roleId);
        for ( PermissionFunc func : funcs ) {
            funcIds.add(func.getFuncId());
        }

        result.put("result", "success");
        result.put("funcIds", funcIds);
        return result;
    }

    @RequestMapping(value="/sm_permission", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> saveRoleFuncList(@RequestParam("roleId") String roleId, @RequestParam("ids") String[] ids) {
        try {
            pfService.savePermissioFuncList(roleId, ids);
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("result", "success");
        return result;
    }
}
