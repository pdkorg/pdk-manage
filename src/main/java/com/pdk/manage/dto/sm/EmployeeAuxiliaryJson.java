package com.pdk.manage.dto.sm;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pdk.manage.model.sm.Employee;
import com.pdk.manage.model.sm.EmployeeAuxiliary;
import com.pdk.manage.util.DBConst;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liuhaiming on 2015/8/14.
 */
public class EmployeeAuxiliaryJson {

    @JsonProperty(value = "DT_RowId", index = 0)
    private String id;

    private String checked = "<input type='checkbox' class='checkboxes' value='1'/>";

    private int index;

    private String roleId;

    private String code;

    private String name;

    private String employeeId;

    private String memo;

    private Date ts;

    private String tsStr;

    private Short dr;

    public EmployeeAuxiliaryJson(int index, EmployeeAuxiliary employeeAuxiliary) {
        this.id = employeeAuxiliary.getId();
        this.index = index;
        this.code = employeeAuxiliary.getRole().getCode();
        this.name = employeeAuxiliary.getRole().getName();
        this.roleId = employeeAuxiliary.getRoleId();
        this.memo = employeeAuxiliary.getRole().getMemo();
        this.employeeId = employeeAuxiliary.getEmployeeId();
        this.dr = employeeAuxiliary.getDr();
        this.ts = employeeAuxiliary.getTs();
    }

    public EmployeeAuxiliaryJson() {
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
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

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
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

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(ts);
    }

    public void setTsStr(String tsStr) {
        this.tsStr = tsStr;
    }

    public Short getDr() {
        return dr;
    }

    public void setDr(Short dr) {
        this.dr = dr;
    }
}
