package com.pdk.manage.service.coupon.rule.factory;

import com.pdk.manage.service.coupon.rule.DefaultFilterRule;
import com.pdk.manage.service.coupon.rule.IActivitySendRule;
import com.pdk.manage.service.coupon.rule.ReviewFilterRule;
import com.pdk.manage.service.coupon.rule.ShareFilterRule;
import com.pdk.manage.util.DBConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by liuhaiming on 2015/10/12.
 */
@Service
public class ActRuleFactory {
    @Autowired
    DefaultFilterRule defaultFilterRule;

    @Autowired
    ReviewFilterRule reviewFilterRule;

    @Autowired
    ShareFilterRule shareFilterRule;

    public IActivitySendRule getRule(String actCode) {
        if ( actCode == DBConst.ACTIVITY_SEND_CODE_FST_REGISTER ) {
            return defaultFilterRule;
        } else if ( actCode == DBConst.ACTIVITY_SEND_CODE_REVIEW ) {
            return reviewFilterRule;
        } else if ( actCode == DBConst.ACTIVITY_SEND_CODE_SHARE ) {
            return shareFilterRule;
        }

        return defaultFilterRule;
    }
}
