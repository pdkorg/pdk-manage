package com.pdk.manage.common.wapper.coupon;

/**
 * Created by liuhm on 2015/8/30
 */
public class CouponPageQueryWapper {
    private int pageNo = 1;

    private int pageSize = 5;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
