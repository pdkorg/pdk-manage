package com.pdk.manage.dto.coupon;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pdk.manage.model.coupon.CouponActivity;
import com.pdk.manage.model.coupon.CouponActivityTemplate;
import com.pdk.manage.util.DBConst;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liuhaiming on 2015/9/11.
 */
public class CouponActivityTemplateJson {
    private static final SimpleDateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @JsonProperty(value = "DT_RowId", index = 0)
    private String id;

    private String checked = "<input type='checkbox' class='checkboxes' value='1'/>";

    private int index;

    private String code;

    private String name;

    private String memo;

    private Short status;

    private String statusName;

    private Date ts;

    private String tsStr;

    private String linkName;

    public CouponActivityTemplateJson(int index, CouponActivityTemplate coupon) {
        this.id = coupon.getId();
        this.index = index;
        this.code = coupon.getCode();
        this.name = coupon.getName();
        this.memo = coupon.getMemo();
        this.ts = coupon.getTs();
        this.status = coupon.getStatus();

        if ( status == DBConst.STATUS_ENABLE ) {
            statusName = "启用";
        } else if ( status == DBConst.STATUS_DISABLE ) {
            statusName = "停用";
        }
    }

    public CouponActivityTemplateJson() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getTs() {
        return ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }

    public String getLinkName() {
        return "<a onclick=\"showDetail('" + id + "')\">" + name + "</a>";
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getTsStr() {
        if (ts == null) {
            return null;
        }

        return formatDateTime.format(ts);
    }

    public void setTsStr(String tsStr) {
        this.tsStr = tsStr;
    }
}
