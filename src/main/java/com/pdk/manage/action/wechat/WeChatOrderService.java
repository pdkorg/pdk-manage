package com.pdk.manage.action.wechat;

import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.service.coupon.CouponUserRelationService;
import com.pdk.manage.service.order.OrderService;
import com.pdk.manage.service.order.OrderTipService;
import com.pdk.manage.util.DBConst;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Created by hubo on 2015/10/13
 */
@Service
public class WeChatOrderService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderTipService orderTipService;

    @Autowired
    private CouponUserRelationService couponUserRelationService;

    @Transactional(rollbackFor = Exception.class)
    public void payOrder(String orderId, BigDecimal actMny, String couponId, BigDecimal couponMny, BigDecimal tip)
            throws BusinessException {

        if(StringUtils.isEmpty(orderId)) {
            throw new BusinessException("订单支付异常！");
        }

        int code = orderService.payOrder(orderId, DBConst.PAY_WEBCHAT, couponMny, actMny, tip);

        if(code != 1) {

            throw new BusinessException("订单支付异常！");
        }

        if(StringUtils.isNotEmpty(couponId) ) {
            couponUserRelationService.useCoupon(couponId);
        }

        if(tip != null && tip.compareTo(BigDecimal.ZERO) > 0) {
            orderTipService.saveByOrder(orderId, tip);
        }
    }

}
