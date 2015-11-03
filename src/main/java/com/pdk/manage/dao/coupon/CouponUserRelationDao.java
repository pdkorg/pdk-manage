package com.pdk.manage.dao.coupon;

import com.pdk.manage.dao.common.BaseDao;
import com.pdk.manage.model.coupon.Coupon;
import com.pdk.manage.model.coupon.CouponUserRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by liuhaiming on 2015/9/11.
 */
@Repository
public interface CouponUserRelationDao extends BaseDao<CouponUserRelation> {
    List<CouponUserRelation> selectUserCouponUnUseBySourceId(@Param("sourceId") String sourceId, @Param("currDate") Date currDate);

    List<CouponUserRelation> selectUnStartCouponUsedBySourceId(@Param("sourceId") String sourceId, @Param("currDate") Date currDate);

    List<CouponUserRelation> selectUserCouponOverdueBySourceId(@Param("sourceId") String sourceId, @Param("currDate") Date currDate);

    List<CouponUserRelation> selectUserCouponCanUseBySourceId(@Param("userId") String userId, @Param("currDate") Date currDate, @Param("flowTypeId") String flowTypeId, @Param("mny") BigDecimal mny);

    List<CouponUserRelation> selectByIds(@Param("ids") List<String> ids);

    List<CouponUserRelation> selectBySendRelation(@Param("templateId") String templateId, @Param("userId") String userId, @Param("currDate") Date currDate);

    void deleteUserCouponByTemplateId(@Param("templateId") String templateId);
}
