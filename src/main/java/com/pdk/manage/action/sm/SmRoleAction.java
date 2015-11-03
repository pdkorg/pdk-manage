package com.pdk.manage.action.sm;

import com.github.pagehelper.PageInfo;
import com.pdk.manage.common.wapper.sm.SmRoleAuxiliaryRefDataTableQueryArgWapper;
import com.pdk.manage.common.wapper.sm.SmRoleDataTableQueryArgWapper;
import com.pdk.manage.dto.sm.RoleJson;
import com.pdk.manage.dto.sm.RoleTreeJson;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.sm.Role;
import com.pdk.manage.service.sm.SmRoleService;
import com.pdk.manage.util.DBConst;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * Created by liuhaiming on 2015/8/10
 */

@Controller
@RequestMapping("sm")
public class SmRoleAction {

    private static final Logger log = LoggerFactory.getLogger(SmRoleAction.class);

    @Autowired
    private SmRoleService roleService;

    @Autowired
    private String adminRoleId;

    @RequestMapping("/sm_role")
    public String index() {
        return "sm/sm_role";
    }

    @ModelAttribute
    public void getRole(@RequestParam(value = "id", required = false) String id, Map<String, Object> map) {
        if( StringUtils.isNotEmpty(id) ) {
            map.put( "role", roleService.get(id) );
        }
    }

    @RequestMapping("/sm_role_tree")
    @ResponseBody
    public List<RoleTreeJson> getRoleTree() {
        List<RoleTreeJson> data = new ArrayList<>();

        List<RoleTreeJson> children = new ArrayList<>();
        try {
            List<Role> roles = roleService.selectStatusEnable();
            for ( Role role : roles ) {
                if ( adminRoleId.equals(role.getId()) ) {
                    continue;
                }
                children.add( new RoleTreeJson(role) );
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        RoleTreeJson root = new RoleTreeJson("角色类型");
        root.setChildren(children);

        data.add(root);
        return data;
    }

    @RequestMapping(value = "/sm_role/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> get(@PathVariable("id") String id) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", "success");
        if("all".equals(id)) {
            try {
                result.put("data", roleService.selectAll());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                result.put("result", "error");
            }
        } else if("status".equals(id)) {
            try {
                result.put("data", roleService.selectStatusEnable());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                result.put("result", "error");
            }
        } else {
            result.put("data", roleService.get(id));
        }
        return result;
    }

    @RequestMapping(value = "/sm_role", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@Valid Role role, Errors errors, Map<String, Object> map) {

        Map<String, Object> result = new HashMap<>();

        if(errors.getErrorCount() > 0) {
            result.put("result", "error");
            for (FieldError err : errors.getFieldErrors()) {
                map.put("err_" + err.getField(), err.getDefaultMessage());
            }
            return result;
        }

        if ( isCodeRepeat(role) ) {
            result.put("result", "error");
            result.put("errorMsg", "角色编码或名称重复，请修改后保存!");
            return result;
        }

        try {
            roleService.save(role);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
        }

        return result;
    }

    @RequestMapping(value = "/sm_role", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> update(@Valid Role role, Errors errors, Map<String, Object> map) {

        Map<String, Object> result = new HashMap<>();

        if(errors.getErrorCount() > 0) {
            result.put("result", "error");
            for (FieldError err : errors.getFieldErrors()) {
                map.put("err_" + err.getField(), err.getDefaultMessage());
            }
            return result;
        }

        if ( isCodeRepeat(role) ) {
            result.put("result", "error");
            result.put("errorMsg", "角色编码重复，请修改后保存!");
            return result;
        }

        try {
            roleService.update(role);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/sm_role", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> batchDelete(@RequestParam("ids") String[] ids, @RequestParam(value = "ts", required = false) Long[] ts) {

        Map<String, Object> result = new HashMap<>();

        List<Role> roleList = new ArrayList<>();
        List<String> roleIds = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            Role role = new Role();
            role.setId(ids[i]);
            if(ts != null && ts[i] != null) {
                role.setTs(new Date(ts[i]));
            }

            roleIds.add(role.getId());
            roleList.add(role);

            if (adminRoleId.equals(role.getId())) {
                result.put("result", "error");
                result.put("errorMsg", "预置管理员角色不能删除!");
                return result;
            }
        }

        boolean isRefed = roleService.isRefrence(roleIds);
        if ( isRefed ) {
            result.put("result", "error");
            result.put("errorMsg", "角色被人员引用，请修改后保存!");
            return result;
        }

        try {
            roleService.delete(roleList);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/sm_role/status/{status}", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> updateStatus(@PathVariable Short status, @RequestParam("ids") String[] ids, @RequestParam(value = "ts", required = false) Long[] ts) {

        Map<String, Object> result = new HashMap<>();

        List<Role> roleList = new ArrayList<>();

        for (int i = 0; i < ids.length; i++) {
            Role role = new Role();
            role.setId(ids[i]);
            if(ts != null && ts[i] != null) {
                role.setTs(new Date(ts[i]));
            }
            roleList.add(role);
        }

        try {
            if(status == DBConst.STATUS_ENABLE) {
                roleService.enable(roleList);
            }else {
                roleService.disable(roleList);
            }
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        }

        return result;
    }

    @RequestMapping("/sm_role/table_data")
    public @ResponseBody Map<String, Object> list( SmRoleDataTableQueryArgWapper arg) {

        Map<String, Object> result = new HashMap<>();

        PageInfo<Role> pageInfo = null;
        try {
            pageInfo = roleService.selectLikePage(arg.getSearchText(), arg.getPageNum(), arg.getLength(), arg.getOrderStr());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        List<RoleJson> data = new ArrayList<>();

        int index = 1;

        for (Role role : pageInfo.getList()) {
            data.add(new RoleJson(index, role.getId(), role.getCode(), role.getName(), role.getStatus(), role.getMemo(), role.getTs()));
            index++;
        }

        result.put("draw", arg.getDraw());
        result.put("recordsTotal", pageInfo.getTotal());
        result.put("recordsFiltered", pageInfo.getTotal());
        result.put("data", data);

        return result;

    }

    @RequestMapping("/sm_role/table_data/auxiliary_ref/{employeeId}")
    public @ResponseBody Map<String, Object> refAuxiliaryList( SmRoleAuxiliaryRefDataTableQueryArgWapper arg, @PathVariable String employeeId) {
        Map<String, Object> result = new HashMap<>();

        PageInfo<Role> pageInfo = null;
        try {
            pageInfo = roleService.select4Auxiliary(employeeId, arg.getSearchText(), arg.getPageNum(), arg.getLength(), arg.getOrderStr());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        List<RoleJson> data = new ArrayList<>();

        int index = 1;

        for (Role role : pageInfo.getList()) {
            data.add(new RoleJson(index, role.getId(), role.getCode(), role.getName(), role.getStatus(), role.getMemo(), role.getTs()));
            index++;
        }

        result.put("draw", arg.getDraw());
        result.put("recordsTotal", pageInfo.getTotal());
        result.put("recordsFiltered", pageInfo.getTotal());
        result.put("data", data);

        return result;

    }

    private boolean isCodeRepeat(Role role) {
        return roleService.isCodeRepeat(role);
    }

}
