package com.pdk.manage.dto.report;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

/**
 * Created by liuhaiming on 2015/10/27.
 */
public class ReportIndexJson {
    private int orderCount = 0;

    private int feeOrderCount = 0;

    private int payedOrderCount = 0;

    private int newUserCount = 0;

    private BigDecimal mny = BigDecimal.ZERO;

    private BigDecimal feeMny = BigDecimal.ZERO;

    private BigDecimal payedMny = BigDecimal.ZERO;

    private BigDecimal unPayedMny = BigDecimal.ZERO;

    private BigDecimal couponMny = BigDecimal.ZERO;

    private BigDecimal payedMnyPersent = BigDecimal.ZERO;

    private BigDecimal payedPersent = BigDecimal.ZERO;

    private BigDecimal feePersent = BigDecimal.ZERO;

    private BigDecimal userKeepPersent = BigDecimal.ZERO;

    public BigDecimal getFeePersent() {
        return feePersent;
    }

    public int getPayedOrderCount() {
        return payedOrderCount;
    }

    public void setPayedOrderCount(int payedOrderCount) {
        this.payedOrderCount = payedOrderCount;
    }

    public void setFeePersent(BigDecimal feePersent) {
        this.feePersent = feePersent;
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

    public int getNewUserCount() {
        return newUserCount;
    }

    public void setNewUserCount(int newUserCount) {
        this.newUserCount = newUserCount;
    }

    public BigDecimal getMny() {
        return mny;
    }

    public void setMny(BigDecimal mny) {
        this.mny = mny;
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

    public BigDecimal getPayedPersent() {
        return payedPersent;
    }

    public void setPayedPersent(BigDecimal payedPersent) {
        this.payedPersent = payedPersent;
    }

    public BigDecimal getPayedMnyPersent() {
        return payedMnyPersent;
    }

    public void setPayedMnyPersent(BigDecimal payedMnyPersent) {
        this.payedMnyPersent = payedMnyPersent;
    }

    public BigDecimal getUserKeepPersent() {
        return userKeepPersent;
    }

    public void setUserKeepPersent(BigDecimal userKeepPersent) {
        this.userKeepPersent = userKeepPersent;
    }

    public BigDecimal getCouponMny() {
        return couponMny;
    }

    public void setCouponMny(BigDecimal couponMny) {
        this.couponMny = couponMny;
    }
}
