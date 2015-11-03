package com.pdk.manage.service.coupon.rule;

import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.coupon.Coupon;
import com.pdk.manage.model.sm.User;
import com.pdk.manage.service.coupon.CouponActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liuhaiming on 2015/10/12.
 */
@Service
public class DefaultFilterRule implements IActivitySendRule {
    @Override
    public List<Coupon> filterCoupons(List<Coupon> couponLst, User user) throws BusinessException {
        return couponLst;
    }
}

