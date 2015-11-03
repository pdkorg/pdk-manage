package com.pdk.manage.common.wapper.order;

import com.pdk.manage.common.wapper.AbstractDataTableQueryArgWapper;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 程祥 on 15/8/17.
 * Function：
 */
public class OrderDataTableQueryArgWapper extends AbstractDataTableQueryArgWapper {

    @Override
    public List<String> getColNameList() {
        return Arrays.asList("", "id", "code", "flowtype_id", "name","real_name","phone","adress","pay_type","delivery_status","start_time");
    }
}
