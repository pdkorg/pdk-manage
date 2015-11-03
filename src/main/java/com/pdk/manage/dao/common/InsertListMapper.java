package com.pdk.manage.dao.common;

import com.pdk.manage.dao.common.provider.InsertListMapperProvider;
import org.apache.ibatis.annotations.InsertProvider;

import java.util.List;

/**
 * Created by hubo on 2015/8/28.
 */
public interface InsertListMapper<T> {

    /**
     * 批量插入，支持数据库自增字段，支持回写
     *
     * @param recordList
     * @return
     */
    @InsertProvider(type = InsertListMapperProvider.class, method = "dynamicSQL")
    int insertList(List<T> recordList);
}
