package com.pdk.manage.service.flow;

import com.pdk.manage.BaseJunit4Test;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.flow.FlowTemplateInstance;
import com.pdk.manage.model.flow.FlowTemplateInstanceUnit;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hubo on 2015/8/28
 */
public class FlowTemplateInstanceServiceTest extends BaseJunit4Test{

    @Autowired
    private FlowTemplateInstanceService flowTemplateInstanceService;

    @Test
    @Transactional
    public void testGenerateFlowTemplateInstance() throws BusinessException {

//        String flowTypeId = "0001SM201508121532000001";
//
//        FlowTemplateInstance ins = flowTemplateInstanceService.generateFlowTemplateInstance(flowTypeId);
//
//        Assert.assertNotNull(ins);
//        Assert.assertNotNull(ins.getId());

    }

    @Test
    @Transactional
    public void testFinishCurrFlowUnit() throws BusinessException {

//        String flowInstanceId = "0001SM201508291659340002";
//
//        FlowTemplateInstanceUnit nextUnit = flowTemplateInstanceService.finishCurrFlowUnit(flowInstanceId);
//
//        Assert.assertNotNull(nextUnit);

    }

}
