package com.pdk.manage.dao.flow;

import com.pdk.manage.dao.common.BaseDao;
import com.pdk.manage.model.flow.FlowTemplateInstanceUnit;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface FlowTemplateInstanceUnitDao extends BaseDao<FlowTemplateInstanceUnit> {

    List<FlowTemplateInstanceUnit> getFlowTemplateInstanceUnitList(String flowInstanceId);
}