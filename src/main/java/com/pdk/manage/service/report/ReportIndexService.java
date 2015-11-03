package com.pdk.manage.service.report;

import com.pdk.manage.dao.order.OrderMapper;
import com.pdk.manage.dao.sm.UserDao;
import com.pdk.manage.dto.report.ReportIndexJson;
import com.pdk.manage.dto.report.ReportOrderJson;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.order.Order;
import com.pdk.manage.model.order.OrderFlowTypeModel;
import com.pdk.manage.model.order.ReportIndexModel;
import com.pdk.manage.model.order.YwyMnyInfoModel;
import com.pdk.manage.model.sm.User;
import com.pdk.manage.util.DBConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by liuhaiming on 2015/8/9.
 */
@Service
public class ReportIndexService {
    Logger log = LoggerFactory.getLogger(ReportIndexService.class);
    private static final SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserDao userDao;

    public ReportIndexJson getAllOrder() {
        ReportIndexJson json = new ReportIndexJson();

        ReportIndexModel reportIndexModel = orderMapper.queryOrderInfo(null, null);
        json.setOrderCount(reportIndexModel.getOrderCount());
        json.setMny(reportIndexModel.getMny());

        ReportIndexModel payedModel = orderMapper.queryOrderPayed(null, null);
        json.setPayedOrderCount(payedModel.getPayedOrderCount());
        json.setPayedMny(payedModel.getPayedMny());

        ReportIndexModel unPayedModel = orderMapper.queryOrderUnPayed(null, null);
        json.setUnPayedMny(unPayedModel.getUnPayedMny());

        ReportIndexModel feeModel = orderMapper.queryOrderFeeInfo(null, null);
        json.setFeeOrderCount(feeModel.getFeeOrderCount());
        json.setFeeMny(feeModel.getFeeMny());

        int newUserCnt = userDao.selectNewUserCount(Calendar.getInstance().getTime());
        int newUserkeepCnt = userDao.selectNewUserKeepCount(Calendar.getInstance().getTime());
        json.setNewUserCount(newUserCnt);

        json.setPayedMnyPersent(divMny(json.getPayedMny(), addMny(json.getPayedMny(), json.getUnPayedMny())).multiply(new BigDecimal(100)));
        json.setFeePersent(divMny(new BigDecimal(json.getFeeOrderCount()), new BigDecimal(json.getOrderCount())).multiply(new BigDecimal(100)));
        json.setPayedPersent(divMny(new BigDecimal(json.getPayedOrderCount()), new BigDecimal(json.getOrderCount())).multiply(new BigDecimal(100)));
        json.setUserKeepPersent(divMny(new BigDecimal(newUserkeepCnt), new BigDecimal(newUserCnt)).multiply(new BigDecimal(100)));
        return json;
    }

    private BigDecimal addMny(BigDecimal add, BigDecimal added) {
        add = ( add == null ? BigDecimal.ZERO : add );
        added = ( added == null ? BigDecimal.ZERO : added );

        return add.add(added);
    }
    private BigDecimal subMny(BigDecimal sub, BigDecimal sub2) {
        sub = ( sub == null ? BigDecimal.ZERO : sub );
        sub2 = ( sub2 == null ? BigDecimal.ZERO : sub2 );

        return sub.subtract(sub2);
    }

    private BigDecimal divMny(BigDecimal div, BigDecimal div2) {
        div = ( div == null ? BigDecimal.ZERO : div );
        div2 = ( div2 == null ? BigDecimal.ZERO : div2 );
        MathContext mc = new MathContext(2, RoundingMode.HALF_DOWN);
        return div2.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : div.divide(div2, mc);
    }

    public ReportIndexJson getOneMonthOrder(Date fromDate, Date toDate) {
        ReportIndexJson json = new ReportIndexJson();

        ReportIndexModel reportIndexModel = orderMapper.queryOrderInfo(fromDate, toDate);
        if ( reportIndexModel != null ) {
            json.setOrderCount(reportIndexModel.getOrderCount());
            json.setMny(reportIndexModel.getMny());
        }

        ReportIndexModel payedModel = orderMapper.queryOrderPayed(fromDate, toDate);
        if ( payedModel != null ) {
            json.setPayedOrderCount(payedModel.getPayedOrderCount());
            json.setPayedMny(payedModel.getPayedMny());
        }

        ReportIndexModel unPayedModel = orderMapper.queryOrderUnPayed(fromDate, toDate);
        if ( unPayedModel != null ) {
            json.setUnPayedMny(unPayedModel.getUnPayedMny());
        }

//        ReportIndexModel feeModel = orderMapper.queryOrderFeeInfo(fromDate, toDate);
//        if ( feeModel != null ) {
//            json.setFeeOrderCount(feeModel.getFeeOrderCount());
//            json.setFeeMny(feeModel.getFeeMny());
//        }

        ReportIndexModel couponModel = orderMapper.queryOrderCouponInfo(fromDate, toDate);
        if ( couponModel != null ) {
            json.setCouponMny(couponModel.getCouponMny());
        }

        return json;
    }

    public List<ReportIndexJson> getOrderByMonth() {
        List<ReportIndexJson> orderList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        int currMonth = cal.get(Calendar.MONTH);
        int month = 0;

        Date fstDate;
        Date lastDate;
        while ( month <= currMonth ) {
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            fstDate = cal.getTime();

            cal.add(Calendar.MONTH, 1);
            cal.add(Calendar.DAY_OF_MONTH, -1);
            lastDate = cal.getTime();

            orderList.add( getOneMonthOrder(fstDate, lastDate) );
            month++;
        }

        return orderList;
    }

    public List<OrderFlowTypeModel> getFlowTypeByMonth(int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date fstDate = cal.getTime();

        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDate = cal.getTime();
        return orderMapper.queryOrderFlowTypeCount(fstDate, lastDate);
    }
}