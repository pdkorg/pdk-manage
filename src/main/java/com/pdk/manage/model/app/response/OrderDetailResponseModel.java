package com.pdk.manage.model.app.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pdk.manage.model.order.OrderDetail;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Created by 程祥 on 15/8/12.
 * Function：订单详情模型
 */
public class OrderDetailResponseModel extends BaseResponseModel{

    public OrderDetailResponseModel() {
    }
    public OrderDetailResponseModel(OrderInfoResponseModel infomodel) {
        this.busTypeCode = infomodel.getBusTypeCode();
        this.busTypeName = infomodel.getBusTypeName();
        this.buyaddress = infomodel.getBuyaddress();
        this.cusaddress = infomodel.getCusaddress();
        this.cusname = infomodel.getCusname();
        this.ipaytype = infomodel.getIpaytype();
        this.orderamount= infomodel.getOrderamount();
        this.orderid = infomodel.getOrderid();
        this.ordercode = infomodel.getOrdercode();
        this.isFinished = infomodel.getIsFinished();
        this.ordernotes = infomodel.getOrdernotes();
        this.orderState  = infomodel.getOrderState();
        this.status = infomodel.getStatus();
        this.ordertime = infomodel.getOrdertime();
        this.orderPreTime = infomodel.getOrderPreTime();
        this.custell = infomodel.getCustell();
        this.ts = infomodel.getTs();
        this.flowAction = infomodel.getFlowAction();
    }
    //表体，service查询的时候赋值
    private List<OrderDetail> list;

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
    private String[][] distarr;

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


    public String getOrdercode() {
        return ordercode;
    }

    public void setOrdercode(String ordercode) {
        this.ordercode = ordercode;
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

    public String[][] getDistarr() {
        return distarr;
    }

    public void setDistarr(String[][] distarr) {
        this.distarr = distarr;
    }

    public String getOrderStateName() {
        return orderStateName;
    }

    public void setOrderStateName(String orderStateName) {
        this.orderStateName = orderStateName;
    }

    public List<OrderDetail> getList() {
        return list;
    }

    public void setList(List<OrderDetail> list) {
        this.list = list;
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

    public String getBusTypeName() {
        return busTypeName;
    }

    public void setBusTypeName(String busTypeName) {
        this.busTypeName = busTypeName;
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

}
