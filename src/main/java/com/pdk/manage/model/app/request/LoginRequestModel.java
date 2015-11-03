package com.pdk.manage.model.app.request;

/**
 * Created by 程祥 on 15/8/8.
 * Function：登陆请求model
 */
public class LoginRequestModel extends BaseRequestModel{

    private String userpw;
    /**
     * 个推注册id
     */
    private String clientid;

    public String getUserpw() {
        return userpw;
    }

    public void setUserpw(String userpw) {
        this.userpw = userpw;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }
}
