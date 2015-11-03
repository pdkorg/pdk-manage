package com.pdk.manage.action;

import com.pdk.manage.dto.report.ReportIndexJson;
import com.pdk.manage.dto.sm.FuncDto;
import com.pdk.manage.model.order.OrderFlowTypeModel;
import com.pdk.manage.service.report.ReportIndexService;
import com.pdk.manage.util.CommonConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hubo on 2015/8/8
 */

@Controller
public class IndexAction {
    @Autowired
    private HttpSession session;

    @Autowired
    private ReportIndexService reportIndexService;

    @RequestMapping("/")
    public String index() {
        String rtnUri;
        if (hasPermession()) {
            rtnUri = "index";
        } else {
            rtnUri = "blank";
        }
        return rtnUri;
    }

    private boolean hasPermession() {
        List<FuncDto> permissionFuncs = (List<FuncDto>) session.getAttribute(CommonConst.SESSION_ATTR_KEY_PERMISSION_FUNCS);
        for (FuncDto func : permissionFuncs) {
            if ("INDEX".equals(func.getCode())) {
                return true;
            }
        }

        return false;
    }

    @RequestMapping(value = "/index/chart_data")
    @ResponseBody
    public Map<String,Object> chartData(){
        Map<String, Object> result = new HashMap<>();
        ReportIndexJson json = reportIndexService.getAllOrder();

        result.put("data", json);
        result.put("result", "success");
        return result;
    }

    @RequestMapping(value = "/index/chart_order_data")
    @ResponseBody
    public Map<String,Object> chartOrderData(){
        Map<String, Object> result = new HashMap<>();
        List<ReportIndexJson> json = reportIndexService.getOrderByMonth();

        result.put("data", json);
        result.put("result", "success");
        return result;
    }

    @RequestMapping(value = "/index/chart_flow_type_pie_data/{month}")
    @ResponseBody
    public Map<String,Object> chartFlowTypePieData(@PathVariable int month){
        Map<String, Object> result = new HashMap<>();
        List<OrderFlowTypeModel> json = reportIndexService.getFlowTypeByMonth(month);

        result.put("data", json);
        result.put("result", "success");
        return result;
    }

}
