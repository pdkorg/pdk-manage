package com.pdk.manage.model.sm;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pdk.manage.model.IBaseVO;
import com.pdk.manage.model.sm.Employee;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by liuhaiming on 2015/9/9.
 */
@Table(name="pdk_message")
public class Message implements IBaseVO {
    @Id
    private String id;

    @NotNull
    private String employeeId;

    @Transient
    private Employee employee;

    private String frEmployeeId;

    @Transient
    private Employee frEmployee;

    @NotNull
    private Short type;

    @NotNull
    private String message;

    @NotNull
    private String isRead;
    @NotNull
    private String isLoad;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ts;

    private Short dr;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getFrEmployeeId() {
        return frEmployeeId;
    }

    public void setFrEmployeeId(String frEmployeeId) {
        this.frEmployeeId = frEmployeeId;
    }

    public Employee getFrEmployee() {
        return frEmployee;
    }

    public void setFrEmployee(Employee frEmployee) {
        this.frEmployee = frEmployee;
    }

    public String getIsLoad() {
        return isLoad;
    }

    public void setIsLoad(String isLoad) {
        this.isLoad = isLoad;
    }
}
