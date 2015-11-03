package com.pdk.manage.action.cs;

import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.order.OrderPageModel;
import com.pdk.manage.model.order.OrderRequestModel;
import com.pdk.manage.service.order.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hubo on 2015/8/19
 */
@Controller
@RequestMapping("/cs")
public class CustomerServiceAction {

    private static Logger log = LoggerFactory.getLogger(CustomerServiceAction.class);

    @Autowired
    private OrderService orderService;

    @RequestMapping("chat")
    public String index() {
        return "cs/customer_service";
    }
    @RequestMapping("chat/c")
    public String index_c() {
        return "cs/customer_service_c";
    }

    @RequestMapping("{userId}/order")
    @ResponseBody
    public Map<String, Object> queryOrderByUser(@PathVariable("userId") String userId, Integer payStatus, int pageNo, String dateRange) {

        Map<String, Object> result = new HashMap<>();

        OrderRequestModel requestModel = new OrderRequestModel();

        String[] range = dateRange.split("~");

        requestModel.setPageNum(pageNo);
        requestModel.setPageSize(10);
        if(payStatus >= 0){
            requestModel.setPayStatus(payStatus.toString());
        }
        requestModel.setFromDate(range[0]);
        requestModel.setToDate(range[1]);
        requestModel.setCustomid(userId);

        try {
            OrderPageModel orderPageModel = orderService.getOrderDataForChart(requestModel);
            result.put("totalPage", orderPageModel.getPages());
            result.put("data", orderPageModel.getOrderAggModel());
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
        }

        return result;
    }

}
