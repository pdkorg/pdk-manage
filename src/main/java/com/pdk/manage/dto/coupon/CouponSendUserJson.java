package com.pdk.manage.dto.coupon;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pdk.manage.model.coupon.CouponUserSended;
import com.pdk.manage.model.order.UserInfoForCoupon;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by liuhaiming on 2015/9/28.
 */
public class CouponSendUserJson {
    @JsonProperty(value = "DT_RowId", index = 0)
    private String id;

    private int index;

    private String code;

    private String name;

    private String realName;

    private BigDecimal payMny;

    private Integer payTimes;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date firstRegisterDate;

    public CouponSendUserJson(int index, UserInfoForCoupon userInfo) {
        this.index = index;
        this.id = userInfo.getUserId();
        this.name = userInfo.getName();
        this.realName = userInfo.getRealName();
        this.payTimes = userInfo.getTimes();
        this.payMny = userInfo.getPayMny();
        this.firstRegisterDate = userInfo.getRegisterTime();

    }

    public CouponSendUserJson(int index, CouponUserSended userInfo) {
        this.index = index;
        this.id = userInfo.getUserId();
        this.name = userInfo.getName();
        this.realName = userInfo.getRealName();
        this.payTimes = userInfo.getTimes();
        this.payMny = userInfo.getPayMny();
        this.firstRegisterDate = userInfo.getRegisterDate();

    }
    public CouponSendUserJson() {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public BigDecimal getPayMny() {
        return payMny;
    }

    public void setPayMny(BigDecimal payMny) {
        this.payMny = payMny;
    }

    public Integer getPayTimes() {
        return payTimes;
    }

    public void setPayTimes(Integer payTimes) {
        this.payTimes = payTimes;
    }

    public Date getFirstRegisterDate() {
        return firstRegisterDate;
    }

    public void setFirstRegisterDate(Date firstRegisterDate) {
        this.firstRegisterDate = firstRegisterDate;
    }
}
