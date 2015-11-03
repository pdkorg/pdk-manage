package com.pdk.manage.common.wapper.bd;

import com.pdk.manage.common.wapper.AbstractDataTableQueryArgWapper;

import java.util.Arrays;
import java.util.List;

/**
 * Created by liuhm on 2015/8/30
 */
public class BdAddressDataTableQueryArgWapper extends AbstractDataTableQueryArgWapper {
    @Override
    public List<String> getColNameList() {
            return Arrays.asList("", "", "receiver", "a.full_address", "is_default");
    }
}
