package com.pdk.manage.action.coupon;

import com.github.pagehelper.PageInfo;
import com.pdk.manage.common.wapper.coupon.CouponActivityDataTableQueryArgWapper;
import com.pdk.manage.common.wapper.coupon.CouponActivityRefDataTableQueryArgWapper;
import com.pdk.manage.dto.coupon.CouponActivityJson;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.coupon.CouponActivity;
import com.pdk.manage.service.coupon.CouponActivityService;
import com.pdk.manage.util.DBConst;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by liuhaiming on 2015/9/11.
 */
@Controller
@RequestMapping("coupon")
public class CouponActivityAction {
    private static final Logger log = LoggerFactory.getLogger(CouponActivityAction.class);

    @Autowired
    private CouponActivityService couponActivityService;

    @RequestMapping("/coupon_activity")
    public String index() {
        return "coupon/coupon_activity";
    }

    @ModelAttribute
    public void getCouponActivity(@RequestParam(value = "id", required = false) String id, Map<String, Object> map) {
        if( StringUtils.isNotEmpty(id) ) {
            map.put("couponActivity", couponActivityService.get(id));
        }
    }

    @RequestMapping("/coupon_activity/table_data")
    public @ResponseBody
    Map<String, Object> list( CouponActivityDataTableQueryArgWapper arg) {

        Map<String, Object> result = new HashMap<>();

        PageInfo<CouponActivity> pageInfo = null;
        try {
            pageInfo = couponActivityService.selectLikePage(arg.getSearchText(), arg.getPageNum(), arg.getLength(), arg.getOrderStr());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        List<CouponActivityJson> data = new ArrayList<>();

        int index = 1;

        for (CouponActivity activity : pageInfo.getList()) {
            data.add(new CouponActivityJson(index, activity));
            index++;
        }

        result.put("draw", arg.getDraw());
        result.put("recordsTotal", pageInfo.getTotal());
        result.put("recordsFiltered", pageInfo.getTotal());
        result.put("data", data);

        return result;
    }

    @RequestMapping("/coupon_activity/ref_table_data")
    public @ResponseBody
    Map<String, Object> refList( CouponActivityRefDataTableQueryArgWapper arg) {

        Map<String, Object> result = new HashMap<>();

        PageInfo<CouponActivity> pageInfo = null;
        try {
            pageInfo = couponActivityService.selectLikePageEnable(arg.getSearchText(), arg.getPageNum(), arg.getLength(), arg.getOrderStr());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        List<CouponActivityJson> data = new ArrayList<>();

        int index = 1;

        for (CouponActivity activity : pageInfo.getList()) {
            data.add(new CouponActivityJson(index, activity));
            index++;
        }

        result.put("draw", arg.getDraw());
        result.put("recordsTotal", pageInfo.getTotal());
        result.put("recordsFiltered", pageInfo.getTotal());
        result.put("data", data);

        return result;
    }

    @RequestMapping(value = "/coupon_activity", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> batchDelete(@RequestParam("ids") String[] ids, @RequestParam(value = "ts", required = false) Long[] ts) {

        Map<String, Object> result = new HashMap<>();

        List<CouponActivity> activitys = new ArrayList<>();
        List<String> activityIds = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            CouponActivity activity = new CouponActivity();
            activity.setId(ids[i]);
            if(ts != null && ts[i] != null) {
                activity.setTs(new Date(ts[i]));
            }

            activityIds.add(activity.getId());
            activitys.add(activity);
        }

        try {
            couponActivityService.deleteByBill(activitys);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        }

        return result;
    }
}
