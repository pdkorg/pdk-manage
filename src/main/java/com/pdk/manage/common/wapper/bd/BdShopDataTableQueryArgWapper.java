package com.pdk.manage.common.wapper.bd;

import com.pdk.manage.common.wapper.AbstractDataTableQueryArgWapper;

import java.util.Arrays;
import java.util.List;

/**
 * Created by liangyh on 2015/8/17.
 */
public class BdShopDataTableQueryArgWapper  extends AbstractDataTableQueryArgWapper {
    @Override
    public List<String> getColNameList() {
        return Arrays.asList("", "id", "code", "name","info", "status");
    }

}
