package com.pdk.manage.action.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.pdk.manage.common.wapper.order.OrderDataTableQueryArgWapper;
import com.pdk.manage.common.wapper.order.OrderEmployeeTableWapper;
import com.pdk.manage.common.wapper.order.OrderUserTableWapper;
import com.pdk.manage.dto.sm.EmployeeJson;
import com.pdk.manage.dto.sm.UserJson;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.app.response.OrderDetailResponseModel;
import com.pdk.manage.model.bd.Address;
import com.pdk.manage.model.bd.Unit;
import com.pdk.manage.model.flow.FlowType;
import com.pdk.manage.model.order.*;
import com.pdk.manage.model.sm.Employee;
import com.pdk.manage.model.sm.User;
import com.pdk.manage.service.app.AppOperateServiceImpl;
import com.pdk.manage.service.bd.AddressService;
import com.pdk.manage.service.bd.UnitService;
import com.pdk.manage.service.flow.FlowTemplateInstanceService;
import com.pdk.manage.service.flow.FlowTypeService;
import com.pdk.manage.service.flow.FlowUnitService;
import com.pdk.manage.service.getui.GetTuiSendMessUtil;
import com.pdk.manage.service.order.OrderService;
import com.pdk.manage.service.sm.EmployeeService;
import com.pdk.manage.service.sm.SmUserService;
import com.pdk.manage.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by 程祥 on 15/8/19.
 * Function：订单相关处理
 */
@Controller
@RequestMapping(value = "/order")
public class OrderAction {

    private static final Logger log = LoggerFactory.getLogger("OrderLog");

    @Autowired
    private HttpSession session;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private FlowTemplateInstanceService flowTemplateService;

    @Autowired
    private SmUserService smUserService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private FlowTypeService flowTypeService;

    @Autowired
    private FlowUnitService flowUnitService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private AppOperateServiceImpl appOperateService;

    @RequestMapping("/orderindex")
    public String index() {
        return "order/orderindex";
    }

