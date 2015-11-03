package com.pdk.manage.action.flow;

import com.pdk.manage.dto.flow.FlowTemplateUnitJson;
import com.pdk.manage.dto.flow.FlowTypeTreeNodeJson;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.exception.MessageBusinessException;
import com.pdk.manage.model.flow.FlowTemplate;
import com.pdk.manage.model.flow.FlowTemplateUnit;
import com.pdk.manage.model.flow.FlowType;
import com.pdk.manage.service.flow.FlowTemplateService;
import com.pdk.manage.service.flow.FlowTemplateUnitService;
import com.pdk.manage.service.flow.FlowTypeService;
import com.pdk.manage.util.DBConst;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * Created by hubo on 2015/8/15
 */
@Controller
@RequestMapping("/flow")
public class FlowTemplateAction {

    private static final Logger log = LoggerFactory.getLogger(FlowTemplateAction.class);

    @Autowired
    private FlowTypeService flowTypeService;

    @Autowired
    private FlowTemplateService flowTemplateService;

    @Autowired
    private FlowTemplateUnitService flowTemplateUnitService;

    @RequestMapping("/flow_templates")
    public String index() {
        return "flow/flow_template";
    }

    @ModelAttribute
    public void getFlowTemplateUnit(@RequestParam(value = "id", required = false) String id, Map<String, Object> map) {
        if(StringUtils.isNotEmpty(id)) {
            map.put("flowTemplateUnit", flowTemplateUnitService.get(id));
        }
    }

    @RequestMapping("/flow_template/flow_type_tree")
    @ResponseBody
    public List<Object> loadFlowTypeTree() {

        List<Object> result = new ArrayList<>();

        FlowTypeTreeNodeJson root = new FlowTypeTreeNodeJson("root", "业务类型");

        try {
            for (FlowType flowType : flowTypeService.selectAll(DBConst.STATUS_ENABLE)) {
                FlowTypeTreeNodeJson node = new FlowTypeTreeNodeJson(flowType.getId(), flowType.getCode() + "    " + flowType.getName());
                root.addChild(node);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        result.add(root);

        return result;

    }

    @RequestMapping(value = "/flow_template/{flowTypeId}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getFlowTemplate(@PathVariable String flowTypeId, String draw) {

        Map<String, Object> result = new HashMap<>();

        FlowTemplate flowTemplate = flowTemplateService.getFlowTemplate(flowTypeId);

        List<FlowTemplateUnit> flowTemplateUnits = flowTemplate.getFlowTemplateUnitList();

        List<FlowTemplateUnitJson> data = new ArrayList<>();

        int index = 1;

        for (FlowTemplateUnit flowTemplateUnit : flowTemplateUnits) {

            data.add(new FlowTemplateUnitJson(flowTemplateUnit.getId(), index, flowTemplateUnit.getFlowUnit().getName(), flowTemplateUnit.getRole().getName(), flowTemplateUnit.getIsPushMsg(), flowTemplateUnit.getMemo(), flowTemplateUnit.getTs()));

            index++;

        }

        result.put("draw", draw);
        result.put("templateId", flowTemplate.getId());
        result.put("recordsTotal", data.size());
        result.put("recordsFiltered", data.size());

        result.put("data", data);

        return result;
    }


    @RequestMapping(value = "/flow_template/flow_template_unit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@Valid FlowTemplateUnit flowTemplateUnit, Errors errors) {

        Map<String, Object> result = new HashMap<>();

        if(errors.getErrorCount() > 0) {
            result.put("result", "error");
            for (FieldError err : errors.getFieldErrors()) {
                result.put("err_" + err.getField(), err.getDefaultMessage());
            }
            return result;
        }

        try {
            flowTemplateUnitService.save(flowTemplateUnit);
            result.put("result", "success");
        } catch (MessageBusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
        }

        return result;
    }

    @RequestMapping(value = "/flow_template/flow_template_unit", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> update(@Valid FlowTemplateUnit flowTemplateUnit, Errors errors) {

        Map<String, Object> result = new HashMap<>();

        if(errors.getErrorCount() > 0) {
            result.put("result", "error");
            for (FieldError err : errors.getFieldErrors()) {
                result.put("err_" + err.getField(), err.getDefaultMessage());
            }
            return result;
        }

        try {
            flowTemplateUnitService.update(flowTemplateUnit);
            result.put("result", "success");
        } catch (MessageBusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
        }

        return result;
    }

    @RequestMapping(value = "/flow_template/flow_template_unit/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> get(@PathVariable("id") String id) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", "success");
        result.put("data", flowTemplateUnitService.get(id));
        return result;
    }

    @RequestMapping(value = "/flow_template/flow_template_unit", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> batchDelete(@RequestParam("ids") String[] ids, @RequestParam(value = "ts", required = false) Long[] ts) {

        Map<String, Object> result = new HashMap<>();

        List<FlowTemplateUnit> flowTemplateUnitListList = new ArrayList<>();

        for (int i = 0; i < ids.length; i++) {
            FlowTemplateUnit f = new FlowTemplateUnit();
            f.setId(ids[i]);
            if(ts != null && ts[i] != null) {
                f.setTs(new Date(ts[i]));
            }
            flowTemplateUnitListList.add(f);
        }

        try {
            flowTemplateUnitService.delete(flowTemplateUnitListList);
            result.put("result", "success");
        } catch (MessageBusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
        }

        return result;
    }

    @RequestMapping(value = "/flow_template/flow_template_unit/{flowTemplateUnitId}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> moveFlowTemplateUnit(@PathVariable String flowTemplateUnitId, @RequestParam("action") String action) {

        Map<String, Object> result = new HashMap<>();
        try {
            if("up".equals(action)) {
                flowTemplateService.upFlowTemplateUnit(flowTemplateUnitId);
            } else if("down".equals(action)) {
                flowTemplateService.downFlowTemplateUnit(flowTemplateUnitId);
            } else {
                log.error("action is not up or down");
                result.put("result", "error");
            }
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
        }

        return result;
    }


}
