package com.pdk.manage.service.order;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pdk.manage.dao.order.OrderDetailMapper;
import com.pdk.manage.dao.order.OrderMapper;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.flow.FlowTemplateInstance;
import com.pdk.manage.model.flow.FlowTemplateInstanceUnit;
import com.pdk.manage.model.flow.FlowType;
import com.pdk.manage.model.flow.FlowUnit;
import com.pdk.manage.model.order.*;
import com.pdk.manage.model.sm.User;
import com.pdk.manage.service.BaseService;
import com.pdk.manage.service.flow.FlowTemplateInstanceService;
import com.pdk.manage.service.flow.FlowTypeService;
import com.pdk.manage.service.flow.FlowUnitService;
import com.pdk.manage.service.sm.SmUserService;
import com.pdk.manage.util.DBConst;
import com.pdk.manage.util.IdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


/**
 * Created by 程祥 on 15/8/17.
 * Function：
 */
@Service
public class OrderService extends BaseService<Order> {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private FlowUnitService flowUnitService;
    @Autowired
    private FlowTemplateInstanceService flowTemplateService;
    @Autowired
    private FlowTypeService flowTypeService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private SmUserService smUserService;

    @Override
    public String getModuleCode() {
        return IdGenerator.OR_MODULE_CODE;
    }


    public PageInfo<OrderTable> getOrderPageData(OrderRequestModel requestModel) throws BusinessException {

        requestModel.setLimitStart(requestModel.getPageSize() * (requestModel.getPageNum() - 1));

        //处理用户sourceid，转成用户userid
        if(StringUtils.isNotEmpty(requestModel.getSourceid())){
            User user = smUserService.findUserBySourceId(requestModel.getSourceid());
            requestModel.setCustomid(user.getId());
        }
        //设置默认排序方式 时间排序
        if(StringUtils.isEmpty(requestModel.getOrderStr())){
            requestModel.setOrderStr("start_time desc");
        }

        List<OrderTable> list = orderMapper.getOrderPageData(requestModel);
        //设置常量值
        addConstentVal(list);

        Page<OrderTable> page = new Page<>(requestModel.getPageNum(),requestModel.getPageSize());
        page.addAll(list);

        int count = getCountNum(requestModel);
        page.setTotal(count);
        page.setReasonable(Boolean.TRUE);

        return new PageInfo(page);

    }

    public int getCountNum(OrderRequestModel requestModel){
        return orderMapper.selectCountNum(requestModel);
    }

    /**
     * 通过查询条件查询订单聚合VO
     * @param requestModel
     * @return
     */
    public ShowOrderPageModel queryAggVOByArgs(OrderRequestModel requestModel) throws BusinessException{
        requestModel.setLimitStart(requestModel.getPageSize() * (requestModel.getPageNum() - 1));
        //处理用户sourceid，转成用户userid
        if(StringUtils.isNotEmpty(requestModel.getSourceid())){
            User user = smUserService.findUserBySourceId(requestModel.getSourceid());
            requestModel.setCustomid(user.getId());
        }
        //设置默认排序方式 时间排序
        if(StringUtils.isEmpty(requestModel.getOrderStr())){
            requestModel.setOrderStr("pay_status asc,start_time desc");
        }
        List<OrderTable> list = orderMapper.getOrderPageData(requestModel);
        ShowOrderPageModel result = new ShowOrderPageModel();
        if(list==null||list.size()==0){
            return result;
        }
        //设置常量值
        addConstentVal(list);

        result = addBodyByHead(list);
        //如果pagesize=0，则不需要传递订单分页信息
        if(0==requestModel.getPageSize()){
            return result;
        }

        int count = getCountNum(requestModel);
        result.setTotal(count);
        result.setPageNum(requestModel.getPageNum());
        result.setPageSize(requestModel.getPageSize());
        int pages =0;
        if(count%requestModel.getPageSize()==0){
            pages = count/requestModel.getPageSize();
        }else {
            pages = count/requestModel.getPageSize()+1;
        }
        result.setPages(pages);

        return result;
    }

