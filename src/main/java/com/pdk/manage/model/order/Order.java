package com.pdk.manage.model.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pdk.manage.model.IBaseVO;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Table(name = "pdk_order_order")
public class Order implements IBaseVO {
    @Id
    private String id;

    private String code;

    @Column(name = "flowtype_id")
    private String flowtypeId;

    @Column(name = "ywy_id")
    private String ywyId;

    @Column(name = "user_id")
    private String userId;

    @Size(max = 200)
    private String adress;

    @Column(name = "delivery_status")
    private String deliveryStatus;

    @Column(name = "pay_type")
    private Short payType;

    @Column(name = "reserve_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reserveTime;

    private BigDecimal mny = new BigDecimal(0);

    @Column(name = "actual_mny")
    private BigDecimal actualMny = new BigDecimal(0);

    @Column(name = "coupon_mny")
    private BigDecimal couponMny = new BigDecimal(0);

    @Column(name = "fee_mny")
    private BigDecimal feeMny = new BigDecimal(0);

    @Column(name = "pay_status")
    private Short payStatus;

    @Column(name = "start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @Column(name = "end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @Column(name = "shop_manager_id")
    private String shopManagerId;

    @Column(name = "cs_id")
    private String csId;

    @NotNull
    private Short status;

    @Size(max = 400)
    private String memo;

    @Column(name = "flow_instance_id")
    private String flowInstanceId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ts;

    private Short dr;

    @Column(name = "is_flow_finish")
    private Short isFlowFinish;

    @Column(name="flow_action")
    private Short flowAction;

    public Short getFlowAction() {
        return flowAction;
    }

    public void setFlowAction(Short flowAction) {
        this.flowAction = flowAction;
    }

    public Short getIsFlowFinish() {
        return isFlowFinish;
    }

    public void setIsFlowFinish(Short isFlowFinish) {
        this.isFlowFinish = isFlowFinish;
    }

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getYwyId() {
        return ywyId;
    }

    public void setYwyId(String ywyId) {
        this.ywyId = ywyId;
    }

    /**
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * @return flowtype_id
     */
    public String getFlowtypeId() {
        return flowtypeId;
    }

    /**
     * @param flowtypeId
     */
    public void setFlowtypeId(String flowtypeId) {
        this.flowtypeId = flowtypeId == null ? null : flowtypeId.trim();
    }

    /**
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * @return adress
     */
    public String getAdress() {
        return adress;
    }

    /**
     * @param adress
     */
    public void setAdress(String adress) {
        this.adress = adress == null ? null : adress.trim();
    }

    /**
     * @return delivery_status
     */
    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    /**
     * @param deliveryStatus
     */
    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    /**
     * @return pay_type
     */
    public Short getPayType() {
        return payType;
    }

    /**
     * @param payType
     */
    public void setPayType(Short payType) {
        this.payType = payType;
    }

    /**
     * @return reserve_time
     */
    public Date getReserveTime() {
        return reserveTime;
    }

    /**
     * @param reserveTime
     */
    public void setReserveTime(Date reserveTime) {
        this.reserveTime = reserveTime;
    }

    /**
     * @return mny
     */
    public BigDecimal getMny() {
        return mny;
    }

    /**
     * @param mny
     */
    public void setMny(BigDecimal mny) {
        this.mny = mny;
    }

    public BigDecimal getFeeMny() {
        return feeMny;
    }

    public void setFeeMny(BigDecimal feeMny) {
        this.feeMny = feeMny;
    }

    /**
     * @return actual_mny
     */
    public BigDecimal getActualMny() {
        return actualMny;
    }

    /**
     * @param actualMny
     */
    public void setActualMny(BigDecimal actualMny) {
        this.actualMny = actualMny;
    }

    /**
     * @return coupon_mny
     */
    public BigDecimal getCouponMny() {
        return couponMny;
    }

    /**
     * @param couponMny
     */
    public void setCouponMny(BigDecimal couponMny) {
        this.couponMny = couponMny;
    }

    /**
     * @return pay_status
     */
    public Short getPayStatus() {
        return payStatus;
    }

    /**
     * @param payStatus
     */
    public void setPayStatus(Short payStatus) {
        this.payStatus = payStatus;
    }

    /**
     * @return start_time
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @return end_time
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @param endTime
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * @return shop_manager_id
     */
    public String getShopManagerId() {
        return shopManagerId;
    }

    /**
     * @param shopManagerId
     */
    public void setShopManagerId(String shopManagerId) {
        this.shopManagerId = shopManagerId == null ? null : shopManagerId.trim();
    }

    /**
     * @return cs_id
     */
    public String getCsId() {
        return csId;
    }

    /**
     * @param csId
     */
    public void setCsId(String csId) {
        this.csId = csId == null ? null : csId.trim();
    }

    /**
     * @return status
     */
    public Short getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * @return memo
     */
    public String getMemo() {
        return memo;
    }

    /**
     * @param memo
     */
    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    /**
     * @return flow_instance_id
     */
    public String getFlowInstanceId() {
        return flowInstanceId;
    }

    /**
     * @param flowInstanceId
     */
    public void setFlowInstanceId(String flowInstanceId) {
        this.flowInstanceId = flowInstanceId == null ? null : flowInstanceId.trim();
    }

    /**
     * @return ts
     */
    public Date getTs() {
        return ts;
    }

    /**
     * @param ts
     */
    public void setTs(Date ts) {
        this.ts = ts;
    }

    /**
     * @return dr
     */
    public Short getDr() {
        return dr;
    }

    /**
     * @param dr
     */
    public void setDr(Short dr) {
        this.dr = dr;
    }
}