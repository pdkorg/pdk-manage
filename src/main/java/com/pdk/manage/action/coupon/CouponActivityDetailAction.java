package com.pdk.manage.action.coupon;

import com.github.pagehelper.PageInfo;
import com.pdk.manage.common.wapper.coupon.CouponSendUserDetailTableQueryArgWapper;
import com.pdk.manage.dto.coupon.CouponActivityJson;
import com.pdk.manage.dto.coupon.CouponSendUserJson;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.coupon.*;
import com.pdk.manage.service.coupon.CouponActivityRuleService;
import com.pdk.manage.service.coupon.CouponActivityService;
import com.pdk.manage.service.coupon.CouponService;
import com.pdk.manage.service.coupon.CouponUserSendedService;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuhaiming on 2015/9/11.
 */
@Controller
@RequestMapping("coupon")
public class CouponActivityDetailAction {
    private static final Logger log = LoggerFactory.getLogger(CouponActivityDetailAction.class);
    private static final String ACTION_TYPE_ADD = "ADD";

    @Autowired
    private CouponActivityService couponActivityService;

    @Autowired
    private CouponUserSendedService couponUserSendedService;

    @Autowired
    private CouponActivityRuleService couponActivityRuleService;

    @Autowired
    private CouponService couponService;

    @ModelAttribute
    public void getCouponActivity(@RequestParam(value = "id", required = false) String id, Map<String, Object> map) {
        if( StringUtils.isNotEmpty(id) ) {
            map.put("couponActivity", couponActivityService.get(id));
        }
    }

    @RequestMapping(value = "/coupon_activity_detail", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@Valid CouponActivity activity, Errors errors, Map<String, Object> map) {

        Map<String, Object> result = new HashMap<>();

        if (errors.getErrorCount() > 0) {
            result.put("result", "error");
            for (FieldError err : errors.getFieldErrors()) {
                map.put("err_" + err.getField(), err.getDefaultMessage());
            }
            return result;
        }

        try {
            if ( StringUtils.isEmpty(activity.getId()) ) {
                couponActivityService.saveAll(activity);
            } else {
                couponActivityService.updateAll(activity);
            }

            result.put("activityId", activity.getId());
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
        }

        return result;
    }

    @RequestMapping("/coupon_activity_detail/{activityId}")
    public String activityDetail(@PathVariable String activityId, Map<String, Object> map, boolean canEdit) {
        CouponActivity activity;
        if ( ACTION_TYPE_ADD.equals(activityId) ) {
            activity = new CouponActivity();
            activity.setStatus(DBConst.STATUS_ENABLE);

        } else {
            activity = couponActivityService.get(activityId);

        }

        map.put("activity", new CouponActivityJson(0, activity));
        map.put("canEdit", canEdit);

        return canEdit ? "coupon/coupon_activity_detail_edit" : "coupon/coupon_activity_detail";

    }

    @RequestMapping(value = "/coupon_activity_detail/search_user_send_info")
    @ResponseBody
    public Map<String, Object> searchUserSendInfo(CouponActivity activity) {
        Map<String, Object> result = new HashMap<>();
        searchSendUserInfo(result, activity.getRules(), activity.getCoupons());

        return result;
    }

    @RequestMapping(value = "/coupon_activity_detail/search_user_send_info/id/{id}")
    @ResponseBody
    public Map<String, Object> searchUserSendInfoById(@PathVariable String id) {
        Map<String, Object> result = new HashMap<>();
        CouponActivity couponActivity = couponActivityService.get(id);
        if ( couponActivity.getStatus() == DBConst.COUPON_ACTIVITY_STATUS_SENDED ) {
            result.put("sendUserCnt", couponActivity.getSendUserCnt());
            result.put("sendTotalMny", couponActivity.getSendTotalMny());
            result.put("result", "success");
        } else {
            List<CouponActivityRule> rules = couponActivityRuleService.getByActivityId(id);
            List<Coupon> coupons = couponService.getByActivityId(id);
            searchSendUserInfo(result, rules, coupons);
        }

        return result;
    }

    @RequestMapping(value = "/coupon_activity_detail/search_user_send_detail_table/{id}")
    @ResponseBody
    public Map<String, Object> searchSendUserDetail(CouponSendUserDetailTableQueryArgWapper arg, @PathVariable String id) {
        Map<String, Object> result = new HashMap<>();
        List<CouponActivityRule> rules = new ArrayList<>();

        CouponActivity activity = null;
        if ( !ACTION_TYPE_ADD.equals(id) ) {
            activity = couponActivityService.get(id);
            rules = couponActivityRuleService.getByActivityId(id);
        }

        try {
            List<CouponSendUserJson> json = new ArrayList<>();
            int index = 1;
            long total = 0;

            if ( activity != null && activity.getStatus() == DBConst.COUPON_ACTIVITY_STATUS_SENDED ) {
                PageInfo<CouponUserSended> pageInfo = couponUserSendedService.selectPageByActivityId(activity.getId(), arg.getSearchText(), arg.getPageNum(), arg.getLength(), arg.getOrderStr());
                for ( CouponUserSended user : pageInfo.getList() ) {
                    json.add(new CouponSendUserJson(index, user));
                    index++;
                }

                total = pageInfo.getTotal();
            } else {
                CouponActivityRuleModel model = couponActivityService.transRule2Condition(rules);
                PageInfo<CouponUserSended> pageInfo = couponUserSendedService.getUserPageInfoForCoupon(arg, model);
                for ( CouponUserSended user : pageInfo.getList() ) {
                    json.add(new CouponSendUserJson(index, user));
                    index++;
                }

                total = pageInfo.getTotal();
            }


            result.put("draw", arg.getDraw());
            result.put("recordsTotal", total);
            result.put("recordsFiltered", total);
            result.put("data", json);
        } catch (BusinessException e) {
            e.printStackTrace();
        }

        return result;
    }

    private void searchSendUserInfo(Map<String, Object> result, List<CouponActivityRule> rules, List<Coupon> coupons) {
        try {

            BigDecimal sendTotalMny;
            BigDecimal couponMny = BigDecimal.ZERO;

            if ( coupons != null ) {
                for (Coupon coupon : coupons) {
                    couponMny = couponMny.add( coupon.getSendMny() );
                }
            }

            int userCnt  = couponActivityService.getSendUserCount(rules);
            sendTotalMny = couponMny.multiply( BigDecimal.valueOf(userCnt) ).setScale(2, BigDecimal.ROUND_HALF_UP);

            result.put("sendUserCnt", userCnt);
            result.put("sendTotalMny", sendTotalMny.toString());
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
        }
    }

    @RequestMapping(value = "/coupon_activity_detail/send_coupon/{id}")
    @ResponseBody
    public Map<String, Object> sendCoupon(@PathVariable String id) {
        Map<String, Object> result = new HashMap<>();

        try {
            boolean isSendSuccess = false;
            isSendSuccess = couponActivityService.sendCoupon(id);

            if ( isSendSuccess ) {
                result.put("result", "success");
            } else {
                result.put("result", "error");
                result.put("message", "优惠券发送失败！");
            }

        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("message", "优惠券发送失败！");
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("message", "优惠券发送失败！");
        }

        return result;
    }

    @RequestMapping(value = "/coupon_activity_detail/status/{status}", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> updateStatus(@PathVariable Short status, CouponActivity activity) {
        Map<String, Object> result = new HashMap<>();

        try {
            if(status == DBConst.STATUS_ENABLE) {
                couponActivityService.enable(activity);
            }else {
                couponActivityService.disable(activity);
            }
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        }

        return result;
    }
}
