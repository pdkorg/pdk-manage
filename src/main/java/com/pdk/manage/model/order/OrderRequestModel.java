package com.pdk.manage.model.order;

import java.util.List;

/**
 * Created by 程祥 on 15/8/19.
 * Function：查询订单查询类
 */
public class OrderRequestModel {

    private String orderId;
    private String orderCode;
    //业务员id
    private String ywyid;
    //客服id
    private String waiterid;
    //用户id
    private String customid;
    //用户sourceid
    private String sourceid;
    //订单状态
    private String orderState;
    private String deliveryState;
    //开始时间
    private String fromDate;
    //截止时间
    private String toDate;
    //订单类型
    private String flowtypeid;
    //支付状态
    private String payStatus;

    private int pageNum;
    private int pageSize;
    private int limitStart;
    private String orderStr;

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getYwyid() {
        return ywyid;
    }

    public void setYwyid(String ywyid) {
        this.ywyid = ywyid;
    }

    public String getWaiterid() {
        return waiterid;
    }

    public void setWaiterid(String waiterid) {
        this.waiterid = waiterid;
    }

    public String getCustomid() {
        return customid;
    }

    public void setCustomid(String customid) {
        this.customid = customid;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(String deliveryState) {
        this.deliveryState = deliveryState;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getFlowtypeid() {
        return flowtypeid;
    }

    public void setFlowtypeid(String flowtypeid) {
        this.flowtypeid = flowtypeid;
    }

    public String getSourceid() {
        return sourceid;
    }

    public void setSourceid(String sourceid) {
        this.sourceid = sourceid;
    }
}