    @RequestMapping(value = "/table_data", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> list(
            OrderDataTableQueryArgWapper arg,OrderRequestModel query) {

        Map<String, Object> result = new HashMap<>();

        query.setPageNum(arg.getPageNum());
        query.setPageSize(arg.getLength());
        query.setOrderStr(arg.getOrderStr());


        PageInfo<OrderTable> pageInfo = null;
        try {
            pageInfo = orderService.getOrderPageData(query);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        List<OrderTable> data = new ArrayList<>();

        if(pageInfo==null){
            return result;
        }

        int index = 1;
        for (OrderTable temp : pageInfo.getList()) {
            temp.setRowId(temp.getId());
            temp.setIndex(index);
            data.add(temp);
            index++;
        }
        result.put("draw", arg.getDraw());
        result.put("recordsTotal", pageInfo.getTotal());
        result.put("recordsFiltered", pageInfo.getTotal());
        result.put("data", data);
        return result;

    }

    /**
     * 业务类型下拉数据
     * @return
     */
    @RequestMapping(value = "/flowlistdata")
    @ResponseBody
    public Map<String,Object> getFlowTableData(){
        Map<String, Object> result = new HashMap<>();
        List<FlowType> data = new ArrayList<>();
        try {
            data = flowTypeService.getFlowTypeForOrder();
        }catch (Exception e){
            log.error(e.getMessage(),e);
            result.put("result","error");
            result.put("message","查询流程类型出错！");
        }
        result.put("result","success");
        result.put("data",data);
        return result;
    }

    /**
     * 物品单位下拉数据
     * @return
     */
    @RequestMapping(value = "/unitlistdata")
    @ResponseBody
    public Map<String,Object> getUnitListData(){
        Map<String, Object> result = new HashMap<>();
        List<Unit> data = new ArrayList<>();
        try {
            data = unitService.selectAll();
        }catch (Exception e){
            log.error(e.getMessage(),e);
            result.put("result","error");
            result.put("message","查询物品单位出错！");
        }
        result.put("result","success");
        result.put("data",data);
        return result;
    }


    /**
     *
     * @param type 1代表业务员，2客服
     * @return
     */
    @RequestMapping(value = "/employeetable/{type}")
    @ResponseBody
    public Map<String,Object> employeeRefData(OrderEmployeeTableWapper arg,@PathVariable("type") String type){
        Map<String, Object> result = new HashMap<>();

        String positionId = "";
        if("2".equals(type)){
            positionId = DBConst.POSITION_WAITER;
        }else {
            //2代表客服，1、3代表业务员
            positionId = DBConst.POSITION_YEWUYUAN;
        }

        PageInfo<Employee> pageInfo = null;
        try {
            pageInfo = employeeService.mySelectLikePageByPositionId(arg.getSearchText(), arg.getPageNum(), arg.getLength(), arg.getOrderStr(), positionId);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        List<EmployeeJson> data = new ArrayList<>();

        if(pageInfo==null){
            return result;
        }

        int index = 1;
        for (Employee temp : pageInfo.getList()) {
            EmployeeJson emJson = new EmployeeJson();
            emJson.setId(temp.getId());
            emJson.setCode(temp.getCode());
            emJson.setName(temp.getName());
            emJson.setSex(temp.getSex());
            if(temp.getSex()==DBConst.SEX_MALE){
                emJson.setSexName("男");
            }else {
                emJson.setSexName("女");
            }
            emJson.setPhone(temp.getPhone());
            emJson.setIndex(index);
            data.add(emJson);
            index++;
        }
        result.put("draw", arg.getDraw());
        result.put("recordsTotal", pageInfo.getTotal());
        result.put("recordsFiltered", pageInfo.getTotal());
        result.put("data", data);
        return result;
    }

    @RequestMapping(value = "/usertabel_data")
    @ResponseBody
    public Map<String,Object> getUserTableData(OrderUserTableWapper arg){
        Map<String, Object> result = new HashMap<>();

        PageInfo<User> pageInfo = null;
        try {
            pageInfo = smUserService.selectLikePage(arg.getSearchText(), arg.getPageNum(), arg.getLength(), arg.getOrderStr());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        List<UserJson> data = new ArrayList<>();
        if(pageInfo==null){
            return result;
        }

        int index = 1;
        for (User temp : pageInfo.getList()) {
            UserJson userJson = new UserJson();
            userJson.setIndex(index);
            userJson.setId(temp.getId());
            userJson.setName(temp.getName());
            userJson.setRealName(temp.getRealName());
            userJson.setSex(temp.getSex());
            if (temp.getSex() == DBConst.SEX_MALE) {
                userJson.setSexName("男");
            } else {
                userJson.setSexName("女");
            }
            userJson.setAge(temp.getAge());
            userJson.setPhone(temp.getPhone());
            userJson.setRegisterTime(temp.getRegisterTime());
            userJson.setUnRegisterTime(temp.getUnRegisterTime());
            data.add(userJson);
            index++;
        }
        result.put("draw", arg.getDraw());
        result.put("recordsTotal", pageInfo.getTotal());
        result.put("recordsFiltered", pageInfo.getTotal());
        result.put("data", data);
        return result;
    }


    /**
     * 删除订单
     * @param id
     * @param ts
     * @return
     */
    @RequestMapping(value = "/deleteorder",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> deleteOrder(String id, String ts){
        Map<String, Object> result = new HashMap<>();

        Order order = new Order();
        order.setTs(new Date(Long.parseLong(ts)));
        order.setId(id);
        try {
            orderService.deleteOrder(order);
            result.put("result", "success");
            result.put("message","删除成功~");
        }catch (BusinessException e){
            result.put("result","error");
            result.put("message","更新数据库出错~");
            log.error(e.getMessage(),e);
            return result;
        }

        return result;
    }

    //取消订单
    @RequestMapping(value = "/cancelOrder",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> cancelOrder(String id,Long ts){
        Map<String, Object> result = new HashMap<>();

        try {
            Order order = orderService.get(id);
            if(order.getTs().compareTo(new Date(ts)) != 0) {
                result.put("result","error");
                result.put("message","该订单已经被修改过，请刷新后重新操作!");
                return result;
            }

            order.setStatus(DBConst.ORDER_STATUS_CANCELED);

            orderService.update(order);
            result.put("result", "success");
            result.put("message","取消成功~");
        }catch (BusinessException e){
            result.put("result","error");
            result.put("message","更新数据库出错~");
            log.error(e.getMessage(),e);
        }
        return result;
    }

    /**
     * 修改保存订单
     * @param rModel
     * @return
     */
    @RequestMapping(value = "/editSaveOrder" ,method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> editSave(OrderEditRequestModel rModel){

        Map<String,Object> result = new HashMap<>();

        Order order = new Order();
        order.setId(rModel.getOrderid());
        order.setAdress(rModel.getOrderaddress());
        order.setPayType(Short.parseShort(rModel.getPaytype()));

        try{
            order.setFeeMny(new BigDecimal(rModel.getFeemny()));
            order.setMny(new BigDecimal(rModel.getOrdermny()));
            order.setCouponMny(new BigDecimal(rModel.getOnsalemny()));
            order.setActualMny(new BigDecimal(rModel.getRealcostmny()));
            order.setReserveTime(DateUtil.getDate(rModel.getReservetime(), DateUtil.FORMAT_MIN));
        }catch (Exception decimal){
            log.error(decimal.getMessage(), decimal);
            result.put("result", "error");
            result.put("message","金额或时间格式输入错误！");
            return result;
        }

        order.setMemo(rModel.getMemo());
        order.setTs(new Date(rModel.getTs()));
        log.debug("editSave---rModel.getBodydata()---->" + rModel.getBodydata());
        List<OrderDetail> goodsList = JSONObject.parseArray(
                rModel.getBodydata(),OrderDetail.class);

        try {
            int res = orderService.editSave(order,goodsList);
            if(res==-1){
                //ts校验
                result.put("result", "error");
                result.put("message","该订单已经被修改过，请刷新后重新修改！");
                return result;
            }
        }catch (BusinessException e1){
            log.error(e1.getMessage(), e1);
            result.put("result", "error");
            result.put("message","保存出错，请刷新后重新修改！");
            return result;
        }

        result.put("result", "success");
        result.put("message", "保存成功！");
        result.put("orderid", rModel.getOrderid());
        return result;
    }


    /**
     * 新增保存订单
     * @param rModel
     * @return
     */
    @RequestMapping(value = "/saveorder" ,method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> save(OrderEditRequestModel rModel){


        log.debug("save----rModel.getBodydata()---->" + rModel.getBodydata());
        Map<String,Object> result = new HashMap<>();

        Order order = new Order();
        String orderid = IdGenerator.generateId(IdGenerator.OR_MODULE_CODE);
        order.setId(orderid);
        order.setCode(OrderCodeGenerator.getInstance().generateOrderCode());
        order.setUserId(rModel.getUserid());
        order.setAdress(rModel.getOrderaddress());
        order.setPayType(Short.parseShort(rModel.getPaytype()));


        order.setPayStatus(DBConst.ORDER_UNPAY);
        order.setStartTime(new Date());
        Employee e = (Employee)session.getAttribute(CommonConst.SESSION_ATTR_KEY_LOGIN_EMPLOYEE);
        order.setCsId(e.getId());
        order.setMemo(rModel.getMemo());
        order.setFlowtypeId(rModel.getOrdertype());
        order.setTs(new Date());
        order.setDr(DBConst.DR_NORMAL);
        //不能为空，如果为空，查询未完成订单的sql语句会有问题~
        order.setIsFlowFinish(DBConst.ORDER_FLOW_UNFINISH);
        try{
            order.setFeeMny(new BigDecimal(rModel.getFeemny()));
            order.setMny(new BigDecimal(rModel.getOrdermny()));
            order.setCouponMny(new BigDecimal(rModel.getOnsalemny()));
            order.setActualMny(new BigDecimal(rModel.getRealcostmny()));

            order.setReserveTime(DateUtil.getDate(rModel.getReservetime(), DateUtil.FORMAT_MIN));
        }catch (Exception exception){
            log.error(exception.getMessage(), exception);
            result.put("result", "error");
            result.put("message","金额或者时间格式输入错误！");
            return result;
        }

        List<OrderDetail> goodsList = JSONObject.parseArray(
                rModel.getBodydata(),OrderDetail.class);
        for (OrderDetail detail : goodsList) {
            detail.setId(IdGenerator.generateId(IdGenerator.OR_MODULE_CODE));
            detail.setOrderId(orderid);
            if(StringUtils.isEmpty(detail.getReserveTime())){
                detail.setReserveTime(null);
            }
            detail.setTs(new Date());
            detail.setDr(DBConst.DR_NORMAL);
        }
        try {
            orderService.saveOrder(order,goodsList);
        }catch (BusinessException exce){
            log.error(exce.getMessage(), exce);
            result.put("result", "error");
            result.put("message","保存订单出错！");
            return result;
        }

        result.put("result","success");
        result.put("orderid",orderid);
        result.put("message","保存成功!");
        return result;
    }

    /**
     * 聊天界面快速生成订单
     * @param jsonStr
     * @return
     */
    @RequestMapping(value = "/chartgenorder",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> chartGenOrder(String jsonStr){
        Map<String,Object> result = new HashMap<>();

        log.debug("chartGenOrder-->**** jsonStr:" + jsonStr);
        ChartRequestModel requestModel = null;
        try {
            requestModel = JSONObject.parseObject(jsonStr,ChartRequestModel.class);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("message","json转换出错~");
            return result;
        }

        Order order = requestModel.getHead();
        String orderid = IdGenerator.generateId(IdGenerator.OR_MODULE_CODE);
        order.setId(orderid);
        order.setCode(OrderCodeGenerator.getInstance().generateOrderCode());
        //根据用户id查询默认地址，并赋值
        Address address = addressService.selectDefaultByUserId(order.getUserId());
        order.setAdress(address.getFullAddress());
        order.setPayStatus(DBConst.ORDER_UNPAY);
        order.setStartTime(new Date());
        Employee e = (Employee)session.getAttribute(CommonConst.SESSION_ATTR_KEY_LOGIN_EMPLOYEE);
        order.setCsId(e.getId());
        order.setTs(new Date());
        order.setDr(DBConst.DR_NORMAL);

        List<OrderDetail> goodsList = requestModel.getBody();
        for (OrderDetail detail : goodsList) {
            detail.setId(IdGenerator.generateId(IdGenerator.OR_MODULE_CODE));
            detail.setOrderId(orderid);
            if(StringUtils.isEmpty(detail.getReserveTime())){
                detail.setReserveTime(null);
            }
            detail.setTs(new Date());
            detail.setDr(DBConst.DR_NORMAL);
        }

        try {
            orderService.saveOrder(order,goodsList);
        }catch (BusinessException exce){
            log.error(exce.getMessage(), exce);
            result.put("result", "error");
            result.put("message","保存订单出错！");
            return result;
        }

        result.put("result","success");
        result.put("orderid",orderid);
        result.put("message","保存成功!");
        return result;
    }

    /**
     * 根据用户id获取默认地址
     * @param userId  用户id
     */
    @RequestMapping(value = "/userAddress")
    @ResponseBody
    public Map<String,Object> getDefaultAddress(String userId){
        Map<String,Object> result = new HashMap<>();
        try {
            Address address = addressService.selectDefaultByUserId(userId);
            result.put("result","success");
            if(address!=null){
                result.put("address",address.getFullAddress());
            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("message", "查询用户默认地址出错!");
            return result;
        }

        return result;
    }


    /**
     *
     * @param orderId
     * @param ywyId
     * @return
     */
    @RequestMapping(value = "/changeorderstatus",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> changeOrderStatus(Long ts,String orderId,String ywyId){
        Map<String,Object> result = new HashMap<>();
        Order order = orderService.get(orderId);
        if(order.getTs().compareTo(new Date(ts))!=0){
            result.put("result","error");
            result.put("message","该订单已经被修改，请刷新后重新操作！");
            return result;
        }
        Employee e = (Employee)session.getAttribute(CommonConst.SESSION_ATTR_KEY_LOGIN_EMPLOYEE);
        //待指派
        if(DBConst.FLOW_ACTION_DISTRIBUTE==order.getFlowAction()){
            order.setYwyId(ywyId);
            order.setShopManagerId(e.getId());
            //发送个推消息
            sendGetuiMessage(ywyId, orderId);
        }

        try {
            orderService.changeOrderStatus(order, e.getId(),null);
        }catch (Exception ed){
            log.error(ed.getMessage(),ed);
            result.put("result","error");
            result.put("message","更新数据库表出错！");
            return result;
        }
        result.put("result","success");
        result.put("message","更新业务流程成功！");

        return result;
    }

    /**
     * 支付订单
     * @param orderId 不可为空
     * @param payType 不可为空 0微信支付，1现金支付
     * @param couponMny
     * @param payMny
     * @return
     */

    @RequestMapping(value = "/payaction",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> payAction(@RequestParam(value = "orderId") String orderId,@RequestParam(value = "payType") Short payType,
                                        BigDecimal couponMny,BigDecimal payMny,BigDecimal tipMny){
        Map<String,Object> result = new HashMap<>();
        log.debug(orderId);
        try {
            int res = orderService.payOrder(orderId,payType,couponMny,payMny,tipMny);
            if(-1==res){
                result.put("result","error");
                result.put("message","未查询出该订单！");
                return result;
            }else if(-2==res){
                result.put("result","error");
                result.put("message","该订单已支付！");
                return result;
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            result.put("result","error");
            result.put("message","更新订单失败！");
            return result;
        }

        result.put("result","success");
        result.put("message","更新支付信息成功！");
        return result;
    }

    /**
     * 重新分配订单
     * @param ywyid
     */
    @RequestMapping(value = "/redistributeywy",method = RequestMethod.POST)
    @ResponseBody
    public Object redistributeYwy(@RequestParam(value = "orderid")String orderid,@RequestParam(value = "ywyid")String ywyid){
        Map<String,Object> result = new HashMap<>();
        Order  order = orderService.get(orderid);
        Employee e = (Employee)session.getAttribute(CommonConst.SESSION_ATTR_KEY_LOGIN_EMPLOYEE);
        order.setYwyId(ywyid);
        if(e!=null){
            order.setShopManagerId(e.getId());
        }

        //发送个推消息
        sendGetuiMessage(ywyid,orderid);
        try {
            orderService.update(order);
        } catch (BusinessException e1) {
            log.error(e1.getMessage(), e);
            result.put("result", "error");
            result.put("message","重新分配业务员出错!");
            return result;
        }
        result.put("result", "success");
        result.put("message","重新分配业务员成功!");
        return result;
    }

    /**
     * 发送个推消息
     * @param ywyid
     * @param orderid
     */
    private void sendGetuiMessage(String ywyid,String orderid){
        try {
            OrderDetailResponseModel orderDetail = appOperateService.getOrderDetailInfo(orderid);
            Employee employee = employeeService.get(ywyid);
            log.debug("待指派，给业务员发消息：" + orderid+"employeCode:"+employee.getCode());
            GetTuiSendMessUtil util = new GetTuiSendMessUtil();
            util.sendMessToApp(employee.getCode(),orderDetail);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            log.debug("发送个推消息出错ywyid--"+ywyid+"---orderid"+orderid+"--message:"+e.getMessage());
        }


    }

//    @RequestMapping(value = "/test",produces = "text/html; charset=utf-8")
//    @ResponseBody
//    public String test(){
//
//        //0001SM201508121532000001
//        //0001SM201508151549350000
//        List<UserInfoForCoupon> res = null;
//        try {
//            OrderCouponRequestModel requestModel = new OrderCouponRequestModel();
//            requestModel.setMaxMny(new BigDecimal(1000));
//            requestModel.setMinMny(new BigDecimal(1));
//            requestModel.setMinNum(1);
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//            requestModel.setRegisterTime(format.parse("2015-08-10"));
//            res = orderService.getUserListForCoupon(requestModel);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//
//        return JSONObject.toJSONString(res);
//
//    }


    public static void main(String[] args){
        String t = "{deliveryStatus=null, isFinished=0, flowAction=1, ordercode=D15102521282512, ts=1445779914000, orderid=0001OR201510252128250000, busTypeCode=A1, busTypeName=商超, cusaddress=北京 昌平区 黄平路004号, ordertime=2015-10-25 21:28, buyaddress=null, orderState=出店, orderStateName=null, orderamount=486.00, ipaytype=0, cusname=null, custell=null, ordernotes=, distarr=null, status=1, list=[OrderGoodsModel [id=0001OR201510252130100001, dataState=0, orderId=0001OR201510252128250000, imgUrl=null, buyAdress=什么超市, memo=null, name=桌子, num=1.0, goodsMny=123.0, unitId=张, unit=, ordercode=null], OrderGoodsModel [id=0001OR201510252131540003, dataState=0, orderId=0001OR201510252128250000, imgUrl=null, buyAdress=null, memo=null, name=椅子, num=1.0, goodsMny=121.0, unitId=张, unit=, ordercode=null], OrderGoodsModel [id=0001OR201510252131540004, dataState=0, orderId=0001OR201510252128250000, imgUrl=null, buyAdress=null, memo=null, name=椅子, num=1.0, goodsMny=121.0, unitId=张, unit=, ordercode=null], OrderGoodsModel [id=0001OR201510252131540005, dataState=0, orderId=0001OR201510252128250000, imgUrl=null, buyAdress=null, memo=null, name=椅子, num=1.0, goodsMny=121.0, unitId=张, unit=, ordercode=null]]}";
        OrderDetailResponseModel detailResponseModel = JSON.parseObject(t,OrderDetailResponseModel.class);
        System.out.println(detailResponseModel.getBusTypeName());
    }

}
