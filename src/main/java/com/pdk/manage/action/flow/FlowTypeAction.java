package com.pdk.manage.action.flow;

import com.github.pagehelper.PageInfo;
import com.pdk.manage.dto.flow.FlowTypeJson;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.exception.MessageBusinessException;
import com.pdk.manage.model.flow.FlowType;
import com.pdk.manage.model.sm.Employee;
import com.pdk.manage.service.flow.FlowTypeService;
import com.pdk.manage.common.wapper.flow.FlowTypeDataTableQueryArgWapper;
import com.pdk.manage.service.sm.EmployeeService;
import com.pdk.manage.util.CommonUtil;
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
 * Created by hubo on 2015/8/12
 */
@Controller
@RequestMapping("/flow")
public class FlowTypeAction {

    private static final Logger log = LoggerFactory.getLogger(FlowTypeAction.class);

    @Autowired
    private FlowTypeService flowTypeService;
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/flow_types")
    public String index() {
        return "flow/flow_type";
    }

    @ModelAttribute
    public void getFlowType(@RequestParam(value = "id", required = false) String id, Map<String, Object> map) {
        if(StringUtils.isNotEmpty(id)) {
            map.put("flowType", flowTypeService.get(id));
        }
    }

    @RequestMapping(value = "/flow_type/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> get(@PathVariable("id") String id) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", "success");
        result.put("data", flowTypeService.get(id));
        return result;
    }

    @RequestMapping(value = "/flow_type", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@Valid FlowType flowType, Errors errors, Map<String, Object> map) {

        Map<String, Object> result = new HashMap<>();

        if(errors.getErrorCount() > 0) {
            result.put("result", "error");
            for (FieldError err : errors.getFieldErrors()) {
                map.put("err_" + err.getField(), err.getDefaultMessage());
            }
            return result;
        }

        try {
            flowTypeService.save(flowType);
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

    @RequestMapping(value = "/flow_type", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> update(@Valid FlowType flowType, Errors errors, Map<String, Object> map) {

        Map<String, Object> result = new HashMap<>();

        if(errors.getErrorCount() > 0) {
            result.put("result", "error");
            for (FieldError err : errors.getFieldErrors()) {
                map.put("err_" + err.getField(), err.getDefaultMessage());
            }
            return result;
        }

        try {
            flowTypeService.update(flowType);
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

    @RequestMapping(value = "/flow_type", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> batchDelete(@RequestParam("ids") String[] ids, @RequestParam(value = "ts", required = false) Long[] ts) {

        Map<String, Object> result = new HashMap<>();

        List<FlowType> flowTypeList = new ArrayList<>();

        for (int i = 0; i < ids.length; i++) {
            FlowType f = new FlowType();
            f.setId(ids[i]);
            if(ts != null && ts[i] != null) {
                f.setTs(new Date(ts[i]));
            }
            flowTypeList.add(f);
        }

        try {
            flowTypeService.delete(flowTypeList);
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

    @RequestMapping(value = "/flow_type/status/{status}", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> updateStatus(@PathVariable Short status, @RequestParam("ids") String[] ids, @RequestParam(value = "ts", required = false) Long[] ts) {

        Map<String, Object> result = new HashMap<>();

        List<FlowType> flowTypeList = new ArrayList<>();

        for (int i = 0; i < ids.length; i++) {
            FlowType f = new FlowType();
            f.setId(ids[i]);
            if(ts != null && ts[i] != null) {
                f.setTs(new Date(ts[i]));
            }
            flowTypeList.add(f);
        }

        try {
            if(status == DBConst.STATUS_ENABLE) {
                flowTypeService.enable(flowTypeList);
            }else {
                flowTypeService.disable(flowTypeList);
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


    @RequestMapping("/flow_types/table_data")
    public @ResponseBody Map<String, Object> list( FlowTypeDataTableQueryArgWapper arg) {

        Map<String, Object> result = new HashMap<>();

        PageInfo<FlowType> pageInfo = null;
        try {
            pageInfo = flowTypeService.selectLikePage(arg.getSearchText(), arg.getPageNum(), arg.getLength(), arg.getOrderStr());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        List<FlowTypeJson> data = new ArrayList<>();
        List<String> idList = new ArrayList<>();

        int index = 1;

        for (FlowType flowType : pageInfo.getList()) {
            data.add(new FlowTypeJson(index, flowType));
            if(CommonUtil.pdkBoolean(flowType.getIsAutoAssignDeliver()) && StringUtils.isNotEmpty(flowType.getDeliverId())) {
                idList.add(flowType.getDeliverId());
            }
            index++;
        }

        Map<String, Employee> employeeMap = CommonUtil.toMap(employeeService.get(idList));

        for (FlowTypeJson json : data) {
            if(StringUtils.isNotEmpty(json.getDeliverId())) {
                json.setDeliverName(employeeMap.get(json.getDeliverId()).getName());
            }
        }

        result.put("draw", arg.getDraw());
        result.put("recordsTotal", pageInfo.getTotal());
        result.put("recordsFiltered", pageInfo.getTotal());
        result.put("data", data);

        return result;

    }

    @RequestMapping("/flow_types/all")
    public @ResponseBody Map<String, Object> listAll() {

        Map<String, Object> result = new HashMap<>();

        try {
            result.put("data", flowTypeService.selectAll(DBConst.STATUS_ENABLE));
            result.put("result", "success");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
        }

        return result;

    }

}
