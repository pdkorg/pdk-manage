package com.pdk.manage.dao.coupon;

import com.pdk.manage.dao.common.BaseDao;
import com.pdk.manage.model.coupon.CouponActivity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by liuhaiming on 2015/9/11.
 */
@Repository
public interface CouponActivityDao extends BaseDao<CouponActivity> {
    List<CouponActivity> selectEnable();

    List<CouponActivity> selectLikeEnable(@Param("searchText") String searchText);

    List<CouponActivity> findAutoSendActivity(@Param("sendDate") Date sendDate );
}
