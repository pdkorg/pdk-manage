package com.pdk.manage.model.app.request;

import com.pdk.manage.model.app.response.OrderGoodsModel;
import com.pdk.manage.model.order.OrderDetail;

import java.util.List;

/**
 * Created by 程祥 on 15/8/8.
 * Function：订单详情修改请求model
 */
public class OrderDetailReqModel {


    public OrderDetailReqModel() {
    }

    private String orderid;

    private Long ts;

    private List<OrderDetail> list;

    public List<OrderDetail> getList() {
        return list;
    }

    public void setList(List<OrderDetail> list) {
        this.list = list;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }
}
