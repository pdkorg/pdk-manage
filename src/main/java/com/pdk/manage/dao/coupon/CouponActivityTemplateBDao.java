package com.pdk.manage.dao.coupon;

import com.pdk.manage.dao.common.BaseDao;
import com.pdk.manage.model.coupon.Coupon;
import com.pdk.manage.model.coupon.CouponActivityTemplateB;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liuhaiming on 2015/9/11.
 */
@Repository
public interface CouponActivityTemplateBDao extends BaseDao<CouponActivityTemplateB> {
    List<CouponActivityTemplateB> selectTemplateByActCode(@Param("actCode") String actCode);

    int deleteCouponByTemplateId(@Param("templateIds") List<String> templateIds);

    List<CouponActivityTemplateB> selectListByTemplateId(@Param("templateId") String templateId);
}
