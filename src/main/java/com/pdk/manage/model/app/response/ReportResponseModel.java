package com.pdk.manage.model.app.response;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 程祥 on 15/8/16.
 * Function：订单统计分析报表
 */
public class ReportResponseModel extends BaseResponseModel{
    private BigDecimal totalmny;
    private BigDecimal onlingpaymny;
    private BigDecimal cashpaymny;
    private BigDecimal tipmny;
    //业务员实际支出（帮用户买东西，用户选择在线支付，业务员代垫金额）
    private BigDecimal expendmny;

    private List<OrderInfoResponseModel> list;

    public List<OrderInfoResponseModel> getList() {
        return list;
    }

    public void setList(List<OrderInfoResponseModel> list) {
        this.list = list;
    }

    public BigDecimal getTotalmny() {
        return totalmny;
    }

    public void setTotalmny(BigDecimal totalmny) {
        this.totalmny = totalmny;
    }

    public BigDecimal getOnlingpaymny() {
        return onlingpaymny;
    }

    public void setOnlingpaymny(BigDecimal onlingpaymny) {
        this.onlingpaymny = onlingpaymny;
    }

    public BigDecimal getCashpaymny() {
        return cashpaymny;
    }

    public void setCashpaymny(BigDecimal cashpaymny) {
        this.cashpaymny = cashpaymny;
    }

    public BigDecimal getTipmny() {
        return tipmny;
    }

    public void setTipmny(BigDecimal tipmny) {
        this.tipmny = tipmny;
    }

    public BigDecimal getExpendmny() {
        return expendmny;
    }

    public void setExpendmny(BigDecimal expendmny) {
        this.expendmny = expendmny;
    }
}
