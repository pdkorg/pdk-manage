package com.pdk.manage.dto.coupon;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pdk.manage.model.coupon.CouponActivity;
import com.pdk.manage.util.DBConst;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by liuhaiming on 2015/9/11.
 */
public class CouponActivityJson {
    private static final SimpleDateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

    @JsonProperty(value = "DT_RowId", index = 0)
    private String id;

    private String checked = "<input type='checkbox' class='checkboxes' value='1'/>";

    private int index;

    private String code;

    private String name;

    private String sendMessage;

    private  Short status;

    private String statusName;

    private String statusNameOnly;

    private String memo;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date autoSendTime;

    private String autoSendTimeStr;

    private Integer sendUserCnt;

    private BigDecimal sendTotalMny;

    private Date ts;

    private String tsStr;

    private String linkName;

    public CouponActivityJson(int index, CouponActivity coupon) {
        this.id = coupon.getId();
        this.index = index;
        this.code = coupon.getCode();
        this.name = coupon.getName();
        this.sendMessage = coupon.getSendMessage();
        this.status = coupon.getStatus();
        this.memo = coupon.getMemo();
        this.autoSendTime = coupon.getAutoSendTime();
        this.ts = coupon.getTs();
        this.sendUserCnt = coupon.getSendUserCnt();
        this.sendTotalMny = coupon.getSendTotalMny();

        if ( this.status == DBConst.COUPON_ACTIVITY_STATUS_AUTO_UNSTART ) {
            this.statusNameOnly = "未启动";
        } else if ( this.status == DBConst.COUPON_ACTIVITY_STATUS_AUTO_STARTING ) {
            this.statusNameOnly = "已启动";
        } else if ( this.status == DBConst.COUPON_ACTIVITY_STATUS_SENDED ) {
            this.statusNameOnly = "已派送";
        }

        this.statusName = statusNameOnly + "<input type=\"hidden\" name=\"status\" value=\"" + this.status + "\" >";
    }

    public CouponActivityJson() {
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

    public String getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(String sendMessage) {
        this.sendMessage = sendMessage;
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

    public String getTsStr() {
        if (ts == null) {
            return null;
        }

        return formatDateTime.format(ts);
    }

    public Date getAutoSendTime() {
        return autoSendTime;
    }

    public void setAutoSendTime(Date autoSendTime) {
        this.autoSendTime = autoSendTime;
    }

    public void setTsStr(String tsStr) {
        this.tsStr = tsStr;
    }

    public String getLinkName() {
        return "<a onclick=\"showDetail('" + id + "')\">" + name + "</a>";
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public Integer getSendUserCnt() {
        return sendUserCnt;
    }

    public void setSendUserCnt(Integer sendUserCnt) {
        this.sendUserCnt = sendUserCnt;
    }

    public BigDecimal getSendTotalMny() {
        return sendTotalMny;
    }

    public void setSendTotalMny(BigDecimal sendTotalMny) {
        this.sendTotalMny = sendTotalMny;
    }

    public String getAutoSendTimeStr() {
        if (autoSendTime == null) {
            return null;
        }

        return formatDate.format(autoSendTime);
    }

    public void setAutoSendTimeStr(String autoSendTimeStr) {
        this.autoSendTimeStr = autoSendTimeStr;
    }

    public String getStatusNameOnly() {
        return statusNameOnly;
    }

    public void setStatusNameOnly(String statusNameOnly) {
        this.statusNameOnly = statusNameOnly;
    }
}
