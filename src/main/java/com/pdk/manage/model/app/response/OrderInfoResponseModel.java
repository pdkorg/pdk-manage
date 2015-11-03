package com.pdk.manage.model.app.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 程祥 on 15/8/8.
 * Function：订单详情响应model
 */
public class OrderInfoResponseModel implements Serializable {

    private String orderid;
    private String ordercode;
    //业务类型（订单类型）
    private String busTypeCode;
    private String busTypeName;
    //流程实例id
    private String instanceid;
    private String cusaddress;
    //下单时间
    private String ordertime;
    //预定时间
    private String orderPreTime;
    private String buyaddress;
    //订单状态
    private String status;
    //订单流程状态
    private String orderState;
    private String orderStateName;
    private String orderamount;
    private String ipaytype;
    private String isFinished;
    private String flowAction;

    private String cusname;
    private String custell;
    private String ordernotes;

    public String getFlowAction() {
        return flowAction;
    }

    public void setFlowAction(String flowAction) {
        this.flowAction = flowAction;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ts;

    public Date getTs() {
        return ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }

    public String getInstanceid() {
        return instanceid;
    }

    public void setInstanceid(String instanceid) {
        this.instanceid = instanceid;
    }

    public String getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(String isFinished) {
        this.isFinished = isFinished;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderPreTime() {
        return orderPreTime;
    }

    public void setOrderPreTime(String orderPreTime) {
        this.orderPreTime = orderPreTime;
    }

    public String getOrderStateName() {
        return orderStateName;
    }

    public void setOrderStateName(String orderStateName) {
        this.orderStateName = orderStateName;
    }

    public String getBusTypeName() {
        return busTypeName;
    }

    public void setBusTypeName(String busTypeName) {
        this.busTypeName = busTypeName;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getBusTypeCode() {
        return busTypeCode;
    }

    public void setBusTypeCode(String busTypeCode) {
        this.busTypeCode = busTypeCode;
    }

    public String getCusaddress() {
        return cusaddress;
    }

    public void setCusaddress(String cusaddress) {
        this.cusaddress = cusaddress;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public String getBuyaddress() {
        return buyaddress;
    }

    public void setBuyaddress(String buyaddress) {
        this.buyaddress = buyaddress;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getOrderamount() {
        return orderamount;
    }

    public void setOrderamount(String orderamount) {
        this.orderamount = orderamount;
    }

    public String getIpaytype() {
        return ipaytype;
    }

    public void setIpaytype(String ipaytype) {
        this.ipaytype = ipaytype;
    }

    public String getCusname() {
        return cusname;
    }

    public void setCusname(String cusname) {
        this.cusname = cusname;
    }

    public String getCustell() {
        return custell;
    }

    public void setCustell(String custell) {
        this.custell = custell;
    }

    public String getOrdernotes() {
        return ordernotes;
    }

    public void setOrdernotes(String ordernotes) {
        this.ordernotes = ordernotes;
    }

    public String getOrdercode() {
        return ordercode;
    }

    public void setOrdercode(String ordercode) {
        this.ordercode = ordercode;
    }
}
