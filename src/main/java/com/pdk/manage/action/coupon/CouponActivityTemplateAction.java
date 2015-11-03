package com.pdk.manage.action.coupon;

import com.github.pagehelper.PageInfo;
import com.pdk.manage.common.wapper.coupon.CouponActivityDataTableQueryArgWapper;
import com.pdk.manage.common.wapper.coupon.CouponActivityTemplateDataTableQueryArgWapper;
import com.pdk.manage.dto.coupon.CouponActivityJson;
import com.pdk.manage.dto.coupon.CouponActivityTemplateJson;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.coupon.CouponActivityTemplate;
import com.pdk.manage.service.coupon.CouponActivityTemplateService;
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
public class CouponActivityTemplateAction {
    private static final Logger log = LoggerFactory.getLogger(CouponActivityTemplateAction.class);

    @Autowired
    private CouponActivityTemplateService couponActivityTemplateService;

    @RequestMapping("/coupon_activity_template")
    public String index() {
        return "coupon/coupon_activity_template";
    }

    @ModelAttribute
    public void getCouponActivity(@RequestParam(value = "id", required = false) String id, Map<String, Object> map) {
        if( StringUtils.isNotEmpty(id) ) {
            map.put("couponActivityTemplate", couponActivityTemplateService.get(id));
        }
    }

    @RequestMapping("/coupon_activity_template/table_data")
    public @ResponseBody
    Map<String, Object> list( CouponActivityTemplateDataTableQueryArgWapper arg) {

        Map<String, Object> result = new HashMap<>();

        PageInfo<CouponActivityTemplate> pageInfo = null;
        try {
            pageInfo = couponActivityTemplateService.selectLikePage(arg.getSearchText(), arg.getPageNum(), arg.getLength(), arg.getOrderStr());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        List<CouponActivityTemplateJson> data = new ArrayList<>();

        int index = 1;

        for (CouponActivityTemplate template : pageInfo.getList()) {
            data.add(new CouponActivityTemplateJson(index, template));
            index++;
        }

        result.put("draw", arg.getDraw());
        result.put("recordsTotal", pageInfo.getTotal());
        result.put("recordsFiltered", pageInfo.getTotal());
        result.put("data", data);

        return result;
    }

    @RequestMapping(value = "/coupon_activity_template", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> batchDelete(@RequestParam("ids") String[] ids, @RequestParam(value = "ts", required = false) Long[] ts) {

        Map<String, Object> result = new HashMap<>();

        List<CouponActivityTemplate> templates = new ArrayList<>();
        List<String> templateIds = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            CouponActivityTemplate template = new CouponActivityTemplate();
            template.setId(ids[i]);
            if(ts != null && ts[i] != null) {
                template.setTs(new Date(ts[i]));
            }

            templateIds.add(template.getId());
            templates.add(template);
        }

        try {
            couponActivityTemplateService.deleteByBill(templates);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/coupon_activity_template/status/{status}", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> updateStatus(@PathVariable Short status, @RequestParam("ids") String[] ids, @RequestParam(value = "ts", required = false) Long[] ts) {

        Map<String, Object> result = new HashMap<>();

        List<CouponActivityTemplate> templateList = new ArrayList<>();

        for (int i = 0; i < ids.length; i++) {
            CouponActivityTemplate template = new CouponActivityTemplate();
            template.setId(ids[i]);
            if(ts != null && ts[i] != null) {
                template.setTs(new Date(ts[i]));
            }
            templateList.add(template);
        }

        try {
            if(status == DBConst.STATUS_ENABLE) {
                couponActivityTemplateService.enable(templateList);
            }else {
                couponActivityTemplateService.disable(templateList);
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