    /**
     * 返回给聊天显示的订单聚合vo列表，
     * 主要查询参数为customid、payStatus、fromDate、toDate、 pageNum、pageSize
     * @param requestModel
     * @return
     * @throws BusinessException
     */
    public OrderPageModel getOrderDataForChart(OrderRequestModel requestModel)throws BusinessException{
        Page page = PageHelper.startPage(requestModel.getPageNum(), requestModel.getPageSize(), null);

        List<Order> orders = orderMapper.getOrderDataForChart(requestModel);

        List<String> orderIds = new ArrayList<>();
        for(Order t:orders){
            orderIds.add(t.getId());
        }
        List<OrderDetail> orderDetailList = getOrderDetailByOrderIds(orderIds);
        Map<String,List<OrderDetail>> tempMap = new HashMap<>();
        for(OrderDetail od:orderDetailList){
            List<OrderDetail> templ = tempMap.get(od.getOrderId());
            if(templ==null||templ.size()==0){
                List<OrderDetail> l = new ArrayList<>();
                l.add(od);
                tempMap.put(od.getOrderId(),l);
                continue;
            }
            templ.add(od);
        }
        List<ChartRequestModel> result = new ArrayList<>();
        for(Order t:orders){
            ChartRequestModel model = new ChartRequestModel();
            model.setHead(t);
            model.setBody(tempMap.get(t.getId()));
            result.add(model);
        }
        OrderPageModel pageModel = new OrderPageModel(page.getTotal(),page.getPages(),result);

        return pageModel;
    }

    public List<OrderTable> getOrderDataByOrderId(String orderId) throws BusinessException{
        OrderRequestModel requestModel = new OrderRequestModel();
        requestModel.setOrderId(orderId);
        List<OrderTable> list = orderMapper.getOrderPageData(requestModel);
        addConstentVal(list);

        return list;
    }

    public List<OrderDetail> getOrderDetailByOrderIds(List<String> list) throws BusinessException{
        if(list==null||list.size()==0){
            return new ArrayList<>();
        }
        return orderDetailMapper.getOrderDetailByOrderIds(list);
    }



    /**
     * 删除订单（包含表头和表体）
     * @param order
     * @return
     * @throws BusinessException
     */
    @Transactional(rollbackFor = Exception.class)
    public int deleteOrder(Order order)throws BusinessException{

        int head = super.delete(order);
        int body =orderDetailMapper.deleteDetailsByOrderId(order.getId());
        return head+body;
    }

    /**
     * 新增保存
     * @param order
     * @param detailist
     * @return
     * @throws BusinessException
     */
    @Transactional(rollbackFor = Exception.class)
    public int saveOrder(Order order,List<OrderDetail> detailist) throws BusinessException{
        FlowTemplateInstance flowTemp = flowTemplateService.generateFlowTemplateInstance(order.getFlowtypeId());
        FlowType flowType = flowTypeService.get(order.getFlowtypeId());
        order.setFlowInstanceId(flowTemp.getId());
        //设置第一个流程环节
        FlowTemplateInstanceUnit instanceUnit = flowTemp.getFlowTemplateInstanceUnitList().get(0);
        if(instanceUnit!=null){
            FlowUnit unit = flowUnitService.get(instanceUnit.getFlowUnitId());
            order.setDeliveryStatus(unit.getName());
            order.setStatus(unit.getOrderStatus());
            order.setFlowAction(unit.getFlowActionCode());
        }

        //新增保存的时候是否完成为0
        order.setIsFlowFinish(DBConst.ORDER_FLOW_UNFINISH);

        int head = orderMapper.insert(order);
        int body =0;
        if(detailist!=null&&detailist.size()>0){
            body = orderDetailMapper.insertList(detailist);
        }


        //如果该业务类型为自动分配业务员，则需要判断当前流程环节是否为分配，
        // 并将自动完成分配环节
        if("Y".equalsIgnoreCase(flowType.getIsAutoAssignDeliver())
                && !StringUtils.isEmpty(flowType.getDeliverId())
                &&DBConst.FLOW_ACTION_DISTRIBUTE==order.getFlowAction()){
            order = this.get(order.getId());
            order.setYwyId(flowType.getDeliverId());
            order.setShopManagerId(order.getCsId());
            //直接完成下一步
            changeOrderStatus(order,order.getCsId(),null);
        }

        return head+body;
    }


