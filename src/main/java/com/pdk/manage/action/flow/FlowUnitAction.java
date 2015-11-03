package com.pdk.manage.action.flow;

import com.github.pagehelper.PageInfo;
import com.pdk.manage.common.wapper.flow.FlowUnitDataTableQueryArgWapper;
import com.pdk.manage.dto.flow.FlowUnitJson;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.exception.MessageBusinessException;
import com.pdk.manage.model.flow.FlowUnit;
import com.pdk.manage.service.flow.FlowUnitService;
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
 * Created by hubo on 2015/8/13
 */
@Controller
@RequestMapping("/flow")
public class FlowUnitAction {

    private static final Logger log = LoggerFactory.getLogger(FlowUnitAction.class);

    @Autowired
    private FlowUnitService flowUnitService;


    @RequestMapping("/flow_units")
    public String index() {
        return "flow/flow_unit";
    }

    @ModelAttribute
    public void getFlowUnit(@RequestParam(value = "id", required = false) String id, Map<String, Object> map) {
        if(StringUtils.isNotEmpty(id)) {
            map.put("flowUnit", flowUnitService.get(id));
        }
    }

    @RequestMapping(value = "/flow_unit/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> get(@PathVariable("id") String id) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", "success");
        if("all".equals(id)) {
            try {
                result.put("data", flowUnitService.selectAll());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                result.put("result", "error");
            }
        }else {
            result.put("data", flowUnitService.get(id));
        }

        return result;
    }

    @RequestMapping(value = "/flow_unit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@Valid FlowUnit flowUnit, Errors errors, Map<String, Object> map) {

        Map<String, Object> result = new HashMap<>();

        if(errors.getErrorCount() > 0) {
            result.put("result", "error");
            for (FieldError err : errors.getFieldErrors()) {
                map.put("err_" + err.getField(), err.getDefaultMessage());
            }
            return result;
        }

        try {
            flowUnitService.save(flowUnit);
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

    @RequestMapping(value = "/flow_unit", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> update(@Valid FlowUnit flowUnit, Errors errors, Map<String, Object> map) {

        Map<String, Object> result = new HashMap<>();

        if(errors.getErrorCount() > 0) {
            result.put("result", "error");
            for (FieldError err : errors.getFieldErrors()) {
                map.put("err_" + err.getField(), err.getDefaultMessage());
            }
            return result;
        }

        try {
            flowUnitService.update(flowUnit);
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

    @RequestMapping(value = "/flow_unit", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> batchDelete(@RequestParam("ids") String[] ids, @RequestParam(value = "ts", required = false) Long[] ts) {

        Map<String, Object> result = new HashMap<>();

        List<FlowUnit> flowUnitList = new ArrayList<>();

        for (int i = 0; i < ids.length; i++) {
            FlowUnit f = new FlowUnit();
            f.setId(ids[i]);
            if(ts != null && ts[i] != null) {
                f.setTs(new Date(ts[i]));
            }
            flowUnitList.add(f);
        }

        try {
            flowUnitService.delete(flowUnitList);
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

    @RequestMapping(value = "/flow_unit/status/{status}", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> updateStatus(@PathVariable Short status, @RequestParam("ids") String[] ids, @RequestParam(value = "ts", required = false) Long[] ts) {

        Map<String, Object> result = new HashMap<>();

        List<FlowUnit> flowUnitList = new ArrayList<>();

        for (int i = 0; i < ids.length; i++) {
            FlowUnit f = new FlowUnit();
            f.setId(ids[i]);
            if(ts != null && ts[i] != null) {
                f.setTs(new Date(ts[i]));
            }
            flowUnitList.add(f);
        }

        try {
            if(status == DBConst.STATUS_ENABLE) {
                flowUnitService.enable(flowUnitList);
            }else {
                flowUnitService.disable(flowUnitList);
            }
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


    @RequestMapping("/flow_units/table_data")
    public @ResponseBody Map<String, Object> list( FlowUnitDataTableQueryArgWapper arg) {

        Map<String, Object> result = new HashMap<>();

        PageInfo<FlowUnit> pageInfo = null;
        try {
            pageInfo = flowUnitService.selectLikePage(arg.getSearchText(), arg.getPageNum(), arg.getLength(), arg.getOrderStr());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        List<FlowUnitJson> data = new ArrayList<>();

        int index = 1;

        for (FlowUnit flowUnit : pageInfo.getList()) {
            data.add(new FlowUnitJson(index, flowUnit.getId(), flowUnit.getCode(), flowUnit.getName(), flowUnit.getOrderStatus(), flowUnit.getFlowActionCode(), flowUnit.getStatus(), flowUnit.getMemo(), flowUnit.getTs()));
            index++;
        }

        result.put("draw", arg.getDraw());
        result.put("recordsTotal", pageInfo.getTotal());
        result.put("recordsFiltered", pageInfo.getTotal());
        result.put("data", data);

        return result;

    }


}
