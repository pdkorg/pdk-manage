package com.pdk.manage.dao.coupon;

import com.pdk.manage.dao.common.BaseDao;
import com.pdk.manage.dao.common.BusinessLogicMapper;
import com.pdk.manage.model.coupon.Coupon;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liuhaiming on 2015/9/11.
 */
@Repository
public interface CouponDao extends BaseDao<Coupon>, BusinessLogicMapper<Coupon> {
    List<Coupon> getListByActivityId(String activityId);

    int deleteCouponByActivityId(@Param("activityIds") List<String> activityIds);

    List<Coupon> selectCouponByTemplateId(@Param("templateId") String templateId);

}
