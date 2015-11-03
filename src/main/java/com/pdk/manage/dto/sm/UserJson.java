package com.pdk.manage.dto.sm;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pdk.manage.model.bd.Address;
import com.pdk.manage.model.sm.User;
import com.pdk.manage.model.sm.UserDescribe;
import com.pdk.manage.util.DBConst;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by liuhaiming on 2015/8/14.
 */
public class UserJson {

    @JsonProperty(value = "DT_RowId", index = 0)
    private String id;

    private String checked = "<input type='checkbox' class='checkboxes' value='1'/>";

    private int index;

    private String name;

    private String linkName;

    private Short type;

    private String typeName;

    private String realName;

    private String fullAddress;

    private Short sex;

    private String sexName;

    private Short age;

    private String phone;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registerTime;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date unRegisterTime;

    private String headerImg;

    private Short status;
    private String statusName;

    private String memo;

    private String eventKey;

    private Date ts;

    private String tsStr;

    private String registerTimeStr;

    private String unRegisterTimeStr;

    private Short dr;

    private String sourceId;

    private List<Address> addressList;

    private List<UserDescribe> describeList;

    public UserJson(int index, User user) {
        this.id = user.getId();
        this.index = index;
        this.name = user.getName();
        this.type = user.getType();
        this.realName = user.getRealName();
        this.status = user.getStatus();
        this.memo = user.getMemo();
        this.ts = user.getTs();
        this.age = user.getAge();
        this.setSex(user.getSex());
        this.setPhone(user.getPhone());
        this.registerTime = user.getRegisterTime();
        this.unRegisterTime = user.getUnRegisterTime();
        this.fullAddress = user.getAddr().getFullAddress();
        this.sourceId = user.getSourceId();

        this.addressList = user.getAddressList();
        this.describeList = user.getDescribeList();

        if (status == DBConst.STATUS_ENABLE) {
            statusName = "启用";
        } else if (status == DBConst.STATUS_DISABLE){
            statusName = "禁用";
        } else if (status == DBConst.STATUS_UNATTENTION) {
            statusName = "取消关注";
        }

        if (sex == DBConst.SEX_MALE) {
            sexName = "男";
        } else if (sex == DBConst.SEX_FEMALE) {
            sexName = "女";
        } else {
            sexName = "未知";
        }

        if (type == DBConst.USER_TYPE_WEIXIN ) {
            typeName = "微信用户";
        } else if (type == DBConst.USER_TYPE_REGISTERED ) {
            typeName = "注册用户";
        }
    }

    public UserJson() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkName() {
        return "<a onclick=\"showDetail('" + id + "')\">" + name + "</a>";
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Short getSex() {
        return sex;
    }

    public void setSex(Short sex) {
        this.sex = sex;
    }

    public String getSexName() {
        return sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    public Short getAge() {
        return age;
    }

    public void setAge(Short age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if(phone == null) {
            this.phone = "";
        }else {
            this.phone = phone;
        }
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getUnRegisterTime() {
        return unRegisterTime;
    }

    public void setUnRegisterTime(Date unRegisterTime) {
        this.unRegisterTime = unRegisterTime;
    }

    public String getHeaderImg() {
        return headerImg;
    }

    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg;
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

    public Short getDr() {
        return dr;
    }

    public void setDr(Short dr) {
        this.dr = dr;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public List<UserDescribe> getDescribeList() {
        return describeList;
    }

    public void setDescribeList(List<UserDescribe> describeList) {
        this.describeList = describeList;
    }

    public String getTsStr() {
        if (ts == null) {
            return null;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(ts);
    }

    public void setTsStr(String tsStr) {
        this.tsStr = tsStr;
    }

    public String getRegisterTimeStr() {
        if (registerTime == null) {
            return null;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(registerTime);
    }

    public void setRegisterTimeStr(String registerTimeStr) {
        this.registerTimeStr = registerTimeStr;
    }

    public String getUnRegisterTimeStr() {
        if (unRegisterTime == null) {
            return null;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(unRegisterTime);
    }

    public void setUnRegisterTimeStr(String unRegisterTimeStr) {
        this.unRegisterTimeStr = unRegisterTimeStr;
    }
}
