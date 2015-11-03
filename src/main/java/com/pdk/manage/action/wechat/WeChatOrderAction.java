package com.pdk.manage.action.wechat;

import com.pdk.manage.dto.coupon.CouponJson;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.order.OrderRequestModel;
import com.pdk.manage.model.order.ShowOrderAggVO;
import com.pdk.manage.model.order.ShowOrderPageModel;
import com.pdk.manage.model.sm.Employee;
import com.pdk.manage.model.sm.User;
import com.pdk.manage.service.coupon.CouponUserRelationService;
import com.pdk.manage.service.flow.FlowTemplateInstanceService;
import com.pdk.manage.service.order.OrderService;
import com.pdk.manage.service.order.OrderTipService;
import com.pdk.manage.service.sm.EmployeeService;
import com.pdk.manage.service.sm.SmEmployeeReviewService;
import com.pdk.manage.service.sm.SmUserService;
import com.pdk.manage.util.CommonConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by hubo on 2015/10/8
 */
@Controller
@RequestMapping("/wechat/order")
public class WeChatOrderAction {

    private static Logger log = LoggerFactory.getLogger(WeChatOrderAction.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderTipService orderTipService;

    @Autowired
    private SmEmployeeReviewService employeeReviewService;

    @Autowired
    private CouponUserRelationService couponUserRelationService;

    @Autowired
    private FlowTemplateInstanceService flowTemplateInstanceService;

    @Autowired
    private SmUserService userService;

    @Autowired
    private WeChatOrderService weChatOrderService;
    /**
     *查询用户订单信息
     * @param sourceId 用户的源ID(微信openID)
     * @return 订单json
     */
    @RequestMapping("/user/{sourceId}")
    @ResponseBody
    public Map<String, Object> queryUserOrder(@PathVariable("sourceId") String sourceId, int pageNum, int pageSize) {

        Map<String, Object> result = new HashMap<>();

        if(sourceId == null) {
            return null;
        }

        OrderRequestModel orderRequestModel = new OrderRequestModel();
        orderRequestModel.setSourceid(sourceId);
        orderRequestModel.setPageNum(pageNum);
        orderRequestModel.setPageSize(pageSize);

        try {
            ShowOrderPageModel orderPageModel = orderService.queryAggVOByArgs(orderRequestModel);

            List<ShowOrderAggVO> showOrderAggVOList = orderPageModel.getOrderAggModel();

            if(showOrderAggVOList != null && showOrderAggVOList.size() > 0) {
                Set<String> idList = new HashSet<>();

                for (ShowOrderAggVO orderAggVO : showOrderAggVOList) {
                    idList.add(orderAggVO.getHead().getFlowInstanceId());
                }

                Map<String, Boolean> isPriceMap = flowTemplateInstanceService.queryOrderStatusToUser(new ArrayList<>(idList));

                for (ShowOrderAggVO orderAggVO : showOrderAggVOList) {
                    orderAggVO.getHead().setIsPriced(isPriceMap.get(orderAggVO.getHead().getFlowInstanceId()));
                }

            }


            result.put("result", "success");
            result.put("total", orderPageModel.getTotal());
            result.put("data", showOrderAggVOList);

        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", "订单查询错误！");
        }

        return result;
    }

    @RequestMapping("/{orderId}")
    @ResponseBody
    public Map<String, Object> queryOrderDetail(@PathVariable("orderId") String orderId) throws BusinessException {

        Map<String, Object> result = new HashMap<>();

        OrderRequestModel requestModel = new OrderRequestModel();
        requestModel.setOrderId(orderId);

        ShowOrderPageModel pageModel = orderService.queryAggVOByArgs(requestModel);

        if(pageModel.getOrderAggModel() != null && pageModel.getOrderAggModel().size() > 0) {

            ShowOrderAggVO aggVO = pageModel.getOrderAggModel().get(0);

            Employee employee = employeeService.get(aggVO.getHead().getYwyId());

            int tipCount = orderTipService.getOrderTipCountByEmployee(aggVO.getHead().getYwyId());

            int serviceCount = orderService.getFinishedNum(aggVO.getHead().getYwyId());

            double score = employeeReviewService.getEmployeeReview(aggVO.getHead().getYwyId());

            CouponJson couponJson = couponUserRelationService.getOrderUseCoupon(aggVO.getHead().getUserId(), aggVO.getHead().getFlowtypeId(), aggVO.getHead().getMny());

            User u = userService.get(aggVO.getHead().getUserId());

            boolean isPriced = flowTemplateInstanceService.queryOrderStatusToUser(aggVO.getHead().getFlowInstanceId());
            aggVO.getHead().setIsPriced(isPriced);

            result.put("order", aggVO);
            result.put("score", score);
            result.put("tipCount", tipCount);
            result.put("serviceCount", serviceCount);
            result.put("employee", employee);
            result.put("headerImagePath", CommonConst.HEADER_IMAGE_PATH);
            result.put("user", u);

            if(couponJson != null) {
                result.put("coupon", couponJson);
            }

        }

        return result;
    }

    @RequestMapping("/review/{orderId}")
    @ResponseBody
    public Map<String, Object> reviewOrder(@PathVariable("orderId") String orderId) throws BusinessException {

        Map<String, Object> result = new HashMap<>();

        result.put("orderId", orderId);

        OrderRequestModel requestModel = new OrderRequestModel();
        requestModel.setOrderId(orderId);

        ShowOrderPageModel pageModel = orderService.queryAggVOByArgs(requestModel);

        if(pageModel.getOrderAggModel() != null && pageModel.getOrderAggModel().size() > 0) {

            ShowOrderAggVO aggVO = pageModel.getOrderAggModel().get(0);

            result.put("orderCode", aggVO.getHead().getCode());
            result.put("payStatus", aggVO.getHead().getPayStatus());
            result.put("userId", aggVO.getHead().getUserId());
            result.put("mny", aggVO.getHead().getMny());

            CouponJson couponJson = couponUserRelationService.getOrderUseCoupon(aggVO.getHead().getUserId(), aggVO.getHead().getFlowtypeId(), aggVO.getHead().getMny());

            if(couponJson != null) {
                result.put("couponId", couponJson.getId());
                result.put("couponMny", couponJson.getMny());
            }

        }

        return result;
    }


    @RequestMapping("/pay/{orderId}/{couponId}/{actMny}/{couponMny}/{tip}")
    @ResponseBody
    public Map<String, Object> payOrderSuccess(@PathVariable String orderId,
                                               @PathVariable BigDecimal actMny,
                                               @PathVariable String couponId,
                                               @PathVariable BigDecimal couponMny,
                                               @PathVariable BigDecimal tip) {

        Map<String, Object> result = new HashMap<>();

        try {
            log.info("orderId:{}", orderId);
            log.info("actMny:{}", actMny);
            log.info("couponId:{}", couponId);
            log.info("couponMny:{}", couponMny);
            log.info("tip:{}", tip);

            if(couponId == null || couponId.length() != 24) {
                couponId = null;
            }

            weChatOrderService.payOrder(orderId, actMny, couponId, couponMny, tip);

            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
        }

        return result;
    }

}
