package com.pdk.manage.common.wapper.coupon;

import com.pdk.manage.common.wapper.AbstractDataTableQueryArgWapper;

import java.util.Arrays;
import java.util.List;

/**
 * Created by liuhm on 2015/9/11
 */
public class CouponSendDataTableQueryArgWapper extends AbstractDataTableQueryArgWapper {
    @Override
    public List<String> getColNameList() {
            return Arrays.asList("", "id", "code", "name", "a.name", "begin_date", "end_date", "status", "memo");
    }
}