    @Transactional(rollbackFor = Exception.class)
    public void changeOrderStatus(Order order,String eid,Short payType) throws BusinessException{
        //先将业务员信息更新到数据库，因为完成当前流程环节的操作要通过订单id去查询最新订单
        this.update(order);
        //以后可注释掉，ts没有存毫秒
//        order = this.get(order.getId());

        FlowTemplateInstanceUnit template =flowTemplateService.finishCurrFlowUnit(order.getId(), eid, new Date(), new Date());

        //如果是送达状态，则更新相应数据
        if(DBConst.FLOW_ACTION_DELIVER_FINISH==order.getFlowAction()){
            order.setIsFlowFinish(DBConst.ORDER_FLOW_FINISHED);

        }
        //如果是支付状态，则更新相应数据
        if(DBConst.FLOW_ACTION_PAY==order.getFlowAction()){
            BigDecimal temp = order.getActualMny();
            if(temp == null ||BigDecimal.ZERO.compareTo(temp)==0){
                temp = order.getMny();
            }
            //设置实际支付金额
            order.setActualMny(temp);

            order.setPayStatus(DBConst.ORDER_PAYED);
            //payType 默认为现金支付
            order.setPayType(payType==null?DBConst.PAY_CASH:payType);
        }

        //流程完成，则订单完成
        if(template==null){
            order.setStatus(DBConst.ORDER_STATUS_FINISHED);
            order.setEndTime(new Date());
        }else{
            FlowUnit unit = flowUnitService.get(template.getFlowUnitId());
            order.setDeliveryStatus(unit.getName());
            //必须在判断送达状态后更新
            order.setFlowAction(unit.getFlowActionCode());
            if(unit.getOrderStatus()!=null&&unit.getOrderStatus()>=0){
                order.setStatus(unit.getOrderStatus());
            }
        }

        //如果该业务类型为自动分配业务员，则需要判断当前流程环节是否为分配，
        // 并将自动完成分配环节
        FlowType flowType = flowTypeService.get(order.getFlowtypeId());
        if("Y".equalsIgnoreCase(flowType.getIsAutoAssignDeliver())
                && !StringUtils.isEmpty(flowType.getDeliverId())
                &&DBConst.FLOW_ACTION_DISTRIBUTE==order.getFlowAction()){
            order.setYwyId(flowType.getDeliverId());
            order.setShopManagerId(eid);
            changeOrderStatus(order,eid,null);
            return;
        }

        //如果支付了，下一个动作是支付，则自动完成支付流程
        if(template!=null && DBConst.ORDER_PAYED==order.getPayStatus()
                &&DBConst.FLOW_ACTION_PAY==order.getFlowAction()){
            changeOrderStatus(order, eid,null);
            //更新一次即可
            return;
        }

        this.update(order);
    }

    @Transactional(rollbackFor = Exception.class)
    public int editSave(Order nOrder,List<OrderDetail> detailist)throws BusinessException{
        Order oldOrder = super.get(nOrder.getId());

        oldOrder.setAdress(nOrder.getAdress());
        oldOrder.setPayType(nOrder.getPayType());
        oldOrder.setMny(nOrder.getMny());
        oldOrder.setCouponMny(nOrder.getCouponMny());
        oldOrder.setActualMny(nOrder.getActualMny());
        oldOrder.setFeeMny(nOrder.getFeeMny());
        oldOrder.setReserveTime(nOrder.getReserveTime());
        oldOrder.setMemo(nOrder.getMemo());
        //ts校验
        if(nOrder.getTs().compareTo(oldOrder.getTs()) != 0) {
            return -1;
        }

        int head = super.update(oldOrder);

        List<OrderDetail> oldGoodsList = orderDetailMapper.getGoodsByOrderId(nOrder.getId());
        Map<String,OrderDetail> oldmap = hashInitlizeList(oldGoodsList);
        //前台新增的商品集合
        List<OrderDetail> saddlist = new ArrayList<>();
        //前台修改过的商品集合  参数detailist中不包含已删除的
        List<OrderDetail> supdatalist = new ArrayList<>();
        if(detailist == null){
            detailist = new ArrayList<>();
        }
        for(OrderDetail temp:detailist){
            if(temp.getId()==null||temp.getId().length()<10){
                temp.setId(IdGenerator.generateId(IdGenerator.OR_MODULE_CODE));
                temp.setOrderId(nOrder.getId());
                if(StringUtils.isEmpty(temp.getReserveTime())){
                    temp.setReserveTime(null);
                }
                temp.setTs(new Date());
                saddlist.add(temp);
            }else {
                supdatalist.add(temp);
            }
        }
        Map<String,OrderDetail> supdateMap = hashInitlizeList(supdatalist);
        //用于传给mybatis
        List<OrderDetail> updateBodyList = new ArrayList<>();
        Iterator<String> it = oldmap.keySet().iterator();
        while (it.hasNext()){
            String key = it.next();
            OrderDetail temp = oldmap.get(key);
            OrderDetail utemp = supdateMap.get(key);
            if(utemp==null){
                //被删除了
                temp.setDr(DBConst.DR_DEL);
                temp.setTs(new Date());
            }else{
                //被修改了
                temp.setName(utemp.getName());
                temp.setNum(utemp.getNum());
                temp.setBuyAdress(utemp.getBuyAdress());
                temp.setPrice(utemp.getPrice());
                temp.setGoodsMny(utemp.getGoodsMny());
                temp.setServiceMny(utemp.getServiceMny());
                temp.setOthMny(utemp.getOthMny());
                temp.setBuyAdress(utemp.getBuyAdress());
                temp.setReserveTime(StringUtils.isEmpty(utemp.getReserveTime())?null:utemp.getReserveTime());
                temp.setMemo(utemp.getMemo());
                temp.setUnitId(utemp.getUnitId());
                temp.setImgUrl(utemp.getImgUrl());
                temp.setTs(new Date());

            }
            updateBodyList.add(temp);
        }

        int updatebody=0;
        int addbody =0;
        if(saddlist.size()>0){
            addbody =orderDetailService.save(saddlist);
        }
        if(updateBodyList.size()>0){
            updatebody =  orderDetailMapper.updateDetailList(updateBodyList);
        }

        return head+addbody+updatebody;
    }

