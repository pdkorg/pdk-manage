package com.pdk.manage.action.sm;

import com.github.pagehelper.PageInfo;
import com.pdk.manage.common.wapper.sm.SmUserDataTableQueryArgWapper;
import com.pdk.manage.common.wapper.sm.SmUserRefDataTableQueryArgWapper;
import com.pdk.manage.dto.sm.UserJson;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.sm.User;
import com.pdk.manage.service.sm.SmUserService;
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
 * Created by liuhaiming on 2015/8/15
 */
@Controller
@RequestMapping("/sm")
public class SmUserAction {
    private static final Logger log = LoggerFactory.getLogger(SmUserAction.class);

    @Autowired
    private SmUserService userService;

    @RequestMapping("/sm_user")
    public String index() {
        return "sm/sm_user";
    }

    @ModelAttribute
    public void getUser(@RequestParam(value = "id", required = false) String id, Map<String, Object> map) {
        if( StringUtils.isNotEmpty(id) ) {
            map.put( "user", userService.get(id) );
        }
    }

    @RequestMapping(value = "/sm_user/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> get(@PathVariable("id") String id) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", "success");
        result.put("data", new UserJson(0, userService.selectById(id)));
        return result;
    }

    @RequestMapping(value = "/sm_user_all_info/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getById(@PathVariable("id") String id) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", "success");
        result.put("data", new UserJson(0, userService.selectAllInfoById(id)));
        return result;
    }

    @RequestMapping(value = "/sm_user", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> update(@Valid User user, Errors errors, Map<String, Object> map) {

        Map<String, Object> result = new HashMap<>();

        if(errors.getErrorCount() > 0) {
            result.put("result", "error");
            for (FieldError err : errors.getFieldErrors()) {
                map.put("err_" + err.getField(), err.getDefaultMessage());
            }
            return result;
        }

        try {
            userService.update(user);
            result.put("userId", user.getId());
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/sm_user", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> batchDelete(@RequestParam("ids") String[] ids, @RequestParam(value = "ts", required = false) Long[] ts) {

        Map<String, Object> result = new HashMap<>();

        List<User> userList = new ArrayList<>();

        for (int i = 0; i < ids.length; i++) {
            User user = new User();
            user.setId(ids[i]);
            if(ts != null && ts[i] != null) {
                user.setTs(new Date(ts[i]));
            }
            userList.add(user);
        }

        try {
            userService.delete(userList);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/sm_user/status/{status}", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> updateStatus(@PathVariable Short status, @RequestParam("ids") String[] ids, @RequestParam(value = "ts", required = false) Long[] ts) {

        Map<String, Object> result = new HashMap<>();

        List<User> userList = new ArrayList<>();

        for (int i = 0; i < ids.length; i++) {
            User user = new User();
            user.setId(ids[i]);
            if(ts != null && ts[i] != null) {
                user.setTs(new Date(ts[i]));
            }
            userList.add(user);
        }

        try {
            if(status == DBConst.STATUS_ENABLE) {
                userService.enable(userList);
            }else {
                userService.disable(userList);
            }
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        }

        return result;
    }

    @RequestMapping("/sm_user/table_data")
    public @ResponseBody Map<String, Object> list( SmUserDataTableQueryArgWapper arg) {
        Map<String, Object> result = new HashMap<>();

        PageInfo<User> pageInfo = null;
        try {
            pageInfo = userService.mySelectLikePage(arg);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        List<UserJson> data = new ArrayList<>();

        int index = 1;

        for (User employee : pageInfo.getList()) {
            data.add(new UserJson(index, employee));
            index++;
        }

        result.put("draw", arg.getDraw());
        result.put("recordsTotal", pageInfo.getTotal());
        result.put("recordsFiltered", pageInfo.getTotal());
        result.put("data", data);

        return result;

    }

    @RequestMapping("/sm_user/table_data/ref")
    public @ResponseBody Map<String, Object> refList( SmUserRefDataTableQueryArgWapper arg) {
        Map<String, Object> result = new HashMap<>();

        PageInfo<User> pageInfo = null;
        try {
            pageInfo = userService.mySelectRefLikePage(arg);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        List<UserJson> data = new ArrayList<>();

        int index = 1;

        for (User employee : pageInfo.getList()) {
            data.add(new UserJson(index, employee));
            index++;
        }

        result.put("draw", arg.getDraw());
        result.put("recordsTotal", pageInfo.getTotal());
        result.put("recordsFiltered", pageInfo.getTotal());
        result.put("data", data);

        return result;

    }
}
