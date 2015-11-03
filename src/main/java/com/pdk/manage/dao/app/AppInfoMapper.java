package com.pdk.manage.dao.app;

import com.pdk.manage.model.app.request.OrderListRequestModel;
import com.pdk.manage.model.app.request.ReportRequestModel;
import com.pdk.manage.model.app.response.OrderInfoResponseModel;
import com.pdk.manage.model.app.response.ReportSumInfoModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 程祥 on 15/9/25.
 * Function：订单相关的dao处理
 */
@Repository
public interface AppInfoMapper {

    List<OrderInfoResponseModel> getorderInfoList(OrderListRequestModel requestModel);

    int queryFinishedNum(String userId);

    OrderInfoResponseModel getOrderDetailById(String orderid);

    List<ReportSumInfoModel> querySumDataByDate(ReportRequestModel model);

    List<OrderInfoResponseModel> queryOrderListByDate(ReportRequestModel model);

}
