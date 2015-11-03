package com.pdk.manage.action.sm;

import com.github.pagehelper.PageInfo;
import com.pdk.manage.common.wapper.sm.SmUserDataTableQueryArgWapper;
import com.pdk.manage.dto.sm.UserJson;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.sm.User;
import com.pdk.manage.model.sm.UserDescribe;
import com.pdk.manage.service.sm.SmUserDescribeService;
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
 * Created by liuhaiming on 2015/9/1
 */
@Controller
@RequestMapping("/sm")
public class SmUserDescribeAction {
    private static final Logger log = LoggerFactory.getLogger(SmUserDescribeAction.class);

    @Autowired
    private SmUserDescribeService userDescribeService;

    @RequestMapping(value = "/sm_user_describe", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(String userId, String describe) {

        Map<String, Object> result = new HashMap<>();

        try {
            userDescribeService.saveDescribe(userId, describe);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
        }

        return result;
    }

    @RequestMapping(value = "/sm_user_describe", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> delete(String userId, String describe) {

        Map<String, Object> result = new HashMap<>();

        try {
            userDescribeService.delByDescribe(userId, describe);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/sm_user_describe/{userId}")
    public @ResponseBody Map<String, Object> list( @PathVariable String userId ) {
        Map<String, Object> result = new HashMap<>();

        List<UserDescribe> userDescribes = null;
        try {
            userDescribes = userDescribeService.selectDescribeByUserId(userId);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        result.put("result", "success");
        result.put("data", userDescribes);

        return result;

    }

}