package com.pdk.manage.model.app.response;

import java.math.BigDecimal;

/**
 * Created by 程祥 on 15/8/16.
 * Function：查询报表数据库所需要用到的
 */
public class ReportSumInfoModel {

    private BigDecimal totalmny;

    private Short paytype;

    //业务员额外支出金额 = 用户在线支付订单金额+小费
    private BigDecimal tipmny;

    public BigDecimal getTotalmny() {
        return totalmny;
    }

    public void setTotalmny(BigDecimal totalmny) {
        this.totalmny = totalmny;
    }

    public Short getPaytype() {
        return paytype;
    }

    public void setPaytype(Short paytype) {
        this.paytype = paytype;
    }

    public BigDecimal getTipmny() {
        return tipmny;
    }

    public void setTipmny(BigDecimal tipmny) {
        this.tipmny = tipmny;
    }
}
