package com.pdk.manage.action.coupon;

import com.pdk.manage.model.coupon.CouponActivityRule;
import com.pdk.manage.model.sm.User;
import com.pdk.manage.service.coupon.CouponActivityRuleService;
import com.pdk.manage.service.sm.SmUserService;
import com.pdk.manage.util.DBConst;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuhaiming on 2015/9/11.
 */
@Controller
@RequestMapping("coupon")
public class CouponActivityRuleAction {
    private static final Logger log = LoggerFactory.getLogger(CouponActivityRuleAction.class);
    private static final String MARK_ADD = "ADD";

    @Autowired
    private CouponActivityRuleService couponActivityRuleService;

    @Autowired
    private SmUserService userService;

    @ModelAttribute
    public void getCoupon(@RequestParam(value = "id", required = false) String id, Map<String, Object> map) {
        if( StringUtils.isNotEmpty(id) ) {
            map.put( "couponActivityRule", couponActivityRuleService.get(id) );
        }
    }

    @RequestMapping("/coupon_activity_rule/{activityId}")
    @ResponseBody
    public Map<String, Object> couponsByActivityId(@PathVariable String activityId) {
        Map<String, Object> result = new HashMap<>();

        List<CouponActivityRule> rules;
        if (MARK_ADD.equals(activityId)) {
            rules = new ArrayList<>();
        } else {
            rules = couponActivityRuleService.getByActivityId(activityId);
        }

        fillUserName(rules);

        result.put("couponActivityRules", rules);
        result.put("result", "success");
        return result;

    }

    private void fillUserName(List<CouponActivityRule> rules) {
        List<String> ids = new ArrayList<>();
        for (CouponActivityRule rule : rules) {
            if ( rule.getRuleType() == DBConst.RULE_TYPE_REF_USER ) {
                ids.add(rule.getChkVal());
            }
        }

        if ( ids.size() > 0 ) {
            List<User> users = userService.findUserByIds(ids);
            Map<String, String> userNameMap = new HashMap<>();
            for (User user : users) {
                userNameMap.put(user.getId(), user.getName());
            }

            for (CouponActivityRule rule : rules) {
                if ( rule.getRuleType() == DBConst.RULE_TYPE_REF_USER ) {
                    rule.setChkValName( userNameMap.get(rule.getChkVal()) );
                }
            }
        }

    }
}
