package com.pdk.manage.service.coupon;

import com.pdk.manage.dao.coupon.CouponActivityRuleDao;
import com.pdk.manage.dao.coupon.CouponDao;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.coupon.Coupon;
import com.pdk.manage.model.coupon.CouponActivity;
import com.pdk.manage.model.coupon.CouponActivityRule;
import com.pdk.manage.service.BaseService;
import com.pdk.manage.util.DBConst;
import com.pdk.manage.util.IdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaiming on 2015/9/11
 */
@Service
public class CouponActivityRuleService extends BaseService<CouponActivityRule> {
    private CouponActivityRuleDao getDao() {
        return (CouponActivityRuleDao) this.dao;
    }

    @Override
    public String getModuleCode() {
        return IdGenerator.CP_MODULE_CODE;
    }

    public List<CouponActivityRule> getByActivityId(String activityId) {
        return getDao().getListByActivityId(activityId);
    }

    public void updateCouponActivityRules(List<CouponActivityRule> couponActivityRules, String activityId) throws BusinessException {
        if (couponActivityRules == null) {
            return;
        }

        List<CouponActivityRule> insCouponActivityRules = new ArrayList<>();
        List<CouponActivityRule> updCouponActivityRules = new ArrayList<>();
        List<CouponActivityRule> delCouponActivityRules = new ArrayList<>();
        for (CouponActivityRule couponActivityRule : couponActivityRules) {
            couponActivityRule.setActivityId(activityId);

            if (StringUtils.isEmpty(couponActivityRule.getId())) {
                insCouponActivityRules.add(couponActivityRule);
            } else if (couponActivityRule.getDr() == DBConst.DR_DEL) {
                delCouponActivityRules.add(couponActivityRule);
            } else {
                couponActivityRule.setDr(DBConst.DR_NORMAL);
                updCouponActivityRules.add(couponActivityRule);
            }
        }

        if ( insCouponActivityRules.size() > 0 ) {
            save(insCouponActivityRules);
        }

        if ( updCouponActivityRules.size() > 0 ) {
            for (CouponActivityRule couponActivityRule : updCouponActivityRules) {
                update(couponActivityRule);
            }
        }

        if ( delCouponActivityRules.size() > 0 ) {
            delete(delCouponActivityRules);
        }
    }

    public void deleteByActivity(List<CouponActivity> activitys) {
        List<String> actIds = new ArrayList<>();
        for ( CouponActivity activity : activitys ) {
            actIds.add(activity.getId());
        }

        getDao().deleteCouponActivityRuleByActivityId(actIds);
    }
}

