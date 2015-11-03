package com.pdk.manage.service.flow;

import com.pdk.manage.dao.flow.FlowTemplateUnitDao;
import com.pdk.manage.dao.flow.FlowUnitDao;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.exception.MessageBusinessException;
import com.pdk.manage.model.flow.FlowUnit;
import com.pdk.manage.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hubo on 2015/8/13
 */
@Service
public class FlowUnitService extends BaseService<FlowUnit> {

    @Autowired
    private FlowTemplateUnitDao flowTemplateUnitDao;

    @Autowired
    private FlowUnitDao flowUnitDao;

    @Override
    public int delete(List<FlowUnit> entitys) throws BusinessException {

        if(entitys.size() == 0) {
            return 0;
        }

        Set<String> idSet = new HashSet<>();

        for (FlowUnit entity : entitys) {
            idSet.add(entity.getId());
        }

        List<String> idList = new ArrayList<>(idSet);

        if(flowTemplateUnitDao.queryFlowTemplateUnitByFlowUnits(idList) > 0) {
            throw new MessageBusinessException("当前流程环节已经被流程模板引用，不能删除");
        }

        return super.delete(entitys);
    }

    public List<FlowUnit> getFlowUnitsByRoleIds(List<String> roleIds) throws BusinessException{
        return flowUnitDao.selectFlowUnitByRoleIds(roleIds);
    }

    @Override
    public int save(FlowUnit entity) throws BusinessException {
        if(getDao().repeatCountSelect(entity) > 0) {
            throw new MessageBusinessException("编码或名称重复！");
        }
        return super.save(entity);
    }

    private FlowUnitDao getDao() {
        return (FlowUnitDao) this.dao;
    }

    @Override
    public int update(FlowUnit entity) throws BusinessException {
        if(getDao().repeatCountSelect(entity) > 0) {
            throw new MessageBusinessException("编码或名称重复！");
        }
        return super.update(entity);
    }
}
