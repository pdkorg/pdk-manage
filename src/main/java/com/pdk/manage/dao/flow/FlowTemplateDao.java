package com.pdk.manage.dao.flow;

import com.pdk.manage.dao.common.BaseDao;
import com.pdk.manage.model.flow.FlowTemplate;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface FlowTemplateDao extends BaseDao<FlowTemplate> {

    FlowTemplate getFlowTemplate(String flowTypeId);

    int deleteFlowTemplateByFlowTypes(List<String> flowTypes);

}