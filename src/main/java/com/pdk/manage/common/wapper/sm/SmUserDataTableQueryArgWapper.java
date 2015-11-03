package com.pdk.manage.common.wapper.sm;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pdk.manage.common.wapper.AbstractDataTableQueryArgWapper;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by liuhm on 2015/8/14
 */
public class SmUserDataTableQueryArgWapper extends AbstractDataTableQueryArgWapper {
    private Short qryType;

    private String qryName;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fromDate;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date toDate;

    private String qryAddr;

    @Override
    public List<String> getColNameList() {
        return Arrays.asList("","", "name", "type", "real_name", "sex", "age", "phone","a.full_address", "status", "register_time","un_register_time");
    }

    public Short getQryType() {
        return qryType;
    }

    public void setQryType(Short qryType) {
        this.qryType = qryType;
    }

    public String getQryName() {
        return qryName;
    }

    public void setQryName(String qryName) {
        this.qryName = qryName;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getQryAddr() {
        return qryAddr;
    }

    public void setQryAddr(String qryAddr) {
        this.qryAddr = qryAddr;
    }
}
