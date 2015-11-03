package com.pdk.manage.common.wapper.report;

import com.pdk.manage.common.wapper.AbstractDataTableQueryArgWapper;

import java.util.List;

/**
 * Created by 程祥 on 15/10/28.
 * Function：
 */
public class ReportYwyMnyArgWapper extends AbstractDataTableQueryArgWapper {

    private String ywyName;
    private String queryDate;

    public String getYwyName() {
        return ywyName;
    }

    public void setYwyName(String ywyName) {
        this.ywyName = ywyName;
    }

    public String getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(String queryDate) {
        this.queryDate = queryDate;
    }

    @Override
    public List<String> getColNameList() {
        return null;
    }
}
