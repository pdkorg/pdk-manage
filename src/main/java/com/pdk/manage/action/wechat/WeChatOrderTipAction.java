package com.pdk.manage.action.wechat;

import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.order.OrderRequestModel;
import com.pdk.manage.model.order.ShowOrderPageModel;
import com.pdk.manage.service.order.OrderService;
import com.pdk.manage.service.order.OrderTipService;
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
 * Created by hubo on 2015/10/8
 */
@Controller
@RequestMapping("/wechat/order_tip")
public class WeChatOrderTipAction {

    private static Logger log = LoggerFactory.getLogger(WeChatOrderTipAction.class);

    @Autowired
    private OrderTipService orderTipService;

    /**
     * 查询小跑君小费次数
     * @param sourceId 用户的源ID(微信openID)
     * @return 订单json
     */
    @RequestMapping("/tip_count/{sourceId}")
    @ResponseBody
    public Map<String, Object> queryTipCountByEmployeeId(@PathVariable("sourceId") String sourceId) {
        Map<String, Object> result = new HashMap<>();

        int tipCount = orderTipService.getOrderTipCountByEmployee(sourceId);
        result.put("result", "success");
        result.put("tipCount", tipCount);

        return result;
    }

}
