package com.pdk.manage.service.flow;

import com.pdk.manage.dao.flow.FlowTemplateDao;
import com.pdk.manage.dao.flow.FlowTemplateUnitDao;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.flow.FlowTemplate;
import com.pdk.manage.model.flow.FlowTemplateUnit;
import com.pdk.manage.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hubo on 2015/8/17
 */
@Service
public class FlowTemplateService extends BaseService<FlowTemplate> {

    private static final Logger log = LoggerFactory.getLogger(FlowTemplateService.class);

    @Autowired
    private FlowTemplateUnitService flowTemplateUnitService;

    @Autowired
    private FlowTemplateDao flowTemplateDao;

    public FlowTemplate getFlowTemplate(String flowTypeId) {

        log.info("flowTypeId : {}", flowTypeId);

        FlowTemplate flowTemplate = flowTemplateDao.getFlowTemplate(flowTypeId);

        log.info("flowTemplate : {}", flowTemplate);

        return flowTemplate;
    }

    @Transactional(rollbackFor = Exception.class)
    public void upFlowTemplateUnit(String flowTemplateUnitId) throws BusinessException {

        FlowTemplateUnit unit = flowTemplateUnitService.get(flowTemplateUnitId);

        FlowTemplate template = getFlowTemplate(unit.getFlowTypeId());

        List<FlowTemplateUnit> flowTemplateUnits = template.getFlowTemplateUnitList();

        int currIndex = 0;

        for (int i = 0; i < flowTemplateUnits.size(); i++  ) {
            currIndex = i;
            if(flowTemplateUnits.get(i).getId().equals(flowTemplateUnitId)) {
                break;
            }
        }

        if(currIndex - 1 >= 0) {
            FlowTemplateUnit preUnit = flowTemplateUnits.get(currIndex - 1);
            short tempIq = unit.getIq();
            unit.setIq(preUnit.getIq());
            preUnit.setIq(tempIq);
            flowTemplateUnitService.update(preUnit);
            flowTemplateUnitService.update(unit);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void downFlowTemplateUnit(String flowTemplateUnitId) throws BusinessException {

        FlowTemplateUnit unit = flowTemplateUnitService.get(flowTemplateUnitId);

        FlowTemplate template = getFlowTemplate(unit.getFlowTypeId());

        List<FlowTemplateUnit> flowTemplateUnits = template.getFlowTemplateUnitList();

        int currIndex = 0;

        for (int i = 0; i < flowTemplateUnits.size(); i++  ) {
            currIndex = i;
            if(flowTemplateUnits.get(i).getId().equals(flowTemplateUnitId)) {
                break;
            }
        }

        if(currIndex + 1 < flowTemplateUnits.size()) {
            FlowTemplateUnit nextUnit = flowTemplateUnits.get(currIndex + 1);
            short tempIq = unit.getIq();
            unit.setIq(nextUnit.getIq());
            nextUnit.setIq(tempIq);
            flowTemplateUnitService.update(nextUnit);
            flowTemplateUnitService.update(unit);
        }

    }

}
