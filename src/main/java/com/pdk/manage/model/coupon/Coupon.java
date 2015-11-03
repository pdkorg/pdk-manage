package com.pdk.manage.model.coupon;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pdk.manage.annotation.RelationColumn;
import com.pdk.manage.model.IBaseVO;
import com.pdk.manage.model.flow.FlowType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by liuhaiming on 2015/9/11.
 */
@Table(name = "pdk_coupon")
public class Coupon implements IBaseVO {
    @Id
    private String id;

    private String templateId;

    private String activityId;

    private String code;

    private String name;

    @RelationColumn(column="flow_type_id", relationKey="com.pdk.manage.model.flow.FlowType")
    private String flowTypeId;

    private BigDecimal sendMny;

    private BigDecimal minPayMny;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date actBeginDate;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date actEndDate;

    private String memo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ts;

    private Short dr;

    @Transient
    private FlowType flowType;

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

    public BigDecimal getSendMny() {
        return sendMny;
    }

    public void setSendMny(BigDecimal sendMny) {
        this.sendMny = sendMny;
    }

    public BigDecimal getMinPayMny() {
        return minPayMny;
    }

    public void setMinPayMny(BigDecimal minPayMny) {
        this.minPayMny = minPayMny;
    }

    public Date getActBeginDate() {
        return actBeginDate;
    }

    public void setActBeginDate(Date actBeginDate) {
        this.actBeginDate = actBeginDate;
    }

    public Date getActEndDate() {
        return actEndDate;
    }

    public void setActEndDate(Date actEndDate) {
        this.actEndDate = actEndDate;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public FlowType getFlowType() {
        return flowType;
    }

    public void setFlowType(FlowType flowType) {
        this.flowType = flowType;
    }
}
