package com.pdk.manage.dao.flow;

import com.pdk.manage.dao.common.BaseDao;
import com.pdk.manage.dao.common.BusinessLogicMapper;
import com.pdk.manage.model.flow.FlowType;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface FlowTypeDao extends BaseDao<FlowType>, BusinessLogicMapper<FlowType> {

    List<FlowType> getFlowTypeForOrder();

}