package com.pdk.manage.common.wapper.bd;

import com.pdk.manage.common.wapper.AbstractDataTableQueryArgWapper;

import java.util.Arrays;
import java.util.List;

/**
 * Created by liangyh on 2015/8/15.
 */
public class BdUnitDataTableQueryArgWapper extends AbstractDataTableQueryArgWapper {

    @Override
    public List<String> getColNameList() {
        return Arrays.asList("", "", "code", "name", "status");
    }
}
