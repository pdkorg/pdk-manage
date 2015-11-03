package com.pdk.manage.dao.order;

import com.pdk.manage.dao.common.BaseDao;
import com.pdk.manage.model.order.OrderDetail;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface OrderDetailMapper extends BaseDao<OrderDetail> {

    List<OrderDetail> getGoodsByOrderId(String orderId);

    List<OrderDetail> getOrderDetailByOrderIds(List<String> list);

    int deleteDetailsByOrderId(String orderId);

    int updateDetailList(List<OrderDetail> list);
}