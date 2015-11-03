package com.pdk.manage.model.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pdk.manage.model.IBaseVO;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Table(name = "pdk_order_orderdetail")
public class OrderDetail implements IBaseVO{
    @Id
    private String id;

    @Column(name = "order_id")
    private String orderId;

    @Size(max = 50)
    private String name;

    private Double num = 0.00;

    @Column(name = "unit_id")
    private String unitId;

    @Column(name = "buy_adress")
    private String buyAdress;

    //单价
    private BigDecimal price = new BigDecimal(0.00);

    //总价
    @Column(name = "goods_mny")
    private BigDecimal goodsMny = new BigDecimal(0.00);

    @Column(name = "service_mny")
    private BigDecimal serviceMny= new BigDecimal(0);

    @Column(name = "oth_mny")
    private BigDecimal othMny= new BigDecimal(0);

    @NotNull
    private Short status;

    @Size(max = 400)
    private String memo;

    @Column(name = "img_url")
    @Size(max = 200)
    private String imgUrl;

    @Column(name = "reserve_time")
    private String reserveTime;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ts;

    private Short dr;

    @Transient
    private String unitListHtml;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * @return order_id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * @param orderId
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return num
     */
    public Double getNum() {
        return num;
    }

    /**
     * @param num
     */
    public void setNum(Double num) {
        this.num = num;
    }

    /**
     * @return unit_id
     */
    public String getUnitId() {
        return unitId;
    }

    /**
     * @param unitId
     */
    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    /**
     * @return buy_adress
     */
    public String getBuyAdress() {
        return buyAdress;
    }

    /**
     * @param buyAdress
     */
    public void setBuyAdress(String buyAdress) {
        this.buyAdress = buyAdress == null ? null : buyAdress.trim();
    }

    /**
     * @return price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return service_mny
     */
    public BigDecimal getServiceMny() {
        return serviceMny;
    }

    /**
     * @param serviceMny
     */
    public void setServiceMny(BigDecimal serviceMny) {
        this.serviceMny = serviceMny;
    }

    /**
     * @return oth_mny
     */
    public BigDecimal getOthMny() {
        return othMny;
    }

    /**
     * @param othMny
     */
    public void setOthMny(BigDecimal othMny) {
        this.othMny = othMny;
    }

    /**
     * @return status
     */
    public Short getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * @return memo
     */
    public String getMemo() {
        return memo;
    }

    /**
     * @param memo
     */
    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    /**
     * @return ts
     */
    public Date getTs() {
        return ts;
    }

    /**
     * @param ts
     */
    public void setTs(Date ts) {
        this.ts = ts;
    }

    /**
     * @return dr
     */
    public Short getDr() {
        return dr;
    }

    /**
     * @param dr
     */
    public void setDr(Short dr) {
        this.dr = dr;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getReserveTime() {
        return reserveTime;
    }

    public void setReserveTime(String reserveTime) {
        this.reserveTime = reserveTime;
    }

    public String getUnitListHtml() {
        return unitListHtml;
    }

    public void setUnitListHtml(String unitListHtml) {
        this.unitListHtml = unitListHtml;
    }

    public BigDecimal getGoodsMny() {
        return goodsMny;
    }

    public void setGoodsMny(BigDecimal goodsMny) {
        this.goodsMny = goodsMny;
    }
}