    public ShowOrderPageModel getOrderDataByFlowArgs(OrderFlowRequestMode requestMode,int pageNum)throws BusinessException{

        if(pageNum<=0){
            pageNum=1;
        }
        Page page = PageHelper.startPage(pageNum, 5);
        List<OrderTable> orderList = orderMapper.getOrderDataByFlowArgs(requestMode);
        ShowOrderPageModel result = new ShowOrderPageModel();
        if(orderList==null||orderList.size()==0){
            return result;
        }
        result = addBodyByHead(orderList);
        result.setPageNum(pageNum);
        result.setPages(page.getPages());
        result.setPageSize(page.getPageSize());
        result.setTotal(page.getTotal());

        return result;

    }

    /**
     * 根据表头将表体添加到聚合VO中
     * @param orderList
     * @return
     */
    private ShowOrderPageModel addBodyByHead(List<OrderTable> orderList){
        ShowOrderPageModel result = new ShowOrderPageModel();
        //添加常量~
        addConstentVal(orderList);
        List<String> orderIds = new ArrayList<>();
        for(OrderTable temp : orderList){
            orderIds.add(temp.getId());
        }
        List<OrderDetail> orderDetailList = orderDetailMapper.getOrderDetailByOrderIds(orderIds);
        Map<String,List<OrderDetail>> detailMap = new HashMap<>();
        for(OrderDetail temp : orderDetailList){
            String orderId = temp.getOrderId();
            List<OrderDetail> list = detailMap.get(orderId);
            if(list==null||list.size()==0){
                list = new ArrayList<>();
            }
            list.add(temp);
            detailMap.put(orderId,list);
        }
        List<ShowOrderAggVO> modelList = new ArrayList<>();
        for(OrderTable temp:orderList){
            String orderId = temp.getId();
            ShowOrderAggVO model = new ShowOrderAggVO();
            model.setHead(temp);
            model.setBody(detailMap.get(orderId));
            modelList.add(model);
        }
        result.setOrderAggModel(modelList);

        return result;
    }

    /**
     * 根据requestModel查询订单中的用户id列表
     * @param sqlWhere
     * @param sqlHaving
     * @return list
     */
    public List<UserInfoForCoupon> getUserListForCoupon(String sqlWhere, String sqlHaving, String searchText) throws BusinessException{

        List<UserInfoForCoupon> result =  orderMapper.getUserListForCoupon(sqlWhere, sqlHaving, searchText);
        return result;
    }

    /**
     * 根据requestModel查询订单中的用户id列表
     * @param sqlWhere
     * @param sqlHaving
     * @return list
     */
    public PageInfo<UserInfoForCoupon> getUserPageInfoForCoupon(int pageNum, int pageSize, String orderBy, String sqlWhere, String sqlHaving, String searchText) throws BusinessException{
        PageHelper.startPage(pageNum, pageSize, orderBy);
        return new PageInfo<>(orderMapper.getUserListForCoupon(sqlWhere, sqlHaving, searchText));
    }

