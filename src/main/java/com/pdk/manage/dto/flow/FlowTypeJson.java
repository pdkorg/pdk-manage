package com.pdk.manage.dto.flow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pdk.manage.model.flow.FlowType;
import com.pdk.manage.util.CommonUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hubo on 2015/8/12
 */
public class FlowTypeJson {

    @JsonProperty(value = "DT_RowId", index = 0)
    private String id;

    private String checked = "<input type='checkbox' class='checkboxes' value='1'/>";

    private int index;

    private String code;

    private String name;

    private int status;

    private String statusName;

    private String memo;

    private Date ts;

    private String deliverName;

    private String deliverId;

    private String isAutoAssignDeliver;

    public FlowTypeJson(int index, FlowType flowType) {
        this.id = flowType.getId();
        this.index = index;
        this.code = flowType.getCode();
        this.name = flowType.getName();
        this.status = flowType.getStatus();
        this.memo = flowType.getMemo();
        this.ts = flowType.getTs();
        this.deliverId = flowType.getDeliverId();
        this.deliverName = flowType.getDeliverName();
        if(status == 0) {
            statusName = "启用";
        } else  if(status == 1){
            statusName = "禁用";
        }

        if(CommonUtil.pdkBoolean(flowType.getIsAutoAssignDeliver())) {
            isAutoAssignDeliver = "是";
        } else {
            isAutoAssignDeliver = "否";
        }
    }

    public FlowTypeJson() {
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Date getTs() {
        return ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getDeliverName() {
        return deliverName;
    }

    public void setDeliverName(String deliverName) {
        this.deliverName = deliverName;
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
}
