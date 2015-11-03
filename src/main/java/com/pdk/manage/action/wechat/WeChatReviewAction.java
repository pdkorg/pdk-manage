package com.pdk.manage.action.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pdk.manage.dto.coupon.CouponJson;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.coupon.Coupon;
import com.pdk.manage.model.coupon.CouponUserRelation;
import com.pdk.manage.model.order.OrderRequestModel;
import com.pdk.manage.model.order.ShowOrderAggVO;
import com.pdk.manage.model.order.ShowOrderPageModel;
import com.pdk.manage.model.sm.Employee;
import com.pdk.manage.model.sm.EmployeeReview;
import com.pdk.manage.model.sm.User;
import com.pdk.manage.service.coupon.CouponActivityService;
import com.pdk.manage.service.order.OrderService;
import com.pdk.manage.service.order.OrderTipService;
import com.pdk.manage.service.sm.EmployeeService;
import com.pdk.manage.service.sm.SmEmployeeReviewService;
import com.pdk.manage.service.sm.SmUserService;
import com.pdk.manage.util.CommonConst;
import com.pdk.manage.util.DBConst;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 优惠券相关对外接口
 * Created by liuhaiming on 2015/8/30.
 */
@Controller
@RequestMapping("/wechat/review")
public class WeChatReviewAction {
    private static final Logger log = LoggerFactory.getLogger(WeChatReviewAction.class);

    @Autowired
    SmUserService userService;

    @Autowired
    private SmEmployeeReviewService employeeReviewService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderTipService orderTipService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CouponActivityService couponActivityService;

    @ModelAttribute
    public void getEmployeeReview(@RequestParam(value = "id", required = false) String id, Map<String, Object> map) {
        if (StringUtils.isNotEmpty(id)) {
            map.put("employeeReview", employeeReviewService.get(id));
        }
    }

    private Map<String, Object> save(EmployeeReview employeeReview) {

        Map<String, Object> result = new HashMap<>();
        try {
            User user = userService.findUserBySourceId(employeeReview.getSourceId());
            employeeReview.setUserId(user.getId());

            employeeReviewService.save(employeeReview);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errMessage", "人员评价异常！");
        }

        return result;
    }


    @RequestMapping(value = "/employee/{sourceId}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveReview(@PathVariable("sourceId") String sourceId, String jsonStr) {
        JSONObject jsonObject = JSON.parseObject(jsonStr);

        EmployeeReview employeeReview = new EmployeeReview();
        employeeReview.setDescription(jsonObject.getString("description"));
        employeeReview.setEmployeeId(jsonObject.getString("employeeId"));
        employeeReview.setSourceId(sourceId);
        employeeReview.setOrderId(jsonObject.getString("orderId"));
        employeeReview.setScore(jsonObject.getInteger("score"));

        Map<String, Object> result = save(employeeReview);
        try {
            User user = userService.findUserBySourceId(employeeReview.getSourceId());
            CouponJson coupon = couponActivityService.sendCouponByRule(DBConst.ACTIVITY_SEND_CODE_REVIEW, user);

            result.put("result", "success");
            result.put("coupon", coupon);

        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errMessage", "人员评价获取优惠券异常！");
        }

        return result;
    }

    @RequestMapping(value = "/employee/{sourceId}")
    @ResponseBody
    public Map<String, Object> review(@PathVariable("sourceId") String sourceId, @RequestParam("orderId")String orderId)
            throws BusinessException {

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
            // 设置一个默认最小值，小于4.1的最小4.1
            if ( score <= 4.1 ) {
                score = 4.1;
            }

            result.put("score", score);
            result.put("tipCount", tipCount);
            result.put("serviceCount", serviceCount);
            result.put("headerImagePath", CommonConst.HEADER_IMAGE_PATH);
            result.put("employee", employee);

        }

        result.put("orderId", orderId);
        result.put("openId", sourceId);

        return result;
    }

    @RequestMapping(value = "/employee")
    @ResponseBody
    public Map<String, Object> hasReview(@RequestParam("sourceId") String sourceId, @RequestParam("orderId")String orderId)
            throws BusinessException {

        Map<String, Object> result = new HashMap<>();

        if(StringUtils.isNotEmpty(sourceId) && StringUtils.isNotEmpty(orderId)) {
            result.put("result", employeeReviewService.hasEmployeeReviewByOrder(sourceId, orderId));
        } else {
            result.put("result", true);
        }


        return result;
    }
}