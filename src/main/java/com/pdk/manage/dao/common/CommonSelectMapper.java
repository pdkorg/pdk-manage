package com.pdk.manage.dao.common;

import com.pdk.manage.dao.common.provider.CommonSelectMapperProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * Created by hubo on 2015/8/27
 */
public interface CommonSelectMapper<T> {

    @SelectProvider(type = CommonSelectMapperProvider.class, method = "dynamicSQL")
    List<T> selectLike(@Param("searchValue")String searchValue);


    @SelectProvider(type = CommonSelectMapperProvider.class, method = "dynamicSQL")
    List<T> selectByIdList(List<String> idList);

}

