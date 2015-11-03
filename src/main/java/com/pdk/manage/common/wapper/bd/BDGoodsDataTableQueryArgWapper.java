package com.pdk.manage.common.wapper.bd;

import com.pdk.manage.common.wapper.AbstractDataTableQueryArgWapper;

import java.util.Arrays;
import java.util.List;

/**
 * Created by liangyh on 2015/8/15.
 */
public class BDGoodsDataTableQueryArgWapper extends AbstractDataTableQueryArgWapper {
    private String qryCode;

    private String qryName;

    private String goodstypeid;
    @Override
    public List<String> getColNameList() {

        return Arrays.asList("", "id", "code", "name","r.name", "status");
    }

    public String getQryCode() {
        return qryCode;
    }

    public void setQryCode(String qryCode) {
        this.qryCode = qryCode;
    }

    public String getQryName() {
        return qryName;
    }

    public void setQryName(String qryName) {
        this.qryName = qryName;
    }

    public String getGoodstypeid() {
        return goodstypeid;
    }

    public void setGoodstypeid(String goodstypeid) {
        this.goodstypeid = goodstypeid;
    }
}
