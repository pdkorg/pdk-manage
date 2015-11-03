package com.pdk.manage.model.order;

/**
 * Created by 程祥 on 15/10/17.
 * Function： 用于显示订单跟踪的数量
 */
public class CountNumModel {
    public CountNumModel() {
    }

    public CountNumModel(String id, int num) {
        this.id = id;
        this.num = num;
    }

    public CountNumModel(String id, int num, String flowTypeId) {
        this.id = id;
        this.num = num;
        this.flowTypeId = flowTypeId;
    }

    private String id;
    private int num;
    private String flowTypeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getFlowTypeId() {
        return flowTypeId;
    }

    public void setFlowTypeId(String flowTypeId) {
        this.flowTypeId = flowTypeId;
    }
}
