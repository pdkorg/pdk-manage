package com.pdk.manage.model.app.request;

/**
 * Created by 程祥 on 15/8/8.
 * Function：订单详情请求model
 */
public class NewOrderRequestModel {

    private String id;
    private String orderid;
    private String goodsName;
    private int goodsnum;
    private double total;
    private String buyAddress;

    public NewOrderRequestModel() {
    }

    public NewOrderRequestModel(String orderid, String goodsName, int goodsnum, double total, String buyAddress) {
        this.orderid = orderid;
        this.goodsName = goodsName;
        this.goodsnum = goodsnum;
        this.total = total;
        this.buyAddress = buyAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getGoodsnum() {
        return goodsnum;
    }

    public void setGoodsnum(int goodsnum) {
        this.goodsnum = goodsnum;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getBuyAddress() {
        return buyAddress;
    }

    public void setBuyAddress(String buyAddress) {
        this.buyAddress = buyAddress;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }
}
