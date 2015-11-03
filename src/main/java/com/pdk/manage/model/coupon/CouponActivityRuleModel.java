package com.pdk.manage.model.coupon;

/**
 * Created by liuhaiming on 2015/9/11.
 */
public class CouponActivityRuleModel {
    private String sqlWhere;

    private String sqlHaving;

    private boolean isLinkOrder;

    public String getSqlWhere() {
        return sqlWhere;
    }

    public void setSqlWhere(String sqlWhere) {
        this.sqlWhere = sqlWhere;
    }

    public String getSqlHaving() {
        return sqlHaving;
    }

    public void setSqlHaving(String sqlHaving) {
        this.sqlHaving = sqlHaving;
    }

    public boolean isLinkOrder() {
        return isLinkOrder;
    }

    public void setIsLinkOrder(boolean isLinkOrder) {
        this.isLinkOrder = isLinkOrder;
    }
}
