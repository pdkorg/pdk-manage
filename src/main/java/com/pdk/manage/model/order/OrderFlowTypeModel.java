package com.pdk.manage.model.order;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

/**
 * Created by liuhaiming on 2015/10/27.
 */
public class OrderFlowTypeModel {
    private String flowTypeName;

    private int orderCount;

    public String getFlowTypeName() {
        return flowTypeName;
    }

    public void setFlowTypeName(String flowTypeName) {
        this.flowTypeName = flowTypeName;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }
}
