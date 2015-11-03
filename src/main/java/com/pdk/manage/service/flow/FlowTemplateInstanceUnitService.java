package com.pdk.manage.service.flow;

import com.pdk.manage.dao.flow.FlowTemplateInstanceUnitDao;
import com.pdk.manage.model.flow.FlowTemplateInstanceUnit;
import com.pdk.manage.service.BaseService;
import com.pdk.manage.util.DBConst;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hubo on 2015/8/28
 */
@Service
public class FlowTemplateInstanceUnitService extends BaseService<FlowTemplateInstanceUnit> {

    public List<FlowTemplateInstanceUnit> getFlowTemplateInstanceUnitList(String flowInstanceId) {
        return this.getDao().getFlowTemplateInstanceUnitList(flowInstanceId);
    }

    public FlowTemplateInstanceUnitDao getDao() {
        return (FlowTemplateInstanceUnitDao) this.dao;
    }

}
