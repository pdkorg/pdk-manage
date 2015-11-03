package com.pdk.manage.service.flow;

import com.pdk.manage.dao.flow.FlowTemplateUnitDao;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.flow.FlowTemplateUnit;
import com.pdk.manage.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hubo on 2015/8/17
 */
@Service
public class FlowTemplateUnitService extends BaseService<FlowTemplateUnit>{

    @Autowired
    private FlowTemplateUnitDao flowTemplateUnitDao;

    @Override
    public int save(FlowTemplateUnit entity) throws BusinessException {
        Short iq = getDao().queryMaxIq(entity.getTemplateId());
        entity.setIq((iq == null ? (short)1 : (short)(iq + 1)));
        return super.save(entity);
    }

    public FlowTemplateUnitDao getDao() {
        return (FlowTemplateUnitDao)this.dao;
    }

    public List<FlowTemplateUnit> queryTemplateUnitByRoles(List<String> roleIds) throws BusinessException{
        return flowTemplateUnitDao.queryTemplateUnitByRoles(roleIds);
    }
}
