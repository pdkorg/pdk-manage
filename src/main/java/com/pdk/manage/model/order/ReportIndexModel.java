package com.pdk.manage.model.order;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

/**
 * Created by liuhaiming on 2015/10/27.
 */
public class ReportIndexModel {
    private static final SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

    private int orderCount = 0;

    private int feeOrderCount = 0;

    private int payedOrderCount = 0;

    private BigDecimal mny = BigDecimal.ZERO;

    private BigDecimal couponMny = BigDecimal.ZERO;

    private BigDecimal feeMny = BigDecimal.ZERO;

    private BigDecimal payedMny = BigDecimal.ZERO;

    private BigDecimal unPayedMny = BigDecimal.ZERO;

    public int getPayedOrderCount() {
        return payedOrderCount;
    }

    public void setPayedOrderCount(int payedOrderCount) {
        this.payedOrderCount = payedOrderCount;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public int getFeeOrderCount() {
        return feeOrderCount;
    }

    public void setFeeOrderCount(int feeOrderCount) {
        this.feeOrderCount = feeOrderCount;
    }

    public BigDecimal getMny() {
        return mny;
    }

    public void setMny(BigDecimal mny) {
        this.mny = mny;
    }

    public BigDecimal getCouponMny() {
        return couponMny;
    }

    public void setCouponMny(BigDecimal couponMny) {
        this.couponMny = couponMny;
    }

    public BigDecimal getFeeMny() {
        return feeMny;
    }

    public void setFeeMny(BigDecimal feeMny) {
        this.feeMny = feeMny;
    }

    public BigDecimal getPayedMny() {
        return payedMny;
    }

    public void setPayedMny(BigDecimal payedMny) {
        this.payedMny = payedMny;
    }

    public BigDecimal getUnPayedMny() {
        return unPayedMny;
    }

    public void setUnPayedMny(BigDecimal unPayedMny) {
        this.unPayedMny = unPayedMny;
    }
}
