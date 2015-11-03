package com.pdk.manage.action.sm;

import com.pdk.manage.dto.sm.UserJson;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.sm.User;
import com.pdk.manage.service.sm.SmUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuhaiming on 2015/8/15
 */
@Controller
@RequestMapping("/sm")
public class SmUserDetailAction {
    private static final Logger log = LoggerFactory.getLogger(SmUserDetailAction.class);

    @Autowired
    private SmUserService userService;

    @ModelAttribute
    public void getUser(@RequestParam(value = "id", required = false) String id, Map<String, Object> map) {
        if( StringUtils.isNotEmpty(id) ) {
            map.put( "user", userService.get(id) );
        }
    }

    @RequestMapping(value = "/sm_user_detail/{id}", method = RequestMethod.GET)
    public String userDetail(@PathVariable("id") String id, Map<String, Object> map, boolean canEdit) {
        User user =  userService.selectById(id);

        map.put("user", new UserJson(0, user));
        map.put("canEdit", canEdit);

        return canEdit ? "sm/sm_user_detail_edit" : "sm/sm_user_detail";
    }

    @RequestMapping(value = "/sm_user_detail", method = RequestMethod.PUT)
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
}
