package com.pdk.manage.service.app;

import com.pdk.manage.dao.app.AppInfoMapper;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.app.request.OrderListRequestModel;
import com.pdk.manage.model.app.request.ReportRequestModel;
import com.pdk.manage.model.app.response.OrderDetailResponseModel;
import com.pdk.manage.model.app.response.OrderInfoResponseModel;
import com.pdk.manage.model.app.response.ReportResponseModel;
import com.pdk.manage.model.app.response.ReportSumInfoModel;
import com.pdk.manage.model.flow.FlowTemplateInstanceUnit;
import com.pdk.manage.model.order.Order;
import com.pdk.manage.model.order.OrderDetail;
import com.pdk.manage.service.flow.FlowTemplateInstanceUnitService;
import com.pdk.manage.service.order.OrderDetailService;
import com.pdk.manage.service.order.OrderService;
import com.pdk.manage.util.CommonUtil;
import com.pdk.manage.util.DBConst;
import com.pdk.manage.util.MoneyUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 程祥 on 15/9/25.
 * Function： 手机端服务实现
 */
@Service
public class AppOperateServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(AppOperateServiceImpl.class);


    @Autowired
    private AppInfoMapper appInfoMapper;

    @Autowired
    private FlowTemplateInstanceUnitService instanceUnitService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderService orderService;


//queryFinishedNum
    public int getFinishedNum(String userId) throws BusinessException{

        return appInfoMapper.queryFinishedNum(userId);

    }


    public List<OrderInfoResponseModel> getorderInfoList(OrderListRequestModel requestModel) throws BusinessException{
        return appInfoMapper.getorderInfoList(requestModel);
    }

    public OrderDetailResponseModel getOrderDetailInfo(String orderId) throws BusinessException{
        OrderDetailResponseModel responseModel = new OrderDetailResponseModel();

        OrderInfoResponseModel headInfo = appInfoMapper.getOrderDetailById(orderId);
        if(headInfo==null||headInfo.getOrderid()==null){
            return responseModel;
        }
        responseModel = new OrderDetailResponseModel(headInfo);

        List<OrderDetail> bodys = orderDetailService.getGoodsByOrderId(orderId);
        //设置商品图片url值
        if(bodys!=null&&bodys.size()>0){
            String baseUrl = CommonUtil.getResourcePath();
            for (OrderDetail detail:bodys){
                if(StringUtils.isNotEmpty(detail.getImgUrl())){
                    detail.setImgUrl(baseUrl+"/"+detail.getImgUrl());
                }
            }
        }

        responseModel.setList(bodys);


//        List<FlowTemplateInstanceUnit> instanceUnits = instanceUnitService.getFlowTemplateInstanceUnitList(headInfo.getInstanceid());
//        int unitsLen = instanceUnits.size();
//        String[][] arr = new String[unitsLen][unitsLen];

        //TODO 待处理 需求未定
        return responseModel;
    }


    /**
     * 查询订单汇总金额
     * @param model
     * @return
     * @throws BusinessException
     */
    public ReportResponseModel getSumDatas(ReportRequestModel model) throws BusinessException{

        ReportResponseModel responseModel = new ReportResponseModel();

        List<ReportSumInfoModel> sumInfoModels = appInfoMapper.querySumDataByDate(model);
        List<OrderInfoResponseModel> orderInfoList = appInfoMapper.queryOrderListByDate(model);

        if(sumInfoModels==null||sumInfoModels.size()==0){
            logger.debug("getSumDatas sumInfoModels为空:"+sumInfoModels.size());
            return null;
        }
        logger.debug("getSumDatas sumInfoModels.size():"+sumInfoModels.size());
        //合并现金支付与在线支付
        for(ReportSumInfoModel tempModel : sumInfoModels){
            if(DBConst.PAY_WEBCHAT==tempModel.getPaytype()){
                //微信支付
                responseModel.setOnlingpaymny(tempModel.getTotalmny());
                responseModel.setExpendmny(
                        MoneyUtil.safeAdd(tempModel.getTotalmny(),tempModel.getTipmny()));
//
                responseModel.setTipmny(tempModel.getTipmny());
            }else {
                //现金支付
                responseModel.setCashpaymny(tempModel.getTotalmny());
            }
            responseModel.setTotalmny(
                    MoneyUtil.safeAdd(responseModel.getOnlingpaymny(),
                            responseModel.getCashpaymny())
            );
        }
        responseModel.setList(orderInfoList);
        return responseModel;
    }

    /**
     * 修改订单支付类型
     * @param orderId
     * @param payType
     */
    @Transactional(rollbackFor = Exception.class)
    public void updatePayType(String orderId,Short payType) throws BusinessException{
        Order order = orderService.get(orderId);
        order.setPayType(payType);
        orderService.update(order);
    }


}
