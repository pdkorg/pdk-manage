package com.pdk.manage.action.order;

import com.alibaba.fastjson.JSON;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.order.OrderRequestModel;
import com.pdk.manage.model.order.ShowOrderPageModel;
import com.pdk.manage.service.order.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 程祥 on 15/9/24.
 * Function： 订单开放服务~  给微信端提供的查询接口~
 */
@Controller
@RequestMapping(value = "/open/order",produces = "text/html; charset=utf-8")
public class OrderOpenRequest {

    private static final Logger logger = LoggerFactory.getLogger("OrderLog");

    @Autowired
    private OrderService orderService;

    /**
     * 通过sourceid或者订单id等查询条件查询订单详情
     * @param requestModel
     * @return
     */
    @RequestMapping(value = "/forweixin")
    @ResponseBody
    public String queryOrderByArg(OrderRequestModel requestModel){
        ShowOrderPageModel result = new ShowOrderPageModel();
        try {
            result = orderService.queryAggVOByArgs(requestModel);
        } catch (BusinessException e) {
            logger.error(e.getMessage(), e);
            result.setRst_code("fail");
            result.setRst_mess("查询失败"+new Date());
            return JSON.toJSONString(result);
        }
        result.setRst_code("success");
        result.setRst_mess("查询成功!");
        return JSON.toJSONString(result);
    }


    @RequestMapping(value = "/payaction",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> payAction(@RequestParam(value = "orderId") String orderId,@RequestParam(value = "payType") Short payType,BigDecimal couponMny,BigDecimal payMny,BigDecimal tipMny){
        Map<String,Object> result = new HashMap<>();

        try {
            int res = orderService.payOrder(orderId,payType,couponMny,payMny,tipMny);
            if(-1==res){
                result.put("result","error");
                result.put("message","未查询出该订单！");
                return result;
            }
        }catch (Exception e){

            result.put("result", "error");
            result.put("message", "更新订单失败！");
            return result;
        }

        result.put("result", "success");
        result.put("message", "更新支付信息成功！");
        return result;
    }
}
