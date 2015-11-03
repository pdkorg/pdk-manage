package com.pdk.manage.common.wapper.sm;

import com.pdk.manage.common.wapper.AbstractDataTableQueryArgWapper;

import java.util.Arrays;
import java.util.List;

/**
 * Created by liuhm on 2015/8/14
 */
public class SmEmployeeDataTableQueryArgWapper extends AbstractDataTableQueryArgWapper {
    private String qryCode;

    private String qryName;

    private String qryRoleIds;

    private String qryOrgId;

    @Override
    public List<String> getColNameList() {
        return Arrays.asList("", "id", "code", "name", "status", "o.name", "r.name", "p.name", "sex", "id_card", "phone", "memo");
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

    public String getQryRoleIds() {
        return qryRoleIds;
    }

    public void setQryRoleIds(String qryRoleIds) {
        this.qryRoleIds = qryRoleIds;
    }

    public String getQryOrgId() {
        return qryOrgId;
    }

    public void setQryOrgId(String qryOrgId) {
        this.qryOrgId = qryOrgId;
    }
}
