package com.pdk.manage.dao.common;

import tk.mybatis.mapper.common.Mapper;

/**
 * Created by hubo on 2015/8/27
 */
public interface BaseDao<T> extends Mapper<T>, BatchLogicDeleteMapper<T>, CommonSelectMapper<T>, InsertListMapper<T> {

}
