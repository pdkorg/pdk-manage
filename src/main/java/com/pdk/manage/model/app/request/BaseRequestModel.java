package com.pdk.manage.model.app.request;

import java.io.Serializable;

/**
 * Created by chengxiang on 15/8/8.
 */
public class BaseRequestModel implements Serializable {
    protected String userid;
    protected String checkid;

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
}
