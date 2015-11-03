package com.pdk.manage.dto.sm;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pdk.manage.model.sm.Employee;
import com.pdk.manage.util.DBConst;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liuhaiming on 2015/8/14.
 */
public class EmployeeJson {

    @JsonProperty(value = "DT_RowId", index = 0)
    private String id;

    private String checked = "<input type='checkbox' class='checkboxes' value='1'/>";

    private int index;

    private String code;

    private String name;

    private String linkName;

    private double score;

    private String orgId;

    private String orgName;

    private String roleId;

    private String roleName;

    private String positionId;

    private String positionName;

    private Short sex;

    private String sexName;

    private String idCard;

    private String phone;

    private String password;

    private String headerImg;

    private Short status;

    private String statusName;

    private String memo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ts;

    private String tsStr;

    private Short dr;

    public EmployeeJson(int index, Employee employee) {
        this.id = employee.getId();
        this.index = index;
        this.code = employee.getCode();
        this.name = employee.getName();
        this.status = employee.getStatus();
        this.memo = employee.getMemo();
        this.ts = employee.getTs();
        this.orgId = employee.getOrgId();
        this.roleId = employee.getRoleId();
        this.positionId = employee.getPositionId();
        this.sex = employee.getSex();
        this.idCard = employee.getIdCard();
        this.phone = employee.getPhone();
        this.dr = employee.getDr();
        this.password = employee.getPassword();
        this.headerImg = employee.getHeaderImg();

        this.roleName = employee.getRole().getName();
        this.orgName = employee.getOrg().getName();
        this.positionName = (employee.getPosition() == null ? "" : employee.getPosition().getName());
        this.score = employee.getScore();

        if(status == null || status == 0) {
            statusName = "启用";
        } else  if(status == 1){
            statusName = "禁用";
        }

        if (sex == DBConst.SEX_MALE) {
            sexName = "男";
        } else if (sex == DBConst.SEX_FEMALE) {
            sexName = "女";
        } else {
            sexName = "未知";
        }

    }

    public EmployeeJson() {
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

    public String getLinkName() {
        return "<a onclick=\"showDetail('" + id + "')\">" + name + "</a>";
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
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

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
