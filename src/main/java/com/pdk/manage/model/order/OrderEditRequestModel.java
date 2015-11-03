package com.pdk.manage.model.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.zip.DataFormatException;

/**
 * Created by 程祥 on 15/8/26.
 * Function：
 */
public class OrderEditRequestModel {
    private String orderid;

    private String userid;
    private String ywyid;
    private String busiType;
    private String ordercode;
    private String ordertype;
    private String orderaddress;
    private String paytype;
    private String reservetime;
    private String ordermny;
    private String realcostmny;
    private String onsalemny;
    private String feemny;
    private String payState;
    private String memo;
    private String startTime;
    private String endtime;
    private String managerid;
    private String kefuid;

    private Long ts;

    private String bodydata;

    public String getBodydata() {
        return bodydata;
    }

    public void setBodydata(String bodydata) {
        this.bodydata = bodydata;
    }

    public String getUserid() {
        return userid;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getYwyid() {
        return ywyid;
    }

    public void setYwyid(String ywyid) {
        this.ywyid = ywyid;
    }

    public String getFeemny() {
        return feemny;
    }

    public void setFeemny(String feemny) {
        this.feemny = feemny;
    }

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public String getOrdercode() {
        return ordercode;
    }

    public void setOrdercode(String ordercode) {
        this.ordercode = ordercode;
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    public String getOrderaddress() {
        return orderaddress;
    }

    public void setOrderaddress(String orderaddress) {
        this.orderaddress = orderaddress;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getReservetime() {
        return reservetime;
    }

    public void setReservetime(String reservetime) {
        this.reservetime = reservetime;
    }

    public String getOrdermny() {
        return ordermny;
    }

    public void setOrdermny(String ordermny) {
        this.ordermny = ordermny;
    }

    public String getRealcostmny() {
        return realcostmny;
    }

    public void setRealcostmny(String realcostmny) {
        this.realcostmny = realcostmny;
    }

    public String getOnsalemny() {
        return onsalemny;
    }

    public void setOnsalemny(String onsalemny) {
        this.onsalemny = onsalemny;
    }

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getManagerid() {
        return managerid;
    }

    public void setManagerid(String managerid) {
        this.managerid = managerid;
    }

    public String getKefuid() {
        return kefuid;
    }

    public void setKefuid(String kefuid) {
        this.kefuid = kefuid;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }
}
