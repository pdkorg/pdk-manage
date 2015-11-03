package com.pdk.manage.common.wapper.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pdk.manage.common.wapper.AbstractDataTableQueryArgWapper;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by liuhm on 2015/8/14
 */
public class ReportOrderDataTableQueryArgWapper extends AbstractDataTableQueryArgWapper {
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fromDate;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date toDate;

    @Override
    public List<String> getColNameList() {
        return null;
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
}
