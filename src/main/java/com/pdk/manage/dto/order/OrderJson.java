package com.pdk.manage.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by 程祥 on 15/8/18.
 * Function：
 */
public class OrderJson {

    @JsonProperty(value = "DT_RowId", index = 0)
    private String id;

    private String checked = "<input type='checkbox' class='checkboxes' value='1'/>";

    private int index;

    private String code;

    private String flowtypeId;
    private String flowtypeName;

    //微信昵称
    private String csId;
    //用户姓名
    private String userId;
    //联系电话？谁的联系电话？
    private String tel;

    private String address;

    private Short paytype;
    private String paytypeName;

    //配送状态
    private Short status;
    private String statusName;
    //预约时间
    private Date reserveTime;

    private Date ts;

    public OrderJson() {
    }

    public OrderJson(int index,String id, String code, String flowtypeId, String csId, String userId, String tel, String address, Short paytype, Short status, Date reserveTime, Date ts) {
        this.id = id;
        this.index = index;
        this.code = code;
        this.flowtypeId = flowtypeId;
        this.csId = csId;
        this.userId = userId;
        this.tel = tel;
        this.address = address;
        this.paytype = paytype;
        //TODO 测试用
        this.paytypeName = "微信支付a";
        this.status = status;
        this.statusName="到店a";
        this.reserveTime = reserveTime;
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

    public String getFlowtypeId() {
        return flowtypeId;
    }

    public void setFlowtypeId(String flowtypeId) {
        this.flowtypeId = flowtypeId;
    }

    public String getCsId() {
        return csId;
    }

    public void setCsId(String csId) {
        this.csId = csId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Short getPaytype() {
        return paytype;
    }

    public void setPaytype(Short paytype) {
        this.paytype = paytype;
    }

    public String getPaytypeName() {
        return paytypeName;
    }

    public void setPaytypeName(String paytypeName) {
        this.paytypeName = paytypeName;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Date getReserveTime() {
        return reserveTime;
    }

    public void setReserveTime(Date reserveTime) {
        this.reserveTime = reserveTime;
    }

    public Date getTs() {
        return ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }
}
