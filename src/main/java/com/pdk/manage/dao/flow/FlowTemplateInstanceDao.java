package com.pdk.manage.dao.flow;

import com.pdk.manage.dao.common.BaseDao;
import com.pdk.manage.model.flow.FlowTemplateInstance;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface FlowTemplateInstanceDao extends BaseDao<FlowTemplateInstance> {
    List<FlowTemplateInstance> queryFlowTemplateInstances(List<String> flowInstanceIds);
}