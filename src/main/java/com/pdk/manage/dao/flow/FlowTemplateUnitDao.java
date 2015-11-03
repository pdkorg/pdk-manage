package com.pdk.manage.dao.flow;

import com.pdk.manage.dao.common.BaseDao;
import com.pdk.manage.model.flow.FlowTemplateInstanceUnit;
import com.pdk.manage.model.flow.FlowTemplateUnit;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface FlowTemplateUnitDao extends BaseDao<FlowTemplateUnit> {

    Short queryMaxIq(String templateId);

    int queryFlowTemplateUnitByFlowTypes(List<String> flowTypes);

    int queryFlowTemplateUnitByFlowUnits(List<String> flowUnits);

    List<FlowTemplateUnit> queryTemplateUnitByRoles(List<String> roleIds);
}