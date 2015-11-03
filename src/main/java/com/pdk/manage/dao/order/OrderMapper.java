package com.pdk.manage.dao.order;

import com.pdk.manage.dao.common.BaseDao;
import com.pdk.manage.dto.report.ReportIndexJson;
import com.pdk.manage.model.order.*;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.core.jmx.RingBufferAdminMBean;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderMapper extends BaseDao<Order> {

    List<OrderTable> getOrderPageData(OrderRequestModel requestModel);

    int selectCountNum(OrderRequestModel requestModel);

    List<Order> getOrderDataForChart(OrderRequestModel requestModel);

    List<OrderTable> getOrderDataByFlowArgs(OrderFlowRequestMode requestMode);

    List<UserInfoForCoupon> getUserListForCoupon(@Param("sqlWhere") String sqlWhere, @Param("sqlHaving") String sqlHaving, @Param("searchText") String searchText);

    int queryFinishedNum(String userId);

    List<CountNumModel> queryOrderNumByFlowUnitIds(OrderFlowUnitReqModel reqModel);

    List<CountNumModel> queryOrderNumByTemplateUnitIds(List<String> templateUnitIds);

    List<Order> queryByStartDate(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    List<YwyMnyInfoModel> queryOrderByDateAndName(@Param("ywyId")String ywyId,@Param("queryDate")String queryDate);

    ReportIndexModel queryOrderInfo(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    ReportIndexModel queryOrderPayed(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    ReportIndexModel queryOrderUnPayed(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    ReportIndexModel queryOrderFeeInfo(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    ReportIndexModel queryOrderCouponInfo(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    List<OrderFlowTypeModel> queryOrderFlowTypeCount(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
}