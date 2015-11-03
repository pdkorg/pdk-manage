package com.pdk.manage.service.report;

import com.pdk.manage.dao.order.OrderMapper;
import com.pdk.manage.dto.report.ReportOrderJson;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.order.Order;
import com.pdk.manage.model.order.YwyMnyInfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by liuhaiming on 2015/8/9.
 */
@Service
public class ReportOrderService {
    Logger log = LoggerFactory.getLogger(ReportOrderService.class);
    private static final SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private OrderMapper orderMapper;

    public List<ReportOrderJson> getOrderInfo(Date fromDate, Date toDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        fromDate = cal.getTime();

        cal.setTime(toDate);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        toDate = cal.getTime();

        List<Order> orderList = orderMapper.queryByStartDate(fromDate, toDate);
        Map<String, ReportOrderJson> orderJsonMap = new HashMap<>();
        ReportOrderJson json = null;
        String dateStr;

        List<ReportOrderJson> jsonList = new ArrayList<>();
        for (Order order : orderList) {
            dateStr = formatDate.format( order.getStartTime() );
            if ( orderJsonMap.containsKey(dateStr) ) {
                json = orderJsonMap.get( dateStr );
            } else {
                json = new ReportOrderJson();
                orderJsonMap.put(dateStr, json);
                jsonList.add( json );
            }

            json.appendMny(order);

        }

        Collections.sort(jsonList, new Comparator<ReportOrderJson>() {
            @Override
            public int compare(ReportOrderJson o1, ReportOrderJson o2) {
                return o1.getOrderDate().compareTo( o2.getOrderDate() );
            }
        });

        return jsonList;
    }

    /**
     * 查询业务员支出金额
     * @param ywyId
     * @param queryDate
     * @return
     * @throws BusinessException
     */
    public List<YwyMnyInfoModel> getYwyMnyDatas(
            String ywyId,String queryDate)throws BusinessException{

        return orderMapper.queryOrderByDateAndName(ywyId,queryDate);


    }
}