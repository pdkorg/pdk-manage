package com.pdk.manage.model.order;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.pdk.manage.model.IBaseVO;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by 程祥 on 15/8/19.
 * Function：用于界面展示
 */
public class OrderTable implements IBaseVO{

    //记录id，用于向界面传输
    @JsonProperty(value = "DT_RowId", index = 0)
    private String rowId;

    private String checked = "<input type='checkbox' class='checkboxes' value='1'/>";

    private int index;

    private String id;

    private String hrefCode;

    private String blankHrefCode;

    private String code;

    private String flowtypeId;

    private String userId;

    private String adress;

    private String deliveryStatus;

    private Short isFlowFinish;

    private Short payType;

    private String reserveTime;

    private BigDecimal mny = new BigDecimal(0);

    private BigDecimal actualMny = new BigDecimal(0);

    private BigDecimal couponMny = new BigDecimal(0);

    private BigDecimal feeMny = new BigDecimal(0);

    private Short payStatus;

    private Short flowAction;

    private String startTime;

    private String endTime;

    private String shopManagerId;

    private String csId;

    private Short status;

    private String memo;

    private String flowInstanceId;

    private Date ts;

    private Long tsIden;

    private Short dr;

    private String ywyId;

    //订单类型
    private String flowTypeName;
    //微信昵称
    private String nickname;
    //客户真实名称
    private String realname;
    //客户电话号码
    private String phonenum;
    //性别
    private Short userSex;
    //性别名称
    private String userSexName;
    //配送状态名称
    private String buisTypeName;
    //店长姓名
    private String leaderName;
    //客服姓名
    private String waitertName;
    //订单状态名称
    private String statusName;
    //支付类型名称
    private String payTypeName;
    //是否支付名称
    private String payStatusName;
    //业务员姓名
    private String ywyName;
    //业务员电话
    private String ywyPhone;

    //订单评价id
    private String reviewId;

    //是否已经定价
    private boolean isPriced;

    public boolean isPriced() {
        return isPriced;
    }

    public void setIsPriced(boolean isPriced) {
        this.isPriced = isPriced;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public Short getFlowAction() {
        return flowAction;
    }

    public void setFlowAction(Short flowAction) {
        this.flowAction = flowAction;
    }

    public Short getIsFlowFinish() {
        return isFlowFinish;
    }

    public void setIsFlowFinish(Short isFlowFinish) {
        this.isFlowFinish = isFlowFinish;
    }

    public String getYwyId() {
        return ywyId;
    }

    public void setYwyId(String ywyId) {
        this.ywyId = ywyId;
    }

    public String getYwyName() {
        return ywyName;
    }

    public void setYwyName(String ywyName) {
        this.ywyName = ywyName;
    }

    public String getYwyPhone() {
        return ywyPhone;
    }

    public void setYwyPhone(String ywyPhone) {
        this.ywyPhone = ywyPhone;
    }

    public String getPayStatusName() {
        return payStatusName;
    }

    public void setPayStatusName(String payStatusName) {
        this.payStatusName = payStatusName;
    }

    public String getPayTypeName() {
        return payTypeName;
    }

    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public BigDecimal getFeeMny() {
        return feeMny;
    }

    public void setFeeMny(BigDecimal feeMny) {
        this.feeMny = feeMny;
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
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

    public String getFlowTypeName() {
        return flowTypeName;
    }

    public void setFlowTypeName(String flowTypeName) {
        this.flowTypeName = flowTypeName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getBuisTypeName() {
        return buisTypeName;
    }

    public void setBuisTypeName(String buisTypeName) {
        this.buisTypeName = buisTypeName;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getWaitertName() {
        return waitertName;
    }

    public void setWaitertName(String waitertName) {
        this.waitertName = waitertName;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFlowtypeId() {
        return flowtypeId;
    }

    public void setFlowtypeId(String flowtypeId) {
        this.flowtypeId = flowtypeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Short getPayType() {
        return payType;
    }

    public void setPayType(Short payType) {
        this.payType = payType;
    }

    public String getReserveTime() {
        return reserveTime==null?"":reserveTime;
    }

    public void setReserveTime(String reserveTime) {
        this.reserveTime = reserveTime;
    }

    public BigDecimal getMny() {
        return mny;
    }

    public void setMny(BigDecimal mny) {
        this.mny = mny;
    }

    public BigDecimal getActualMny() {
        return actualMny;
    }

    public void setActualMny(BigDecimal actualMny) {
        this.actualMny = actualMny;
    }

    public BigDecimal getCouponMny() {
        return couponMny;
    }

    public void setCouponMny(BigDecimal couponMny) {
        this.couponMny = couponMny;
    }

    public Short getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Short payStatus) {
        this.payStatus = payStatus;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getShopManagerId() {
        return shopManagerId;
    }

    public void setShopManagerId(String shopManagerId) {
        this.shopManagerId = shopManagerId;
    }

    public String getCsId() {
        return csId;
    }

    public void setCsId(String csId) {
        this.csId = csId;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getFlowInstanceId() {
        return flowInstanceId;
    }

    public void setFlowInstanceId(String flowInstanceId) {
        this.flowInstanceId = flowInstanceId;
    }

    public Short getUserSex() {
        return userSex;
    }

    public void setUserSex(Short userSex) {
        this.userSex = userSex;
    }

    public String getUserSexName() {
        return userSexName;
    }

    public void setUserSexName(String userSexName) {
        this.userSexName = userSexName;
    }

    @Override
    public Date getTs() {
        return ts;
    }

    @Override
    public void setTs(Date ts) {
        this.ts = ts;
    }

    @Override
    public Short getDr() {
        return dr;
    }

    @Override
    public void setDr(Short dr) {
        this.dr = dr;
    }

    //target="_self"
    public String getHrefCode() {
        String tbStr = "<a href=\"order/orderdetail/"+id+"?funcActiveCode=DETAIL\">"+code+"</a>";
        return tbStr;
    }

    public void setHrefCode(String hrefCode) {
        this.hrefCode = hrefCode;
    }

    //target="_blank"
    public String getBlankHrefCode() {
        String tbStr = "<a target=\"_blank\" href=\"order/orderdetail/"+id+"?funcActiveCode=DETAIL\">"+code+"</a>";
        return tbStr;
    }

    public void setBlankHrefCode(String blankHrefCode) {
        this.blankHrefCode = blankHrefCode;
    }

    public Long getTsIden() {
        return ts==null?new Date().getTime():ts.getTime();
    }

    public void setTsIden(Long tsIden) {
        this.tsIden = tsIden;
    }
}
