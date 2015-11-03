package com.pdk.manage.dao.coupon;

import com.pdk.manage.dao.common.BaseDao;
import com.pdk.manage.model.coupon.CouponActivityRule;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liuhaiming on 2015/9/11.
 */
@Repository
public interface CouponActivityRuleDao extends BaseDao<CouponActivityRule> {
    List<CouponActivityRule> getListByActivityId(String activityId);

    int deleteCouponActivityRuleByActivityId(@Param("activityIds") List<String> activityIds);
}
