package com.pdk.manage.dao.coupon;

import com.pdk.manage.dao.common.BaseDao;
import com.pdk.manage.model.coupon.CouponUserRelation;
import com.pdk.manage.model.coupon.CouponUserSended;
import com.pdk.manage.model.order.UserInfoForCoupon;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by liuhaiming on 2015/9/11.
 */
@Repository
public interface CouponUserSendedDao extends BaseDao<CouponUserSended> {
    List<CouponUserSended> selectByActivityId(@Param("activityId") String activityId);
    List<CouponUserSended> selectLikeByActivityId(@Param("activityId") String activityId, @Param("searchText") String searchText);

    List<CouponUserSended> getUserList4CouponSend(@Param("sqlWhere") String sqlWhere);
    List<CouponUserSended> getUserList4CouponSendByOrder(@Param("sqlWhere") String sqlWhere, @Param("sqlHaving") String sqlHaving);

    List<CouponUserSended> getUserListLike4CouponSend(@Param("sqlWhere") String sqlWhere, @Param("searchText") String searchText);
    List<CouponUserSended> getUserListLike4CouponSendByOrder(@Param("sqlWhere") String sqlWhere, @Param("sqlHaving") String sqlHaving, @Param("searchText") String searchText);

    int getSendUserCount(@Param("sqlWhere") String sqlWhere);
    int getSendUserCountWithOrder(@Param("sqlWhere") String sqlWhere, @Param("sqlHaving") String sqlHaving);
}
