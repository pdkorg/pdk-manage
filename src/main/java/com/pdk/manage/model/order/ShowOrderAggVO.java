package com.pdk.manage.model.order;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 程祥 on 15/9/10.
 * Function：
 */
public class ShowOrderAggVO implements Serializable {
    private OrderTable head;
    private List<OrderDetail> body;

    public OrderTable getHead() {
        return head;
    }

    public void setHead(OrderTable head) {
        this.head = head;
    }

    public List<OrderDetail> getBody() {
        return body;
    }

    public void setBody(List<OrderDetail> body) {
        this.body = body;
    }

}
