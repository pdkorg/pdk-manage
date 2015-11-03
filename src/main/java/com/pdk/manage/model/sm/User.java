package com.pdk.manage.model.sm;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pdk.manage.annotation.TableSearchColumn;
import com.pdk.manage.model.IBaseVO;
import com.pdk.manage.model.bd.Address;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Table(name = "pdk_sm_user")
public class User implements IBaseVO {
    @Id
    private String id;

    @Size(max = 50)
    @TableSearchColumn
    private String name;

    private Short type;

    @Size(max = 50)
    @TableSearchColumn
    private String realName;

    private Short sex;

    private Short age;

    @TableSearchColumn
    private String phone;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registerTime;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date unRegisterTime;

    private String headerImg;

    private Short status;

    private String eventKey;

    @Size(max=400)
    private String memo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ts;

    private Short dr;

    private String address;

    private String sourceId;

    @Transient
    private Address addr;

    @Transient
    private List<Address> addressList;

    @Transient
    private List<UserDescribe> describeList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public Short getSex() {
        return sex;
    }

    public void setSex(Short sex) {
        this.sex = sex;
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
        this.phone = phone == null ? null : phone.trim();
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
        this.headerImg = headerImg == null ? null : headerImg.trim();
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
        this.memo = memo == null ? null : memo.trim();
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public Address getAddr() {
        return addr == null ? new Address() : addr;
    }

    public void setAddr(Address addr) {
        this.addr = addr;
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
}