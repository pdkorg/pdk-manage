package com.pdk.manage.action.coupon;

import com.pdk.manage.dto.coupon.CouponActivityTemplateJson;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.coupon.CouponActivityTemplate;
import com.pdk.manage.model.coupon.CouponActivityTemplateB;
import com.pdk.manage.service.coupon.CouponActivityTemplateService;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuhaiming on 2015/9/11.
 */
@Controller
@RequestMapping("coupon")
public class CouponActivityTemplateDetailAction {
    private static final Logger log = LoggerFactory.getLogger(CouponActivityTemplateDetailAction.class);
    private static final String ACTION_TYPE_ADD = "ADD";

    @Autowired
    private CouponActivityTemplateService couponActivityTemplateService;

    @ModelAttribute
    public void getCouponActivity(@RequestParam(value = "id", required = false) String id, Map<String, Object> map) {
        if( StringUtils.isNotEmpty(id) ) {
            map.put("couponActivityTemplate", couponActivityTemplateService.get(id));
        }
    }

    @RequestMapping(value = "/coupon_activity_template_detail", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@Valid CouponActivityTemplate activityTemplate, Errors errors, Map<String, Object> map) {

        Map<String, Object> result = new HashMap<>();

        if (errors.getErrorCount() > 0) {
            result.put("result", "error");
            for (FieldError err : errors.getFieldErrors()) {
                map.put("err_" + err.getField(), err.getDefaultMessage());
            }
            return result;
        }

        try {
            if ( StringUtils.isEmpty(activityTemplate.getId()) ) {
                couponActivityTemplateService.saveAll(activityTemplate);
            } else {
                couponActivityTemplateService.updateAll(activityTemplate);
            }

            result.put("activityTemplateId", activityTemplate.getId());
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
        }

        return result;
    }

    @RequestMapping("/coupon_activity_template_detail/{id}")
    public String activityDetail(@PathVariable String id, Map<String, Object> map, boolean canEdit) {
        CouponActivityTemplate activityTemplate;
        if ( ACTION_TYPE_ADD.equals(id) ) {
            activityTemplate = new CouponActivityTemplate();
            activityTemplate.setStatus(DBConst.STATUS_ENABLE);
        } else {
            activityTemplate = couponActivityTemplateService.get(id);

        }

        map.put("activityTemplate", new CouponActivityTemplateJson(0, activityTemplate));
        map.put("canEdit", canEdit);

        return canEdit ? "coupon/coupon_activity_template_detail_edit" : "coupon/coupon_activity_template_detail";

    }
}
