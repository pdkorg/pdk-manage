package com.pdk.manage.dao.common;

import com.pdk.manage.dao.common.provider.BatchLogicDeleteMapperProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

/**
 * Created by hubo on 2015/8/27
 */
public interface BatchLogicDeleteMapper<T> {

    @UpdateProvider(type = BatchLogicDeleteMapperProvider.class, method = "dynamicSQL")
    int batchDelete(List<T> arr);

}
