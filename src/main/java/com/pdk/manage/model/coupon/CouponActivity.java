package com.pdk.manage.model.coupon;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pdk.manage.annotation.TableSearchColumn;
import com.pdk.manage.model.IBaseVO;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by liuhaiming on 2015/9/11.
 */
@Table(name = "pdk_coupon_activity")
public class CouponActivity implements IBaseVO {
    @Id
    private String id;

    @TableSearchColumn
    private String code;

    @TableSearchColumn
    private String name;

    private  Short status;

    @TableSearchColumn
    private String sendMessage;

    @TableSearchColumn
    private String memo;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date autoSendTime;

    private Integer sendUserCnt;

    private BigDecimal sendTotalMny;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ts;

    private Short dr;

    @Transient
    private List<Coupon> coupons;

    @Transient
    private List<CouponActivityRule> rules;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(String sendMessage) {
        this.sendMessage = sendMessage;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getAutoSendTime() {
        return autoSendTime;
    }

    public void setAutoSendTime(Date autoSendTime) {
        this.autoSendTime = autoSendTime;
    }

    public Integer getSendUserCnt() {
        return sendUserCnt;
    }

    public void setSendUserCnt(Integer sendUserCnt) {
        this.sendUserCnt = sendUserCnt;
    }

    public BigDecimal getSendTotalMny() {
        return sendTotalMny;
    }

    public void setSendTotalMny(BigDecimal sendTotalMny) {
        this.sendTotalMny = sendTotalMny;
    }

    @Override
    public Date getTs() {
        return ts;
    }

    @Override
    public void setTs(Date ts) {
        this.ts = ts;
    }

    @Override
    public Short getDr() {
        return dr;
    }

    @Override
    public void setDr(Short dr) {
        this.dr = dr;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    public List<CouponActivityRule> getRules() {
        return rules;
    }

    public void setRules(List<CouponActivityRule> rules) {
        this.rules = rules;
    }
}
