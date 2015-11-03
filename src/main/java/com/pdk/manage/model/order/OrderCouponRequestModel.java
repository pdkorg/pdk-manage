package com.pdk.manage.model.order;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by 程祥 on 15/9/18.
 * Function：优惠券查询model
 */
public class OrderCouponRequestModel {
    //最小最大消费次数
    private Integer minNum;
    private Integer maxNum;
    //最小最大消费金额
    private BigDecimal minMny;
    private BigDecimal maxMny;
    //用户注册时间
    private Date registerTime;
    //订单类型id
    private String orderTypeId;
    //订单完成时间区间
    private Date startDate;
    private Date endDate;

    public OrderCouponRequestModel() {
    }

    public OrderCouponRequestModel(Integer minNum, Integer maxNum, BigDecimal minMny, BigDecimal maxMny, Date registerTime, String orderTypeId, Date startDate, Date endDate) {
        this.minNum = minNum;
        this.maxNum = maxNum;
        this.minMny = minMny;
        this.maxMny = maxMny;
        this.registerTime = registerTime;
        this.orderTypeId = orderTypeId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getMinNum() {
        return minNum;
    }

    public void setMinNum(Integer minNum) {
        this.minNum = minNum;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    public BigDecimal getMinMny() {
        return minMny;
    }

    public void setMinMny(BigDecimal minMny) {
        this.minMny = minMny;
    }

    public BigDecimal getMaxMny() {
        return maxMny;
    }

    public void setMaxMny(BigDecimal maxMny) {
        this.maxMny = maxMny;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(String orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
