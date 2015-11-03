package com.pdk.manage.model.app.request;

/**
 * Created by 程祥 on 15/8/8.
 * Function：用户个人报表请求model
 */
public class ReportRequestModel{

    private String userid;
    private String startDate;
    private String endDate;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
