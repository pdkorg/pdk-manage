package com.pdk.manage.dto.coupon;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pdk.manage.model.coupon.CouponActivity;
import com.pdk.manage.model.coupon.CouponUserRelation;
import com.pdk.manage.util.DBConst;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liuhaiming on 2015/9/11.
 */
public class CouponJson {
    @JsonProperty(value = "DT_RowId", index = 0)
    private String id;

    private String checked = "<input type='checkbox' class='checkboxes' value='1'/>";

    private int index;

    private String couponId;

    private String userId;

    private String sourceId;

    private Short status;

    private String code;

    private String name;

    private String flowTypeId;

    private String flowTypeName;

    private BigDecimal mny;

    private BigDecimal minPayMny;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private Date ts;

    private Short dr;

    public CouponJson(int index, CouponUserRelation relation) {
        this.id = relation.getId();
        this.index = index;
        this.couponId = relation.getCouponId();
        this.userId = relation.getUserId();
        this.sourceId = relation.getSourceId();
        this.status = relation.getStatus();
        this.mny = relation.getSendMny();
        this.minPayMny = relation.getMinPayMny();
        this.beginDate = relation.getBeginDate();
        this.endDate = relation.getEndDate();
        this.ts = relation.getTs();
        this.dr = relation.getDr();

        this.code = relation.getCode();
        this.name = relation.getName();
        this.flowTypeId = relation.getFlowType().getId();
        this.flowTypeName = relation.getFlowType().getName();
    }

    public CouponJson() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
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

    public String getFlowTypeId() {
        return flowTypeId;
    }

    public void setFlowTypeId(String flowTypeId) {
        this.flowTypeId = flowTypeId;
    }

    public BigDecimal getMny() {
        return mny;
    }

    public void setMny(BigDecimal mny) {
        this.mny = mny;
    }

    public BigDecimal getMinPayMny() {
        return minPayMny;
    }

    public void setMinPayMny(BigDecimal minPayMny) {
        this.minPayMny = minPayMny;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getTs() {
        return ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }

    public Short getDr() {
        return dr;
    }

    public void setDr(Short dr) {
        this.dr = dr;
    }

    public String getFlowTypeName() {
        return flowTypeName;
    }

    public void setFlowTypeName(String flowTypeName) {
        this.flowTypeName = flowTypeName;
    }
}
