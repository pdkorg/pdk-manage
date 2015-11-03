package com.pdk.manage.action.wechat;

import com.github.pagehelper.PageInfo;
import com.pdk.manage.common.wapper.coupon.CouponPageQueryWapper;
import com.pdk.manage.dto.coupon.CouponJson;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.coupon.CouponUserRelation;
import com.pdk.manage.model.sm.User;
import com.pdk.manage.service.coupon.CouponActivityService;
import com.pdk.manage.service.coupon.CouponUserRelationService;
import com.pdk.manage.service.sm.SmUserService;
import com.pdk.manage.util.DBConst;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 优惠券相关对外接口
 * Created by liuhaiming on 2015/8/30.
 */
@Controller
@RequestMapping("/wechat/coupon")
public class WeChatCouponAction {
    private static final Logger log = LoggerFactory.getLogger(WeChatCouponAction.class);

    @Autowired
    private CouponUserRelationService couponUserRelationService;

    @Autowired
    SmUserService userService;

    @Autowired
    private CouponActivityService couponActivityService;

    @RequestMapping("/user/{sourceId}")
    public @ResponseBody Map<String, Object> list(@PathVariable("sourceId") String sourceId) {
        Map<String, Object> result = new HashMap<>();
        Date currDate = Calendar.getInstance().getTime();

        List<CouponUserRelation> effectLst =  couponUserRelationService.getEffectCoupon(sourceId, currDate);
        List<CouponUserRelation> unEffectLst = couponUserRelationService.getUnEffectCoupon(sourceId, currDate);

        List<CouponJson> effectJson = getCouponJson(effectLst);
        List<CouponJson> unEffectJson = getCouponJson(unEffectLst);

        result.put("effectLst", effectJson);
        result.put("unEffectLst", unEffectJson);
        result.put("result", "success");
        return result;
    }

    @RequestMapping("/user/{sourceId}/overdue")
    public @ResponseBody Map<String, Object> overDueList(@PathVariable("sourceId") String sourceId, CouponPageQueryWapper wapper) {
        Map<String, Object> result = new HashMap<>();

        Date currDate = Calendar.getInstance().getTime();
        PageInfo<CouponUserRelation> overDuePageInfo = couponUserRelationService.getOverdueCouponByPage(sourceId, currDate, wapper.getPageNo(), wapper.getPageSize());
        List<CouponJson> overDueJson = getCouponJson(overDuePageInfo.getList());

        result.put("totalPageNo", overDuePageInfo.getPages());
        result.put("total", overDuePageInfo.getTotal());
        result.put("overDueLst", overDueJson);
        result.put("result", "success");
        return result;
    }

    private List<CouponJson> getCouponJson(List<CouponUserRelation> unUseLst) {
        List<CouponJson> json = new ArrayList<>();

        int index = 1;
        for (CouponUserRelation relation : unUseLst) {
            json.add( new CouponJson(index, relation) );
            index++;
        }

        return json;
    }

    @RequestMapping("/user/{sourceId}/share")
    @ResponseBody
    public Map<String, Object> share(@PathVariable("sourceId") String sourceId) {
        Map<String, Object> result = new HashMap<>();

        try {
            User user = userService.findUserBySourceId(sourceId);
            CouponJson coupon = couponActivityService.sendCouponByRule(DBConst.ACTIVITY_SEND_CODE_SHARE, user);

            result.put("result", "success");
            result.put("coupon", coupon);

        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errMessage", "分享派发优惠券异常！");
        }
        return result;
    }

}