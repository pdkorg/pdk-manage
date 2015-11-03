package com.pdk.manage.service.coupon.rule;

import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.coupon.Coupon;
import com.pdk.manage.model.sm.User;
import com.pdk.manage.service.coupon.CouponActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by liuhaiming on 2015/10/12.
 */
@Service
public class ReviewFilterRule implements IActivitySendRule {
    @Override
    public List<Coupon> filterCoupons(List<Coupon> couponLst, User user) throws BusinessException {
        Random ran = new Random();

        List<Coupon> rtnCoupon = new ArrayList<>();
        rtnCoupon.add( couponLst.get( ran.nextInt(couponLst.size()) ) );
        return rtnCoupon;
    }
}

