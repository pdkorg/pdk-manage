package com.pdk.manage.common.wapper.flow;

import com.pdk.manage.common.wapper.AbstractDataTableQueryArgWapper;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hubo on 2015/8/12
 */
public class FlowTypeDataTableQueryArgWapper extends AbstractDataTableQueryArgWapper {
    @Override
    public List<String> getColNameList() {
        return Arrays.asList("", "id", "code", "name","isAutoAssignDeliver", "deliverId", "status");
    }
}
