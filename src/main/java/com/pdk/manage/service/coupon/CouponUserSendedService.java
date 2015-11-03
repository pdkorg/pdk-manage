package com.pdk.manage.service.coupon;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pdk.manage.common.wapper.coupon.CouponSendUserDetailTableQueryArgWapper;
import com.pdk.manage.dao.coupon.CouponActivityDao;
import com.pdk.manage.dao.coupon.CouponUserSendedDao;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.coupon.*;
import com.pdk.manage.model.order.UserInfoForCoupon;
import com.pdk.manage.service.BaseService;
import com.pdk.manage.service.order.OrderService;
import com.pdk.manage.util.DBConst;
import com.pdk.manage.util.IdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by liuhaiming on 2015/9/11
 */
@Service
public class CouponUserSendedService extends BaseService<CouponUserSended> {
    private static final Logger log = LoggerFactory.getLogger(CouponUserSendedService.class);

    private CouponUserSendedDao getDao() {
        return (CouponUserSendedDao) this.dao;
    }

    public PageInfo<CouponUserSended> selectPageByActivityId(String activityId, String searchText, int pageNum, int pageSize, String orderBy) throws BusinessException {
        PageHelper.startPage(pageNum, pageSize, orderBy);
        PageInfo<CouponUserSended> pageInfo;
        if (StringUtils.isEmpty(searchText)) {
            pageInfo = new PageInfo<>(getDao().selectByActivityId(activityId));
        } else {
            pageInfo = new PageInfo<>(getDao().selectLikeByActivityId(activityId, searchText));
        }

        return pageInfo;
    }

    /**
     * 根据requestModel查询订单中的用户id列表
     * @param
     * @param searchText
     * @return list
     */
    public List<CouponUserSended> getUserListForCoupon(CouponActivityRuleModel model, String searchText) throws BusinessException{
        List<CouponUserSended> result;
        if (StringUtils.isEmpty(searchText)) {
            if (!model.isLinkOrder()) {
                result = getDao().getUserList4CouponSend(model.getSqlWhere());
            } else {
                result = getDao().getUserList4CouponSendByOrder(model.getSqlWhere(), model.getSqlHaving());
            }
        } else {
            if (!model.isLinkOrder()) {
                result = getDao().getUserListLike4CouponSend(model.getSqlWhere(), searchText);
            } else {
                result = getDao().getUserListLike4CouponSendByOrder(model.getSqlWhere(), model.getSqlHaving(), searchText);
            }
        }

        return result;
    }

    /**
     * 根据requestModel查询订单中的用户id列表
     * @param
     * @return list
     */
    public PageInfo<CouponUserSended> getUserPageInfoForCoupon(CouponSendUserDetailTableQueryArgWapper arg, CouponActivityRuleModel model) throws BusinessException{
        PageHelper.startPage(arg.getPageNum(), arg.getLength(), arg.getOrderStr());
        PageInfo<CouponUserSended> pageInfo;
        if (StringUtils.isEmpty(arg.getSearchText())) {
            if (!model.isLinkOrder()) {
                pageInfo = new PageInfo<>(getDao().getUserList4CouponSend(model.getSqlWhere()));
            } else {
                pageInfo = new PageInfo<>(getDao().getUserList4CouponSendByOrder(model.getSqlWhere(), model.getSqlHaving()));
            }

        } else {
            if (!model.isLinkOrder()) {
                pageInfo = new PageInfo<>(getDao().getUserListLike4CouponSend(model.getSqlWhere(), arg.getSearchText()));
            } else {
                pageInfo = new PageInfo<>(getDao().getUserListLike4CouponSendByOrder(model.getSqlWhere(), model.getSqlHaving(), arg.getSearchText()));
            }
        }
        return pageInfo;
    }

    /**
     * 根据requestModel查询订单中的用户数量
     * @param sqlWhere
     * @param sqlHaving
     * @param isLinkOrder
     * @return
     */
    public int getSendUserCount(String sqlWhere, String sqlHaving, boolean isLinkOrder) {
        int userCount;
        if (!isLinkOrder) {
            userCount = getDao().getSendUserCount(sqlWhere);
        } else {
            userCount = getDao().getSendUserCountWithOrder(sqlWhere, sqlHaving);
        }

        return userCount;
    }
}

