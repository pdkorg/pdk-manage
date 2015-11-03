package com.pdk.manage.model.order;

import java.util.List;

/**
 * Created by 程祥 on 15/9/10.
 * Function：
 */
public class ShowOrderPageModel {

    private String rst_code;
    private String rst_mess;

    /**
     * 总数
     */
    private long total;
    /**
     * 总页数
     */
    private int pages;
    /**
     * 页码，从1开始
     */
    private int pageNum;
    /**
     * 页面大小
     */
    private int pageSize;

    private List<ShowOrderAggVO> orderAggModel;

    public ShowOrderPageModel() {
    }

    public ShowOrderPageModel(long total, int pages, List<ShowOrderAggVO> orderAggModel) {
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

    public List<ShowOrderAggVO> getOrderAggModel() {
        return orderAggModel;
    }

    public void setOrderAggModel(List<ShowOrderAggVO> orderAggModel) {
        this.orderAggModel = orderAggModel;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getRst_code() {
        return rst_code;
    }

    public void setRst_code(String rst_code) {
        this.rst_code = rst_code;
    }

    public String getRst_mess() {
        return rst_mess;
    }

    public void setRst_mess(String rst_mess) {
        this.rst_mess = rst_mess;
    }
}
