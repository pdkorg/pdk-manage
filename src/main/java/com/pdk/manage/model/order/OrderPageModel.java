package com.pdk.manage.model.order;

import java.util.List;

/**
 * Created by 程祥 on 15/9/9.
 * Function：
 */
public class OrderPageModel {

    /**
     * 总数
     */
    private long total;
    /**
     * 总页数
     */
    private int pages;
    private List<ChartRequestModel> orderAggModel;

    public OrderPageModel() {
    }

    public OrderPageModel(long total, int pages, List<ChartRequestModel> orderAggModel) {
        this.total = total;
        this.pages = pages;
        this.orderAggModel = orderAggModel;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<ChartRequestModel> getOrderAggModel() {
        return orderAggModel;
    }

    public void setOrderAggModel(List<ChartRequestModel> orderAggModel) {
        this.orderAggModel = orderAggModel;
    }
}
