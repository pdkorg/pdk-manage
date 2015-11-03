package com.pdk.manage.model.sm;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pdk.manage.annotation.RelationColumn;
import com.pdk.manage.annotation.RepeatColumn;
import com.pdk.manage.model.IBaseVO;
import com.pdk.manage.model.bd.Position;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Table(name = "pdk_sm_employee")
public class Employee implements IBaseVO{
    public static final Short ROLE_FIND_TYPE_MAIN = 0;
    public static final Short ROLE_FIND_TYPE_AUXILIARY = 1;
    public static final Short ROLE_FIND_TYPE_ALL = 2;

    @Id
    private String id;

    @NotNull
    @RepeatColumn
    private String code;

    private String name;

    @RelationColumn(column="org_id", relationKey="com.pdk.manage.model.sm.Org")
    private String orgId;

    @Transient
    private Org org;

    @RelationColumn(column = "role_id", relationKey="com.pdk.manage.model.sm.Role")
    private String roleId;

    @Transient
    private double score;

    @Transient
    private Role role;

    private Short sex;

    private String idCard;

    private String phone;

    private String password;

    private String headerImg;

    @NotNull
    private Short status;

    @Size(max=400)
    private String memo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ts;

    private Short dr;

    private String positionId;

    @Transient
    private Position position;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Org getOrg() {
        return org == null ? new Org() : org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Short getSex() {
        return sex;
    }

    public void setSex(Short sex) {
        this.sex = sex;
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

    public Role getRole() {
        return role == null ? new Role() : role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", orgId='" + orgId + '\'' +
                ", roleId='" + roleId + '\'' +
                ", sex=" + sex +
                ", idCard='" + idCard + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", headerImg='" + headerImg + '\'' +
                ", status=" + status +
                ", memo='" + memo + '\'' +
                ", ts=" + ts +
                ", dr=" + dr +
                ", positionId" + positionId +
                '}';
    }


}