package com.pdk.manage.model.app.response;

/**
 * Created by 程祥 on 15/8/8.
 * Function：登陆状态响应model
 */
public class LoginResponseModel extends BaseResponseModel{
    private String imgurl;
    private String iwstatus = "0";
    private String ifinorder;

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getIwstatus() {
        return iwstatus;
    }

    public void setIwstatus(String iwstatus) {
        this.iwstatus = iwstatus;
    }

    public String getIfinorder() {
        return ifinorder;
    }

    public void setIfinorder(String ifinorder) {
        this.ifinorder = ifinorder;
    }
}
