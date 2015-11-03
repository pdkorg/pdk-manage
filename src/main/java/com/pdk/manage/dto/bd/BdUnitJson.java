package com.pdk.manage.dto.bd;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pdk.manage.util.DBConst;

import java.util.Date;

/**
 * Created by liangyh on 2015/8/15.
 */
public class BdUnitJson {
    @JsonProperty(value = "DT_RowId", index = 0)
    private String id;

    private String checked = "<input type='checkbox' class='checkboxes' value='1'/>";

    private int index;

    private String code;

    private String name;

    private int status;

    private String statusName;

    private String memo;

    private Date ts;

    public BdUnitJson(int index, String id, String code, String name, int status, String memo, Date ts) {
        this.id = id;
        this.index = index;
        this.code = code;
        this.name = name;
        this.status = status;
        this.memo = memo;
        this.ts = ts;

        if(this.status == DBConst.STATUS_ENABLE) {
            statusName = "启用";
        } else  if(this.status == DBConst.STATUS_DISABLE){
            statusName = "禁用";
        }
    }

    public BdUnitJson() {
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Date getTs() {
        return ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
