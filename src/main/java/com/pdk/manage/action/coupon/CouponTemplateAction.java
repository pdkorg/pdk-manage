package com.pdk.manage.action.coupon;

import com.pdk.manage.model.coupon.Coupon;
import com.pdk.manage.model.coupon.CouponActivityTemplateB;
import com.pdk.manage.service.coupon.CouponActivityTemplateBService;
import com.pdk.manage.service.coupon.CouponService;
import com.pdk.manage.service.flow.FlowTypeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuhaiming on 2015/9/11.
 */
@Controller
@RequestMapping("coupon")
public class CouponTemplateAction {
    private static final Logger log = LoggerFactory.getLogger(CouponTemplateAction.class);
    private static final String MARK_ADD = "ADD";

    @Autowired
    private CouponActivityTemplateBService couponActivityTemplateBServic;

    @Autowired
    private FlowTypeService flowTypeService;

    @ModelAttribute
    public void getCoupon(@RequestParam(value = "id", required = false) String id, Map<String, Object> map) {
        if( StringUtils.isNotEmpty(id) ) {
            map.put( "couponActivityTemplateB", couponActivityTemplateBServic.get(id) );
        }
    }

    @RequestMapping("/coupon_template/{templateId}")
    @ResponseBody
    public Map<String, Object> couponsByActivityId(@PathVariable String templateId, Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();

        List<CouponActivityTemplateB> templates = null;
        if (MARK_ADD.equals(templateId)) {
            templates = new ArrayList<>();
        } else {
            templates = couponActivityTemplateBServic.selectListByTemplateId(templateId);
        }

        result.put("couponTemplates", templates);
        result.put("result", "success");
        return result;

    }
}
