package com.pdk.manage.service.coupon;

import com.pdk.manage.dao.coupon.CouponDao;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.coupon.Coupon;
import com.pdk.manage.model.coupon.CouponActivity;
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
public class CouponService extends BaseService<Coupon> {
    private CouponDao getDao() {
        return (CouponDao) this.dao;
    }

    @Override
    public String getModuleCode() {
        return IdGenerator.CP_MODULE_CODE;
    }

    public List<Coupon> getByActivityId(String activityId) {
        return getDao().getListByActivityId(activityId);
    }

    public void updateCoupons(List<Coupon> coupons, String activityId) throws BusinessException {
        List<Coupon> insCoupons = new ArrayList<>();
        List<Coupon> updCoupons = new ArrayList<>();
        List<Coupon> delCoupons = new ArrayList<>();
        for (Coupon coupon : coupons) {
            coupon.setActivityId(activityId);

            if (StringUtils.isEmpty(coupon.getId())) {
                coupon.setCode(IdGenerator.generateCode(IdGenerator.CODE_PER_MARK_COUPON));
                insCoupons.add(coupon);
            } else if (coupon.getDr() == DBConst.DR_DEL) {
                delCoupons.add(coupon);
            } else {
                coupon.setDr(DBConst.DR_NORMAL);
                updCoupons.add(coupon);
            }
        }

        if ( insCoupons.size() > 0 ) {
            save(insCoupons);
        }

        if ( updCoupons.size() > 0 ) {
            for (Coupon coupon : updCoupons) {
                update(coupon);
            }
        }

        if ( delCoupons.size() > 0 ) {
            delete(delCoupons);
        }
    }

    public void deleteByActivity(List<CouponActivity> activitys) {
        List<String> actIds = new ArrayList<>();
        for ( CouponActivity activity : activitys ) {
            actIds.add(activity.getId());
        }

        getDao().deleteCouponByActivityId(actIds);
    }

    public List<Coupon> selectCouponByTemplateId(String templateId) {
        return getDao().selectCouponByTemplateId(templateId);
    }

}