    /**
     * 支付功能
     * @param orderId
     * @param payMny
     * @param couponMny
     * @return
     * @throws BusinessException
     */
    @Transactional(rollbackFor = Exception.class)
    public int payOrder(String orderId,Short payType,BigDecimal couponMny,BigDecimal payMny,BigDecimal tipMny)throws BusinessException{
        Order order = this.get(orderId);
        if(order==null){
            return -1;
        }

        if(DBConst.ORDER_PAYED ==order.getPayStatus()){
            return -2;
        }

        //如果流程动作已完成，则更新订单状态和订单结束时间
        /**if(DBConst.ORDER_FLOW_FINISHED==order.getIsFlowFinish()){
            order.setEndTime(new Date());
            order.setStatus(DBConst.ORDER_STATUS_FINISHED);
        }*/
        BigDecimal tempZero = new BigDecimal(0.00);
        order.setActualMny(payMny == null ? order.getMny() : payMny);
        order.setCouponMny(couponMny == null ? tempZero : couponMny);
        order.setFeeMny(tipMny == null ? tempZero : tipMny);
        order.setPayType(payType);
        order.setPayStatus(DBConst.ORDER_PAYED);
        if(DBConst.FLOW_ACTION_PAY==order.getFlowAction()){
            changeOrderStatus(order,"",payType);
        }else {
            this.update(order);
        }

        return 1;
    }


    //设置常量值
    private void addConstentVal(List<OrderTable> list){
        if(list==null||list.size()==0){
            return;
        }
        //设置常量值
        for(OrderTable temp:list){
            //设置paytypename
            if (temp.getPayType()==null|| DBConst.PAY_WEBCHAT==temp.getPayType()){
                temp.setPayTypeName(DBConst.PAY_WEBCHAT_NAME);
            }else {
                temp.setPayTypeName(DBConst.PAY_CASH_NAME);
            }
            //设置订单状态名称
            temp.setStatusName(getStateName(temp.getStatus()));
            //设置用户性别
            if (DBConst.SEX_MALE==temp.getUserSex()){
                temp.setUserSexName("男");
            }else {
                temp.setUserSexName("女");
            }
            //设置支付状态名称
            if(temp.getPayStatus()==null||DBConst.ORDER_UNPAY==temp.getPayStatus()){
                temp.setPayStatusName(DBConst.ORDER_UNPAY_NAME);
            }else {
                temp.setPayStatusName(DBConst.ORDER_PAYED_NAME);
            }
        }
    }

    private String getStateName(Short state){
        String stateName = "";
        if (DBConst.ORDER_STATUS_UNDISTRIBUTE.equals(state)){
            stateName = DBConst.ORDER_UNDISTRIBUTE_NAME;
        }else if(DBConst.ORDER_STATUS_UNPRICE.equals(state)){
            stateName = DBConst.ORDER_UNPRICE_NAME;
        }else if(DBConst.ORDER_STATUS_UNFINISH.equals(state)){
            stateName = DBConst.ORDER_UNFINISH_NAME;
        }else if(DBConst.ORDER_STATUS_FINISHED.equals(state)){
            stateName = DBConst.ORDER_FINISHED_NAME;
        }else if(DBConst.ORDER_STATUS_CANCELED.equals(state)){
            stateName = DBConst.ORDER_CANCELED_NAME;
        }
        return stateName;
    }

    private Map<String,OrderDetail> hashInitlizeList(List<OrderDetail> detailList){
        Map<String,OrderDetail> map = new HashMap<>();
        for(OrderDetail detail : detailList){
            map.put(detail.getId(),detail);
        }
        return map;
    }


    public int getFinishedNum(String userId) throws BusinessException{

        return orderMapper.queryFinishedNum(userId);

    }

    public List<CountNumModel> queryOrderNumByFlowUnitIds(OrderFlowUnitReqModel reqModel) throws BusinessException{

        return orderMapper.queryOrderNumByFlowUnitIds(reqModel);
    }

    public List<CountNumModel> queryOrderNumByTemplateUnitIds(List<String> templataUnitIds) throws BusinessException{
        return orderMapper.queryOrderNumByTemplateUnitIds(templataUnitIds);
    }
}
