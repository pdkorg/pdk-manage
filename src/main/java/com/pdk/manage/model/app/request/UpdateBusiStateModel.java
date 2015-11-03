package com.pdk.manage.model.app.request;

/**
 * Created by 程祥 on 15/8/8.
 * Function：修改流程状态model
 */
public class UpdateBusiStateModel {
    private String orderstate;
    private String ordermny;
    private String orderid;

    private int paytype;

    public UpdateBusiStateModel() {
    }

    public UpdateBusiStateModel(String orderstate, String orderid) {
        this.orderstate = orderstate;
        this.orderid = orderid;
    }

    public int getPaytype() {
        return paytype;
    }

    public void setPaytype(int paytype) {
        this.paytype = paytype;
    }

    public String getOrdermny() {
        return ordermny;
    }

    public void setOrdermny(String ordermny) {
        this.ordermny = ordermny;
    }

    public String getOrderstate() {
        return orderstate;
    }

    public void setOrderstate(String orderstate) {
        this.orderstate = orderstate;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }
}
