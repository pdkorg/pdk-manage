package com.pdk.manage.common.wapper.bd;

import com.pdk.manage.common.wapper.AbstractDataTableQueryArgWapper;

import java.util.Arrays;
import java.util.List;

/**
 * Created by liuhm on 2015/8/30
 */
public class BdQuickReplyDataTableQueryArgWapper extends AbstractDataTableQueryArgWapper {
    @Override
    public List<String> getColNameList() {
            return Arrays.asList("", "id", "info");
    }
}
