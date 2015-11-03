package com.pdk.manage.service.flow;

import com.pdk.manage.dao.coupon.CouponDao;
import com.pdk.manage.dao.flow.FlowTemplateDao;
import com.pdk.manage.dao.flow.FlowTemplateUnitDao;
import com.pdk.manage.dao.flow.FlowTypeDao;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.exception.MessageBusinessException;
import com.pdk.manage.model.flow.FlowTemplate;
import com.pdk.manage.model.flow.FlowType;
import com.pdk.manage.model.sm.Employee;
import com.pdk.manage.service.BaseService;
import com.pdk.manage.service.sm.EmployeeService;
import com.pdk.manage.util.CommonUtil;
import com.pdk.manage.util.DBConst;
import com.pdk.manage.util.IdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hubo on 2015/8/12
 */
@Service
public class FlowTypeService extends BaseService<FlowType>{

    @Autowired
    private FlowTemplateDao flowTemplateDao;

    @Autowired
    private FlowTemplateUnitDao flowTemplateUnitDao;

    @Autowired
    private CouponDao couponDao;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public String getModuleCode() {
        return IdGenerator.FL_MODULE_CODE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized int save(FlowType entity) throws BusinessException {

        if(getDao().repeatCountSelect(entity) > 0) {
            throw new MessageBusinessException("编码或名称重复！");
        }

        int result = super.save(entity);

        FlowTemplate fl = new FlowTemplate();
        fl.setId(IdGenerator.generateId(getModuleCode()));
        fl.setDr(DBConst.DR_NORMAL);
        fl.setStatus(entity.getStatus());
        fl.setTs(entity.getTs());
        fl.setFlowTypeId(entity.getId());
        flowTemplateDao.insert(fl);

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(List<FlowType> entitys) throws BusinessException {

        if(entitys.size() == 0) {
            return 0;
        }

        Set<String> idSet = new HashSet<>();

        for (FlowType entity : entitys) {
            idSet.add(entity.getId());
        }

        List<String> idList = new ArrayList<>(idSet);

        if ( isRefrence(idList) ) {
            throw new MessageBusinessException("业务类型已经被优惠券引用，不能删除");
        }

        if(flowTemplateUnitDao.queryFlowTemplateUnitByFlowTypes(idList) > 0) {
            throw new MessageBusinessException("业务类型已经定义了流程环节，不能删除");
        }

        int result = super.delete(entitys);

        flowTemplateDao.deleteFlowTemplateByFlowTypes(idList);

        return result;
    }

    @Override
    public List<FlowType> selectAll(){
        return selectAll(null);
    }

    public List<FlowType> selectAll(Short status){
        FlowType f = new FlowType();
        f.setDr(DBConst.DR_NORMAL);
        f.setStatus(status);
        return dao.select(f);
    }

    public List<FlowType> getFlowTypeForOrder() throws BusinessException{
        return getDao().getFlowTypeForOrder();
    }

    public boolean isRefrence(List<String> ids) {
        return couponDao.refrencedCountSelect(ids, FlowType.class.getName()) > 0;
    }

    public FlowTypeDao getDao() {
        return (FlowTypeDao) this.dao;
    }

    @Override
    public FlowType get(String id) {

        FlowType flowType = super.get(id);

        if(CommonUtil.pdkBoolean(flowType.getIsAutoAssignDeliver()) && StringUtils.isNotEmpty(flowType.getDeliverId())) {
            Employee e = employeeService.get(flowType.getDeliverId());
            flowType.setDeliverName(e.getName());
        }

        return flowType;
    }

    @Override
    public int update(FlowType entity) throws BusinessException {
        if(getDao().repeatCountSelect(entity) > 0) {
            throw new MessageBusinessException("编码或名称重复！");
        }
        return super.update(entity);
    }
}

