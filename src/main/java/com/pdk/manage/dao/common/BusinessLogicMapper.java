package com.pdk.manage.dao.common;

import com.pdk.manage.dao.common.provider.BusinessLogicMapperProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.mapping.MappedStatement;

import java.util.List;

/**
 * Created by liuhaiming on 2015/8/27.
 */
public interface BusinessLogicMapper<T> {
    @SelectProvider(type = BusinessLogicMapperProvider.class, method = "dynamicSQL")
    int refrencedCountSelect(@Param("refIds") List<String> refIds, @Param("refCol") String refCol);

    @SelectProvider(type = BusinessLogicMapperProvider.class, method = "dynamicSQL")
    int repeatCountSelect( @Param("vo") T vo );
}
