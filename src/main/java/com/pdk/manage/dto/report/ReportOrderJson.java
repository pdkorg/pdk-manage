package com.pdk.manage.dto.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pdk.manage.model.order.Order;
import com.pdk.manage.util.DBConst;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by liuhaiming on 2015/10/27.
 */
public class ReportOrderJson {
    private String checked = "<input type='checkbox' class='checkboxes' value='1'/>";

    private static final SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

    private int index;

    private int orderCount = 0;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;

    private BigDecimal totalMny = BigDecimal.ZERO;

    private BigDecimal useCouponMny = BigDecimal.ZERO;

    private BigDecimal feeMny = BigDecimal.ZERO;

    private BigDecimal payedMny = BigDecimal.ZERO;

    private int unPayedCount = 0;

    private String unPayedCountLink = null;

    private BigDecimal unPayedMny = BigDecimal.ZERO;

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

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalMny() {
        return totalMny;
    }

    public void setTotalMny(BigDecimal totalMny) {
        this.totalMny = totalMny;
    }

    public BigDecimal getUseCouponMny() {
        return useCouponMny;
    }

    public void setUseCouponMny(BigDecimal useCouponMny) {
        this.useCouponMny = useCouponMny;
    }

    public BigDecimal getFeeMny() {
        return feeMny;
    }

    public void setFeeMny(BigDecimal feeMny) {
        this.feeMny = feeMny;
    }

    public BigDecimal getPayedMny() {
        return payedMny;
    }

    public void setPayedMny(BigDecimal payedMny) {
        this.payedMny = payedMny;
    }

    public int getUnPayedCount() {
        return unPayedCount;
    }

    public void setUnPayedCount(int unPayedCount) {
        this.unPayedCount = unPayedCount;
    }

    public BigDecimal getUnPayedMny() {
        return unPayedMny;
    }

    public void setUnPayedMny(BigDecimal unPayedMny) {
        this.unPayedMny = unPayedMny;
    }

    public String getUnPayedCountLink() {
        return (unPayedCount == 0 ? Integer.toString(unPayedCount) : "<a onClick=\"showUnPayOrders('" + formatDate.format(this.orderDate) + "');\">" + unPayedCount + "</a>");
    }

    public void setUnPayedCountLink(String unPayedCountLink) {
        this.unPayedCountLink = unPayedCountLink;
    }

    public void appendMny(Order order) {
        orderCount++;
        Calendar cal = Calendar.getInstance();
        cal.setTime(order.getStartTime());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        this.orderDate = cal.getTime();

        totalMny = addMny(totalMny, order.getMny());
        useCouponMny = addMny(useCouponMny, order.getCouponMny());
        feeMny = addMny(feeMny, order.getFeeMny());
        if ( order.getPayStatus() == DBConst.ORDER_UNPAY) {
            unPayedMny = addMny(unPayedMny, order.getMny());
            unPayedCount++;
        } else {
            BigDecimal mny = subMny(order.getMny(), order.getCouponMny());
            mny = addMny(mny, order.getFeeMny());
            payedMny = addMny(payedMny, mny);
        }
    }

    public BigDecimal addMny(BigDecimal add, BigDecimal added) {
        add = ( add == null ? BigDecimal.ZERO : add );
        added = ( added == null ? BigDecimal.ZERO : added );

        return add.add(added);
    }
    public BigDecimal subMny(BigDecimal sub, BigDecimal sub2) {
        sub = ( sub == null ? BigDecimal.ZERO : sub );
        sub2 = ( sub2 == null ? BigDecimal.ZERO : sub2 );

        return sub.subtract(sub2);
    }
}
