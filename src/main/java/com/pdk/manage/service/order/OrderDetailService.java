package com.pdk.manage.service.order;

import com.pdk.manage.dao.order.OrderDetailMapper;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.order.OrderDetail;
import com.pdk.manage.service.BaseService;
import com.pdk.manage.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 程祥 on 15/8/17.
 * Function：
 */
@Service
public class OrderDetailService extends BaseService<OrderDetail> {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public String getModuleCode() {
        return IdGenerator.OR_MODULE_CODE;
    }

    public List<OrderDetail> getGoodsByOrderId(String orderId) throws BusinessException {

        return orderDetailMapper.getGoodsByOrderId(orderId);

    }

}
