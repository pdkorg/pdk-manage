package com.pdk.manage.model.flow;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pdk.manage.annotation.RepeatColumn;
import com.pdk.manage.annotation.TableSearchColumn;
import com.pdk.manage.model.IBaseVO;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Table(name = "pdk_flow_flowtype")
public class FlowType implements IBaseVO, Serializable {

    @Id
    private String id;
    @NotNull
    @OrderBy
    @TableSearchColumn
    @RepeatColumn
    private String code;

    @NotNull
    @Size(max = 50)
    @TableSearchColumn
    @RepeatColumn
    private String name;

    @NotNull
    private Short status;

    @Size(max = 400)
    @TableSearchColumn
    private String memo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ts;

    private Short dr;

    @Column(name = "is_auto_assign_deliver")
    private String isAutoAssignDeliver;

    @Column(name = "deliver_id")
    @TableSearchColumn(column = "name", joinTable = "pdk_sm_employee")
    private String deliverId;

    @Transient
    private String deliverName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
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

    public String getIsAutoAssignDeliver() {
        return isAutoAssignDeliver;
    }

    public void setIsAutoAssignDeliver(String isAutoAssignDeliver) {
        this.isAutoAssignDeliver = isAutoAssignDeliver;
    }

    public String getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(String deliverId) {
        this.deliverId = deliverId;
    }

    public String getDeliverName() {
        return deliverName;
    }

    public void setDeliverName(String deliverName) {
        this.deliverName = deliverName;
    }

}