package com.pdk.manage.service.order;

import com.pdk.manage.dao.order.OrderTipMapper;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.order.Order;
import com.pdk.manage.model.order.OrderTip;
import com.pdk.manage.model.sm.User;
import com.pdk.manage.service.BaseService;
import com.pdk.manage.service.sm.SmUserService;
import com.pdk.manage.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


/**
 * Created by 程祥 on 15/8/17.
 * Function：
 */
@Service
public class OrderTipService extends BaseService<OrderTip> {
    public OrderTipMapper getDao() {
        return (OrderTipMapper) this.dao;
    }

    @Autowired
    private OrderService orderService;

    @Autowired
    private SmUserService smUserService;

    @Override
    public String getModuleCode() {
        return IdGenerator.OR_MODULE_CODE;
    }

    public int getOrderTipCountByEmployee(String employeeId) {
        List<OrderTip> orderTips = getDao().selectByEmployeeId(employeeId);

        return orderTips.size();
    }

    public OrderTip getOrderTipByOrderId(String orderId) {
        OrderTip cond = new OrderTip();
        cond.setOrderId(orderId);
        OrderTip tip = getDao().selectOne(cond);

        return tip;
    }

    public int saveByOrder(String orderId, BigDecimal mny) throws BusinessException {
        Order order = orderService.get(orderId);
        User user = smUserService.get(order.getUserId());

        OrderTip orderTip = new OrderTip();
        orderTip.setOrderId(order.getId());
        orderTip.setUserId(order.getUserId());
        orderTip.setEmployeeId(order.getYwyId());
        orderTip.setMny(mny);
        orderTip.setSourceId(user.getSourceId());

        return save(orderTip);
    }
}
