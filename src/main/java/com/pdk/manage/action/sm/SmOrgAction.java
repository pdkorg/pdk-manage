package com.pdk.manage.action.sm;

import com.pdk.manage.dto.sm.OrgJson;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.sm.Org;
import com.pdk.manage.service.sm.SmOrgService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuhaiming on 2015/8/10
 */

@Controller
@RequestMapping("sm")
public class SmOrgAction {
    private static final Logger log = LoggerFactory.getLogger(SmRoleAction.class);

    @Autowired
    private SmOrgService orgService;

    @RequestMapping("/sm_org")
    public String index() {
        return "sm/sm_org";
    }

    @ModelAttribute
    public void getOrg(@RequestParam(value = "id", required = false) String id, Map<String, Object> map) {
        if( StringUtils.isNotEmpty(id) ) {
            map.put( "org", orgService.get(id) );
        }
    }

    @RequestMapping("/sm_org_tree")
    @ResponseBody
    public List<OrgJson> getOrgTree() {
        return getOrgTree(false);
    }

    @RequestMapping("/sm_org_tree_enable")
    @ResponseBody
    public List<OrgJson> getEnablergTree() {
        return getOrgTree(true);
    }

    private List<OrgJson> getOrgTree(boolean enable) {
        List<OrgJson> data = new ArrayList<>();

        List<OrgJson> children = orgService.getOrgTreeRoot(enable);
        OrgJson root = new OrgJson("组织结构");
        root.setChildren(children);

        data.add(root);
        return data;
    }

    @RequestMapping(value = "/sm_org/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> get(@PathVariable("id") String id) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", "success");
        result.put("data", orgService.get(id));
        return result;
    }

    @RequestMapping("/edit")
    public
    @ResponseBody
    Map<String, Object> edit(String id) {
        Map<String, Object> orgData = new HashMap<>();

        orgData.put("org", orgService.get(id));
        orgData.put("success", "true");
        return orgData;
    }

    @RequestMapping(value = "/sm_org", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@Valid Org org, Errors errors, Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();

        if(errors.getErrorCount() > 0) {
            result.put("result", "error");
            for (FieldError err : errors.getFieldErrors()) {
                map.put("err_" + err.getField(), err.getDefaultMessage());
            }
            return result;
        }

        if ( isCodeRepeat(org) ) {
            result.put("result", "error");
            result.put("errorMsg", "组织编码重复，请修改后保存!");
            return result;
        }

        try {
            // 设置内部编码
            String incode = orgService.createInncode(org.getParentId());

            org.setIncode(incode);
            orgService.save(org);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
        }

        return result;
    }

    @RequestMapping(value = "/sm_org", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> update(@Valid Org org, Errors errors, Map<String, Object> map) {

        Map<String, Object> result = new HashMap<>();

        if(errors.getErrorCount() > 0) {
            result.put("result", "error");
            for (FieldError err : errors.getFieldErrors()) {
                map.put("err_" + err.getField(), err.getDefaultMessage());
            }
            return result;
        }

        if ( isCodeRepeat(org) ) {
            result.put("result", "error");
            result.put("errorMsg", "组织编码重复，请修改后保存!");
            return result;
        }

        try {
            orgService.update(org);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/sm_org", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> delete(Org org) {

        Map<String, Object> result = new HashMap<>();

        boolean isRefed = orgService.isRefrence(org.getId());
        if ( isRefed ) {
            result.put("result", "error");
            result.put("errorMsg", "组织被人员引用，请修改后保存!");
            return result;
        }

        try {
            orgService.delete(org);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/sm_org/status/{status}", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> updateStatus( @PathVariable Short status, Org org ) {

        Map<String, Object> result = new HashMap<>();
        org.setStatus(status);

        try {
            if(status == DBConst.STATUS_ENABLE) {
                orgService.enable(org);
            }else {
                orgService.disable(org);
            }
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        }

        return result;
    }

    private boolean isCodeRepeat(Org org) {
        return orgService.isCodeRepeat(org);
    }
}
