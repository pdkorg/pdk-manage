package com.pdk.manage.model.app;

import java.io.Serializable;

/**
 * Created by 程祥 on 15/8/9.
 * Function： 存在session中的值
 */
public class CheckInfoModel implements Serializable {

    //
    private String userid;
    //用来处理业务的
    private String userCode;
    private String checkid;
    private String clientid;

    //经纬度
    private String latitude;
    private String longitude;

    //工作状态
    private int workState = 0;

    public CheckInfoModel() {
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public CheckInfoModel(String userid, String checkid, String clientid) {
        this.userid = userid;
        this.checkid = checkid;
        this.clientid = clientid;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCheckid() {
        return checkid;
    }

    public void setCheckid(String checkid) {
        this.checkid = checkid;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getWorkState() {
        return workState;
    }

    public void setWorkState(int workState) {
        this.workState = workState;
    }
}
