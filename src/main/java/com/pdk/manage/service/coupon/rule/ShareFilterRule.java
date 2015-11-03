package com.pdk.manage.service.coupon.rule;

import com.pdk.manage.dao.coupon.CouponUserRelationDao;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.coupon.Coupon;
import com.pdk.manage.model.coupon.CouponUserRelation;
import com.pdk.manage.model.sm.User;
import org.apache.commons.configuration.web.AppletConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by liuhaiming on 2015/10/12.
 */
@Service
public class ShareFilterRule implements IActivitySendRule {
    @Autowired
    CouponUserRelationDao couponUserRelationDao;

    @Override
    public List<Coupon> filterCoupons(List<Coupon> couponLst, User user) throws BusinessException {
        if ( isSendedToday(couponLst, user) ) {
            return null;
        }

        Random ran = new Random();

        List<Coupon> rtnCoupon = new ArrayList<>();
        rtnCoupon.add( couponLst.get( ran.nextInt(couponLst.size()) ) );
        return rtnCoupon;
    }

    private boolean isSendedToday(List<Coupon> couponLst, User user) {
        String templateId = couponLst.get(0).getTemplateId();
        String userId = user.getId();
        Calendar cal = Calendar.getInstance();
        List<CouponUserRelation> couponUserRelations = couponUserRelationDao.selectBySendRelation(templateId, userId, cal.getTime());

        return couponUserRelations != null && couponUserRelations.size() > 0;
    }
}

