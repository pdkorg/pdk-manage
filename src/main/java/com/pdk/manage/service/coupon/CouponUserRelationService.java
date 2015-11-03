package com.pdk.manage.service.coupon;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pdk.manage.dao.coupon.CouponUserRelationDao;
import com.pdk.manage.dto.coupon.CouponJson;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.coupon.CouponUserRelation;
import com.pdk.manage.service.BaseService;
import com.pdk.manage.util.DBConst;
import com.pdk.manage.util.IdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by liuhaiming on 2015/9/11
 */
@Service
public class CouponUserRelationService extends BaseService<CouponUserRelation> {
    private CouponUserRelationDao getDao() {
        return (CouponUserRelationDao) this.dao;
    }

    @Override
    public String getModuleCode() {
        return IdGenerator.CP_MODULE_CODE;
    }

    public List<CouponUserRelation> getEffectCoupon(String sourceId, Date currDate) {
        return getDao().selectUserCouponUnUseBySourceId(sourceId, currDate);
    }

    public CouponJson getOrderUseCoupon(String userId, String flowTypeId, BigDecimal orderPayMny) {
        CouponUserRelation rtnCoupon = null;

        Date currDate = Calendar.getInstance().getTime();
        List<CouponUserRelation> coupons = getDao().selectUserCouponCanUseBySourceId(userId, currDate, flowTypeId, orderPayMny);
        BigDecimal mny = BigDecimal.ZERO;
        BigDecimal minMny = BigDecimal.ZERO;
        for ( CouponUserRelation coupon : coupons ) {
            if ( mny.compareTo(coupon.getSendMny()) < 0 ) {
                mny = coupon.getSendMny();
                minMny = coupon.getMinPayMny();
                rtnCoupon = coupon;
            } else if ( mny.compareTo(coupon.getSendMny()) == 0
                     && minMny.compareTo(coupon.getMinPayMny()) < 0 ) {
                mny = coupon.getSendMny();
                minMny = coupon.getMinPayMny();
                rtnCoupon = coupon;
            }
        }

        return  rtnCoupon == null ? null : new CouponJson(1, rtnCoupon);
    }

    public List<CouponUserRelation> getUnEffectCoupon(String sourceId, Date currDate) {
        return getDao().selectUnStartCouponUsedBySourceId(sourceId, currDate);
    }
    public PageInfo<CouponUserRelation> getOverdueCouponByPage(String sourceId, Date currDate, int pageNum, int pageSize) {
            PageHelper.startPage(pageNum, pageSize, "create_time desc");
            return new PageInfo<>(getDao().selectUserCouponOverdueBySourceId(sourceId, currDate));
    }

    public void useCoupon(String relationId) throws BusinessException {
        if(StringUtils.isEmpty(relationId)) {
            throw new IllegalArgumentException("relationId is null");
        }

        CouponUserRelation relation = get(relationId);

        if(relation == null) {
            throw new BusinessException("没有找到相应的优惠劵!");
        }

        if ( relation.getStatus() == DBConst.STATUS_DISABLE ) {
            throw new BusinessException("该优惠券已经使用过，不允许重复使用!");
        }

        relation.setStatus(DBConst.STATUS_DISABLE);
        update(relation);
    }

    public List<CouponUserRelation> selectByIds(List<String> ids) {
        return getDao().selectByIds(ids);
    }

    public void deleteUserCouponByTemplateId(String templateId) {
        getDao().deleteUserCouponByTemplateId(templateId);
    }
}

