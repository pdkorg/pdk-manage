package com.pdk.manage.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;


import java.math.BigDecimal;

/**
 * Created by 程祥 on 15/8/19.
 * Function：
 */
public class OrderGoodsJson {
    @JsonProperty(value = "DT_RowId", index = 0)
    private String id;

    private int index;

    private String checked = "<input type='checkbox' class='checkboxes' value='1'/>";

    private String name;

    private double num;

    private String unitId;

    private String buyAdress;

    private BigDecimal goodsMny;

    private BigDecimal servicePrice;

    private BigDecimal otPrice;

    private String memo;


    public OrderGoodsJson(int index,String id, String name, double num,String unitId,String buyAdress, BigDecimal goodsMny, BigDecimal servicePrice, BigDecimal otPrice,String memo) {
        this.index = index;
        this.id = id;
        this.name = name;
        this.num = num;
        this.unitId = unitId;
        this.buyAdress = buyAdress;
        this.goodsMny = goodsMny;
        this.servicePrice = servicePrice;
        this.otPrice = otPrice;
        this.memo = memo;
    }


    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getBuyAdress() {
        return buyAdress;
    }

    public void setBuyAdress(String buyAdress) {
        this.buyAdress = buyAdress;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public BigDecimal getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(BigDecimal servicePrice) {
        this.servicePrice = servicePrice;
    }

    public BigDecimal getOtPrice() {
        return otPrice;
    }

    public void setOtPrice(BigDecimal otPrice) {
        this.otPrice = otPrice;
    }

    public BigDecimal getGoodsMny() {
        return goodsMny;
    }

    public void setGoodsMny(BigDecimal goodsMny) {
        this.goodsMny = goodsMny;
    }
}
