package com.pdk.manage.model.app.response;

/**
 * Created by 程祥 on 15/8/12.
 * Function： 订单详情中商品列表对象
 */

public class OrderGoodsModel{

    private String goodsname="";
    private double goodsnum;
    private double totalprice;
    private String goodsnote="";
    private String buyaddress="";
    //单位
    private String unit="";

    //前台传过来的数据状态，0为修改状态，1为新增，2为删除状态
    private int dataState;
    //删除标志 0为删除，1为有效
    private int flag=1;
    private String orderid="";
    private String id="";

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getDataState() {
        return dataState;
    }

    public void setDataState(int dataState) {
        this.dataState = dataState;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public double getGoodsnum() {
        return goodsnum;
    }

    public void setGoodsnum(double goodsnum) {
        this.goodsnum = goodsnum;
    }

    public double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }

    public String getGoodsnote() {
        return goodsnote;
    }

    public void setGoodsnote(String goodsnote) {
        this.goodsnote = goodsnote;
    }

    public String getBuyaddress() {
        return buyaddress;
    }

    public void setBuyaddress(String buyaddress) {
        this.buyaddress = buyaddress;
    }
}
