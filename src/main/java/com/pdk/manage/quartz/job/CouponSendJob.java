package com.pdk.manage.quartz.job;

import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.service.coupon.CouponActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Date;

/**
 * Created by liuhaiming on 2015/9/15.
 */
@Controller
public class CouponSendJob {
    @Autowired
    CouponActivityService couponActivityService;
    private static final Logger log = LoggerFactory.getLogger(CouponSendJob.class);

    public void excuteJob() {
        try {
            couponActivityService.autoSendCoupon4Activity();
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }
}
