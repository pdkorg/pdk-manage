package com.pdk.manage.model.coupon;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pdk.manage.model.IBaseVO;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by liuhaiming on 2015/9/11.
 */
@Table(name = "pdk_coupon_activity_rule")
public class CouponActivityRule implements IBaseVO {
    @Id
    private String id;

    private String activityId;

    /** and， or */
    private Short connectType;

    private Short ruleType;

    private Short optType;

    private String chkVal;

    private String parentId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ts;

    private Short dr;

    @Transient
    private String chkValName;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public Short getConnectType() {
        return connectType;
    }

    public void setConnectType(Short connectType) {
        this.connectType = connectType;
    }

    public Short getRuleType() {
        return ruleType;
    }

    public void setRuleType(Short ruleType) {
        this.ruleType = ruleType;
    }

    public Short getOptType() {
        return optType;
    }

    public void setOptType(Short optType) {
        this.optType = optType;
    }

    public String getChkVal() {
        return chkVal;
    }

    public void setChkVal(String chkVal) {
        this.chkVal = chkVal;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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

    public String getChkValName() {
        return chkValName;
    }

    public void setChkValName(String chkValName) {
        this.chkValName = chkValName;
    }
}
