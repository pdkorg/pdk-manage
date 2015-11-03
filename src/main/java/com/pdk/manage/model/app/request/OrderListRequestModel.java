package com.pdk.manage.model.app.request;


import java.util.List;

/**
 * Created by 程祥 on 15/8/8.
 * Function：订单列表请求model
 */
public class OrderListRequestModel{
    private String userid;
    //订单状态
    private List<String> orderStatus;
    //业务流程状态
    private List<String> distState;
    private int pagesize;
    //开始条数
    private int currentpage;

    // 判断订单状态和配送状态是用and还是or 0为and，1为or
    private int andor;

    public int getAndor() {
        return andor;
    }

    public void setAndor(int andor) {
        this.andor = andor;
    }

    public List<String> getDistState() {
        return distState;
    }

    public void setDistState(List<String> distState) {
        this.distState = distState;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


    public List<String> getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(List<String> orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getCurrentpage() {
        return currentpage;
    }

    public void setCurrentpage(int currentpage) {
        this.currentpage = currentpage;
    }
}
