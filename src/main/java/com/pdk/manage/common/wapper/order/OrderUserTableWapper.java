package com.pdk.manage.common.wapper.order;

import com.pdk.manage.common.wapper.AbstractDataTableQueryArgWapper;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 程祥 on 15/8/22.
 * Function：
 */
public class OrderUserTableWapper extends AbstractDataTableQueryArgWapper {

    @Override
    public List<String> getColNameList() {
        return Arrays.asList("","", "name", "realName", "sex", "age", "phone","registerTime","unRegisterTime");
    }
}
