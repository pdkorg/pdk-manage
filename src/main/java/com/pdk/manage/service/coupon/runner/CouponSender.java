package com.pdk.manage.service.coupon.runner;

import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.coupon.CouponUserRelation;
import com.pdk.manage.model.coupon.CouponUserSended;
import com.pdk.manage.service.coupon.CouponUserRelationService;
import com.pdk.manage.service.coupon.CouponUserSendedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * Created by liuhaiming on 2015/10/23.
 */
public class CouponSender implements Callable<Boolean> {
    private static final int SEND_MAX_SIZE_ONCE = 1000;

    private static final Logger log = LoggerFactory.getLogger(CouponSender.class);

    private int threadNo = 0;

    private CountDownLatch countDownLatch;

    private CouponUserSendedService couponUserSendedService;

    private CouponUserRelationService couponUserRelationService;

    private List<CouponUserRelation> couponList;

    private List<CouponUserSended> sendLst;

    public Boolean call() {
        try {
            log.info("=================线程" + threadNo + "发送量：" + couponList.size() + "===============================");
            long t1 = Calendar.getInstance().getTimeInMillis();
            oneThreadSendCoupon();
            long t2 = Calendar.getInstance().getTimeInMillis();
            log.info("================thread number: " + threadNo + "; one thread send times:" + ( t2 - t1 ) / 1000 + "===================================");
            return true;
        } catch (BusinessException e) {
            log.error("该线程优惠券发送失败", e);
            return false;
        } finally {
            countDownLatch.countDown();
        }
    }

    public CouponSender(int no,
                        CouponUserSendedService couponUserSendedService,
                        CouponUserRelationService couponUserRelationService,
                        List<CouponUserRelation> couponList,
                        List<CouponUserSended> sendLst,
                        CountDownLatch countDownLatch) {
        threadNo = no;
        this.couponUserSendedService = couponUserSendedService;
        this.couponUserRelationService = couponUserRelationService;
        this.couponList = couponList;
        this.sendLst = sendLst;
        this.countDownLatch = countDownLatch;
    }

    private void oneThreadSendCoupon() throws BusinessException {
        int size = SEND_MAX_SIZE_ONCE;
        if (couponList.size() > 0) {
            List<CouponUserRelation> coupons = new ArrayList<>();
            for (int idx = 0; idx < couponList.size(); idx++) {
                coupons.add(couponList.get(idx));
                if ( coupons.size() > size ) {
                    couponUserRelationService.save(coupons);
                    coupons.clear();
                }
            }

            if (coupons.size() > 0) {
                couponUserRelationService.save(coupons);
            }

        }

        if ( sendLst.size() > 0 ) {
            List<CouponUserSended> snaps = new ArrayList<>();
            for (int idx = 0; idx < sendLst.size(); idx++) {
                snaps.add(sendLst.get(idx));
                if ( snaps.size() > size ) {
                    couponUserSendedService.save(snaps);
                    snaps.clear();
                }
            }

            if (snaps.size() > 0) {
                couponUserSendedService.save(snaps);
            }
        }
    }
}
