package com.pdk.manage.dao.bd;

import com.pdk.manage.dao.common.BaseDao;
import com.pdk.manage.model.bd.Unit;
import com.pdk.manage.model.flow.FlowType;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
@Repository
public interface
        UnitDao  extends BaseDao<Unit> {

}