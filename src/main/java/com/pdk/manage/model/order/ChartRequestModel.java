package com.pdk.manage.model.order;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 程祥 on 15/9/6.
 * Function： 聊天快捷生成订单请求model 订单聚合vo
 */
public class ChartRequestModel implements Serializable{

    private Order head;
    private List<OrderDetail> body;

    public Order getHead() {
        return head;
    }

    public void setHead(Order head) {
        this.head = head;
    }

    public List<OrderDetail> getBody() {
        return body;
    }

    public void setBody(List<OrderDetail> body) {
        this.body = body;
    }
}
