package com.pdk.manage.model.order;

import java.math.BigDecimal;

/**
 * Created by 程祥 on 15/10/28.
 * Function：查询每日小跑君支出钱
 */
public class YwyMnyInfoModel {

    public YwyMnyInfoModel() {
    }

    public YwyMnyInfoModel(String hrefCode, BigDecimal mny, String ywyName, BigDecimal tipMny) {
        this.hrefCode = hrefCode;
        this.mny = mny;
        this.ywyName = ywyName;
        this.tipMny = tipMny;
    }

    private BigDecimal mny;
    private String orderCode;
    private String orderId;
    private String ywyName;
    private BigDecimal tipMny;
    private String hrefCode;

    public BigDecimal getMny() {
        return mny;
    }

    public void setMny(BigDecimal mny) {
        this.mny = mny;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getYwyName() {
        return ywyName;
    }

    public void setYwyName(String ywyName) {
        this.ywyName = ywyName;
    }

    public BigDecimal getTipMny() {
        return tipMny;
    }

    public void setTipMny(BigDecimal tipMny) {
        this.tipMny = tipMny;
    }

    public String getHrefCode() {
        String tbStr = "<a href=\"order/orderdetail/"+orderId+"?funcActiveCode=DETAIL\">"+orderCode+"</a>";
        if(orderId==null||orderId==""){
            tbStr = "";
        }

        return tbStr;
    }

    public void setHrefCode(String hrefCode) {
        this.hrefCode = hrefCode;
    }
}
