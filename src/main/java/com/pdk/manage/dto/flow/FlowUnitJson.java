package com.pdk.manage.dto.flow;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by hubo on 2015/8/12.
 */
public class FlowUnitJson {

    @JsonProperty(value = "DT_RowId", index = 0)
    private String id;

    private String checked = "<input type='checkbox' class='checkboxes' value='1'/>";

    private int index;

    private String code;

    private String name;

    private Short orderStatus;

    private Short flowActionCode;

    private Short status;

    private String statusName;

    private String orderStatusName;

    private String flowActionCodeName;

    private String memo;

    private Date ts;

    public FlowUnitJson(int index, String id, String code, String name, Short orderStatus, Short flowActionCode, Short status, String memo, Date ts) {
        this.id = id;
        this.index = index;
        this.code = code;
        this.name = name;
        this.orderStatus = orderStatus;
        this.status = status;
        this.memo = memo;
        this.ts = ts;
        if(status == 0) {
            statusName = "启用";
        } else  if(status == 1){
            statusName = "禁用";
        }
        if(orderStatus != null) {
            switch (orderStatus) {
                case 0:
                    orderStatusName = "未指派";
                    break;
                case 1:
                    orderStatusName = "未定价";
                    break;
                case 2:
                    orderStatusName = "未完成";
                    break;
                case 3:
                    orderStatusName = "已完成";
                    break;
                case 4:
                    orderStatusName = "已取消";
                    break;
            }
        }

        if(flowActionCode != null) {
            switch (flowActionCode) {
                case 0:
                    flowActionCodeName = "指派";
                    break;
                case 1:
                    flowActionCodeName = "定价";
                    break;
                case 2:
                    flowActionCodeName = "支付";
                    break;
                case 3:
                    flowActionCodeName = "送达";
                    break;
            }
        }
    }

    public FlowUnitJson() {
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public Short getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Short orderStatus) {
        this.orderStatus = orderStatus;
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

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Short getFlowActionCode() {
        return flowActionCode;
    }

    public void setFlowActionCode(Short flowActionCode) {
        this.flowActionCode = flowActionCode;
    }

    public String getFlowActionCodeName() {
        return flowActionCodeName;
    }

    public void setFlowActionCodeName(String flowActionCodeName) {
        this.flowActionCodeName = flowActionCodeName;
    }
}
