package com.pdk.manage.action.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.app.AppStaticVariable;
import com.pdk.manage.model.app.CheckInfoModel;
import com.pdk.manage.model.app.request.OrderDetailReqModel;
import com.pdk.manage.model.app.request.OrderListRequestModel;
import com.pdk.manage.model.app.request.ReportRequestModel;
import com.pdk.manage.model.app.response.*;
import com.pdk.manage.model.order.Order;
import com.pdk.manage.model.order.OrderDetail;
import com.pdk.manage.model.sm.Employee;
import com.pdk.manage.service.app.AppOperateServiceImpl;
import com.pdk.manage.service.order.OrderService;
import com.pdk.manage.service.sm.EmployeeService;
import com.pdk.manage.util.CommonUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by 程祥 on 15/9/25.
 * Function： app服务
 */
@Controller
@RequestMapping(value = "/app",produces = "text/html; charset=utf-8")
public class AppServiceController {
    private static final Logger logger = LoggerFactory.getLogger(AppServiceController.class);

    //用于保存全局正在工作的小跑君，key为用户id，value为用户工作状态
    public static Hashtable<String,CheckInfoModel> inworkers = new Hashtable<String,CheckInfoModel>();

    @Autowired
    private AppOperateServiceImpl appOperateService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private OrderService orderService;

    /**
     * 登陆功能服务，成功后将用户信息添加到session中
     * @param usercode 用户账号
     * @param checkid 用户校验码
     * @param userpw 用户密码
     * @param clientid 用户个推账号
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public String login(HttpSession session,String usercode,String checkid,String userpw,String clientid){
        LoginResponseModel responseModel = new LoginResponseModel();
        String[] argnames = {"usercode","checkid","userpw"};
        String paracheck = checkParaNotNull(argnames, usercode, checkid, userpw);
        if(StringUtils.isNotEmpty(paracheck)){
            return paracheck;
        }
        logger.debug("登陆的sessionid：" + session.getId() + "checkid:" + checkid);

        Employee user = employeeService.getEmployee(usercode);

        if(null==user|| (!DigestUtils.md5Hex(userpw).equals(user.getPassword()))){
            responseModel.setRetcode(AppStaticVariable.LOGIN_FAILED_CODE);
            responseModel.setRetdesc(AppStaticVariable.LOGIN_FAILED_MESG);
            return JSONObject.toJSONString(responseModel);
        }
        responseModel.setRetcode(AppStaticVariable.SUCCEED_CODE);
        responseModel.setRetdesc(AppStaticVariable.SUCCEED_MESG);

        String baseUrl = CommonUtil.getResourcePath();
        String imgUrl = baseUrl+"/static/wx/images/fw.png";
        if(StringUtils.isNotEmpty(user.getHeaderImg())){
            imgUrl = baseUrl+"/"+user.getHeaderImg();
        }
        responseModel.setImgurl(imgUrl);
        //TODO 今日已完成订单数量
        try {
            responseModel.setIfinorder(appOperateService.getFinishedNum(user.getId()) + "");
        } catch (BusinessException e) {
            logger.error(e.getMessage(),e);
        }

//        session.setAttribute(AppStaticVariable.LOGIN_SESSION_PRE + userid, checkid);
        CheckInfoModel checkInfoModel = new CheckInfoModel();
        checkInfoModel.setUserid(user.getId());
        checkInfoModel.setCheckid(checkid);
        checkInfoModel.setClientid(clientid);
        checkInfoModel.setUserCode(usercode);
        inworkers.put(usercode, checkInfoModel);
        session.setAttribute(AppStaticVariable.LOGIN_SESSION_PRE + usercode, checkInfoModel);

        return JSONObject.toJSONString(responseModel);
    }


    /**
     * 退出功能，若校验码正确，则从session中移除用户信息，并返回退出成功信息；
     * 若校验码不正确，则不从session中移除用户信息，但仍返回退出成功信息；
     * 此操作保证用户最后一次登陆可以进行对应操作，若不是校验码和最后一次操作不匹配，则需要提示重新登陆；
     * @param session
     * @param usercode
     * @param checkid
     * @return
     */
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    @ResponseBody
    public String logout(HttpSession session,String usercode,String checkid){
        BaseResponseModel responseModel = new BaseResponseModel();
        responseModel.setRetcode(AppStaticVariable.SUCCEED_CODE);
        responseModel.setRetdesc(AppStaticVariable.SUCCEED_MESG);
        CheckInfoModel userSession = (CheckInfoModel)session.getAttribute(AppStaticVariable.LOGIN_SESSION_PRE+usercode);
        if(userSession==null){
            responseModel.setRetdesc("session中不存在此用户登陆信息，或者已过期！");
            return JSONObject.toJSONString(responseModel);
        }
        if(!checkid.equals(userSession.getCheckid())){
            responseModel.setRetdesc("checkid与session中不一致,此时并未移除该用户的最后登陆信息~");
            return JSONObject.toJSONString(responseModel);
        }

        //验证一致，将用户登陆信息移除
        session.removeAttribute(AppStaticVariable.LOGIN_SESSION_PRE + usercode);
        session.invalidate();
        inworkers.remove(usercode);
        return JSONObject.toJSONString(responseModel);
    }

    /**
     * 修改密码
     * @param session
     * @param usercode
     * @param checkid
     * @param oldpw
     * @param newpw
     * @return
     */
    @RequestMapping(value = "/changepw",method = RequestMethod.POST)
    @ResponseBody
    public String changePasswd(HttpSession session,String usercode,String checkid,String oldpw,String newpw){
        BaseResponseModel responseModel = new BaseResponseModel();
        String[] argnames = {"usercode","checkid","oldpw","newpw"};
        String paracheck = checkParaNotNull(argnames, usercode, checkid, oldpw, newpw);
        if(StringUtils.isNotEmpty(paracheck)){
            return paracheck;
        }

        String checkEff = checkUserEffectice(session, usercode, checkid);
        if(StringUtils.isNotEmpty(checkEff)){
            return checkEff;
        }

        Employee user = employeeService.getEmployee(usercode);

        if(null==user|| !DigestUtils.md5Hex(oldpw).equals(user.getPassword())){
            responseModel.setRetcode(AppStaticVariable.OLDPASSERROR_CODE);
            responseModel.setRetdesc(AppStaticVariable.OLDPASSERROR_MESG);
            return JSONObject.toJSONString(responseModel);
        }
        //修改密码 需要重新登录吗？
        try {
            employeeService.modifyPassword(user.getId(), DigestUtils.md5Hex(newpw));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            responseModel.setRetcode(AppStaticVariable.UPDATEPWERROR_CODE);
            responseModel.setRetdesc("修改用户密码出错！");
            return JSONObject.toJSONString(responseModel);
        }

        responseModel.setRetcode(AppStaticVariable.SUCCEED_CODE);
        responseModel.setRetdesc(AppStaticVariable.SUCCEED_MESG);
        return JSONObject.toJSONString(responseModel);
    }


    /**
     * 提交工作状态
     * @param session
     * @param usercode
     * @param checkid
     * @param workstate
     * @param clientid
     * @param latitude
     * @param longitude
     * @return
     */
    @RequestMapping(value = "/workstate",method = RequestMethod.POST)
    @ResponseBody
    public String changeWorkState(HttpSession session,
                                  String usercode,String checkid,
                                  String workstate,String clientid,
                                  String latitude,String longitude){

        logger.debug("切换工作状态sessionid:----->" + session.getId() + "****checkid----->" + checkid+"**clientid"+clientid);
        logger.debug("sessionid:----->" + session.getId() + "checkid----->" + checkid);
        String[] argnames = {"usercode","checkid","workstate","clientid"};
        String paracheck = checkParaNotNull(argnames, usercode, checkid, workstate, clientid);
        if(StringUtils.isNotEmpty(paracheck)){
            return paracheck;
        }

        String checkEff = checkUserEffectice(session, usercode, checkid);
        if(StringUtils.isNotEmpty(checkEff)){
            return checkEff;
        }
        //上面校验过用户的有效性，所以userinfo不可能为空
        CheckInfoModel userInfo = getSessionUserInfo(session, usercode);
        if("0".equals(workstate)) {
            inworkers.remove(usercode);
        }else if("1".equals(workstate)){
            CheckInfoModel checkInfoModel = new CheckInfoModel();
            checkInfoModel.setCheckid(checkid);
            checkInfoModel.setUserid(userInfo.getUserid());
            checkInfoModel.setUserCode(usercode);
            checkInfoModel.setClientid(clientid);
            checkInfoModel.setWorkState(Integer.parseInt(workstate));
            checkInfoModel.setLatitude(latitude);
            checkInfoModel.setLongitude(longitude);
            inworkers.put(usercode, checkInfoModel);
        }
        //TODO 记录日志
        if(inworkers.keySet()!=null){
            logger.debug("正在工作的小跑君ID：" + Arrays.toString(inworkers.keySet().toArray(new String[0])));
            logger.debug("当前工作小跑君的clientId---"+clientid);
        }

        ChgStaResponseModel responseModel = new ChgStaResponseModel();
        responseModel.setRetcode(AppStaticVariable.SUCCEED_CODE);
        responseModel.setRetdesc(AppStaticVariable.SUCCEED_MESG);
        try {
            //TODO 设置完成订单数量
            responseModel.setIfinorder(appOperateService.getFinishedNum(userInfo.getUserid()));
        } catch (BusinessException e) {
            logger.error(e.getMessage(),e);
            logger.debug("设置完成订单数量出错！" + e.getMessage());
        }
        return JSONObject.toJSONString(responseModel);
    }

    /**
     * 订单列表
     * @param session
     * @param usercode
     * @param checkid
     * @param orderStatus
     * @param pagesize
     * @param currentpage 从0开始
     * @return
     */
    @RequestMapping(value = "/orderlistinfo",method = RequestMethod.POST)
    @ResponseBody
    public String getOrderinfoList(HttpSession session,String usercode,
                                   String checkid,String orderStatus,
                                   String pagesize,String currentpage){
        String[] argnames = {"usercode","checkid","orderStatus","pagesize","currentpage"};
        String paracheck = checkParaNotNull(argnames, usercode,
                checkid, orderStatus, pagesize, currentpage);
        if(StringUtils.isNotEmpty(paracheck)){
            return paracheck;
        }

        String checkEff = checkUserEffectice(session, usercode, checkid);
        if(StringUtils.isNotEmpty(checkEff)){
            return checkEff;
        }
        CheckInfoModel userInfo = getSessionUserInfo(session, usercode);
        OrderListRequestModel requestModel = new OrderListRequestModel();
        requestModel.setUserid(userInfo.getUserid());
        //新增订单和已完成订单：取消或者配送完成均为已完成订单，未配送完成并且未取消
        if(orderStatus.equals("0")){
            requestModel.setAndor(0);
        }else if("1".equals(orderStatus)){
            requestModel.setAndor(1);
        }
        if(StringUtils.isNumeric(pagesize)&&StringUtils.isNumeric(currentpage)){
            requestModel.setCurrentpage(
                    Integer.parseInt(pagesize)*Integer.parseInt(currentpage));
            requestModel.setPagesize(Integer.parseInt(pagesize));
        }else{
            //非数字处理异常
            BaseResponseModel responseModel = new BaseResponseModel();
            responseModel.setRetcode(AppStaticVariable.PARAMERROR_CODE);
            responseModel.setRetdesc("pagesize和currentpage参数必须为数字！");
            return JSONObject.toJSONString(responseModel);
        }
        OrderListResponseModel resultObj = new OrderListResponseModel();
        List<OrderInfoResponseModel> resultList = null;
        try {
            resultList = appOperateService.getorderInfoList(requestModel);
        } catch (BusinessException e) {
            logger.error(e.getMessage(),e);
            logger.debug("查询订单列表出错~" + e.getMessage());
        }
        resultObj.setRetcode(AppStaticVariable.SUCCEED_CODE);
        resultObj.setRetdesc(AppStaticVariable.SUCCEED_MESG);
        resultObj.setList(resultList);

        return JSONObject.toJSONString(resultObj);
    }


    /**
     * 订单详情
     * @param session
     * @param usercode
     * @param checkid
     * @param orderid
     * @return
     */
    @RequestMapping(value = "/orderdetail",method = RequestMethod.POST)
    @ResponseBody
    public String getOrderDetail(HttpSession session,String usercode,
                                 String checkid,String orderid){
        logger.debug("app debug log orderdetail: orderid--"+orderid+"--usercode:"+usercode);

        String[] argnames = {"usercode","checkid","orderid"};
        String paracheck = checkParaNotNull(argnames, usercode,
                checkid, orderid);
        if(StringUtils.isNotEmpty(paracheck)){
            return paracheck;
        }

        String checkEff = checkUserEffectice(session, usercode, checkid);
        if(StringUtils.isNotEmpty(checkEff)){
            return checkEff;
        }

        OrderDetailResponseModel result = null;
        try {
            result = appOperateService.getOrderDetailInfo(orderid);
        } catch (BusinessException e) {
            logger.error(e.getMessage(), e);
            logger.debug("查询订单详情失败:" + orderid + "error message-->" + e.getMessage());
            result.setRetcode(AppStaticVariable.ORDERDETAILERROR_CODE);
            result.setRetdesc(AppStaticVariable.ORDERDETAILERROR_MESG);
            return JSONObject.toJSONString(result);
        }
        //TODO 待完成 需求未定，订单流程状态数组未加  是按照之前的模式还是只显示当前订单处于哪个流程，待确认

        result.setRetcode(AppStaticVariable.SUCCEED_CODE);
        result.setRetdesc(AppStaticVariable.SUCCEED_MESG);
        return JSONObject.toJSONString(result);

    }

    /**
     * 更新订单流程状态
     * @param session
     * @param usercode
     * @param checkid
     * @param ts
     * @param orderid
     * @return
     */
    @RequestMapping(value = "/busistate",method = RequestMethod.POST)
    @ResponseBody
    public String updateBusiState(HttpSession session,String usercode,
                                  String checkid,Long ts,String orderid){
        String[] argnames = {"usercode","checkid","ts","orderid"};
        String paracheck = checkParaNotNull(argnames, usercode,
                checkid, ts + "", orderid);
        if(StringUtils.isNotEmpty(paracheck)){
            return paracheck;
        }

        String checkEff = checkUserEffectice(session, usercode, checkid);
        if(StringUtils.isNotEmpty(checkEff)){
            return checkEff;
        }
        UpdateDeliveryStatusModel responseModel = new UpdateDeliveryStatusModel();

        Order order = orderService.get(orderid);
        logger.debug("执行完更新流程状态之前的ts"+order.getTs().getTime()+"--id:"+orderid);
        if(order.getTs().compareTo(new Date(ts))!=0){
            logger.debug("app updateBusiState-- 更新订单流程失败原因：该订单已经被修改过，请刷新后重新操作!");
            responseModel.setRetcode(AppStaticVariable.TS_ERROR);
            responseModel.setRetdesc("该订单已经被修改过，请刷新后重新操作!");
            return JSON.toJSONString(responseModel);
        }
        CheckInfoModel userInfo = getSessionUserInfo(session,usercode);
        try {
            orderService.changeOrderStatus(order,userInfo.getUserid(),null);
        }catch (Exception ed){
            logger.error(ed.getMessage(), ed);
            logger.debug("app updateBusiState-- 更新订单流程失败原因：异常" + ed.getMessage());
            responseModel.setRetcode(AppStaticVariable.BUSISTATERROR_CODE);
            responseModel.setRetdesc(AppStaticVariable.BUSISTATERROR_MESG);
            return JSON.toJSONString(responseModel);
        }
        Order res_order = orderService.get(orderid);
        logger.debug("执行完更新流程状态之后的ts"+res_order.getTs().getTime() + "");
        responseModel.setDeliveryStatus(res_order.getDeliveryStatus());
        responseModel.setIsFinished(res_order.getIsFlowFinish());
        responseModel.setTs(res_order.getTs().getTime());
        responseModel.setRetcode(AppStaticVariable.SUCCEED_CODE);
        responseModel.setRetdesc(AppStaticVariable.SUCCEED_MESG);
        return JSON.toJSONString(responseModel);
    }


    /**
     * 定时更新派送员位置信息
     * @param session
     * @param usercode
     * @param checkid
     * @param latitude
     * @param longitude
     * @return
     */
    @RequestMapping(value = "/updatelocat",method = RequestMethod.POST)
    @ResponseBody
    public String updateLocation(HttpSession session,String usercode,
                                 String checkid,String latitude,String longitude) {
        String[] argnames = {"usercode", "checkid", "latitude", "longitude"};
        String paracheck = checkParaNotNull(argnames, usercode,
                checkid, latitude, longitude);
        if (StringUtils.isNotEmpty(paracheck)) {
            return paracheck;
        }
        logger.debug("更新位置的sessionid：" + session.getId() + "checkid:" + checkid);
        String checkEff = checkUserEffectice(session, usercode, checkid);
        if (StringUtils.isNotEmpty(checkEff)) {
            return checkEff;
        }
        CheckInfoModel model = inworkers.get(usercode);
        BaseResponseModel responseModel = new BaseResponseModel();
        if(model==null){
            logger.debug("缓存中不存在该用户的信息:"+usercode);
            responseModel.setRetcode(AppStaticVariable.UPDATELOCERROR_CODE);
            responseModel.setRetdesc(AppStaticVariable.UPDATELOCERROR_MESG);
            return JSONObject.toJSONString(responseModel);
        }
        model.setLatitude(latitude);
        model.setLongitude(longitude);
        inworkers.put(usercode, model);

        responseModel.setRetcode(AppStaticVariable.SUCCEED_CODE);
        responseModel.setRetdesc(AppStaticVariable.SUCCEED_MESG);
        return JSON.toJSONString(responseModel);
    }

    /**
     * 修改完善订单商品信息
     * @param session
     * @param usercode
     * @param checkid
     * @param orderdetail 必须包含订单id
     * @return
     */
    @RequestMapping(value = "/addordergoods",method = RequestMethod.POST)
    @ResponseBody
    public String addOrderGoods(HttpSession session,String usercode,
                                String checkid,String orderid,Long ts,String orderdetail) {
        OrderDetailResponseModel responseModel = new OrderDetailResponseModel();

        logger.debug("app addOrderGoods orderdetail--->"+orderdetail);
        System.out.println("*************" + orderdetail);
        String[] argnames = {"usercode","checkid","orderid","ts", "orderdetail"};
        String paracheck = checkParaNotNull(argnames, usercode, checkid, orderid, ts + "", orderdetail);
        if (StringUtils.isNotEmpty(paracheck)) {
            return paracheck;
        }

        String checkEff = checkUserEffectice(session, usercode, checkid);
        if (StringUtils.isNotEmpty(checkEff)) {
            return checkEff;
        }
        OrderDetailReqModel requestJson = JSONObject.parseObject(orderdetail, OrderDetailReqModel.class);
        logger.debug("app addOrderGoods orderid---->:"+requestJson.getOrderid()+"---orderts--->:"+requestJson.getTs());
        List<OrderDetail> bodyList = requestJson.getList();
        Order order = orderService.get(orderid);
        if(order.getTs().compareTo(new Date(ts))!=0){
            responseModel.setRetcode(AppStaticVariable.TS_ERROR);
            responseModel.setRetdesc("该订单已被修改过，请重新刷新后操作!");
            return JSONObject.toJSONString(responseModel);
        }
        if(bodyList==null){
            bodyList = new ArrayList<>();
        }
        BigDecimal countMny = new BigDecimal(0.00);
        for(OrderDetail detail:bodyList){
            BigDecimal temp = detail.getGoodsMny();
            countMny = countMny.add(temp);
        }
        countMny = countMny.setScale(2, BigDecimal.ROUND_CEILING);
        order.setMny(countMny);

        try {
            orderService.editSave(order,bodyList);
            responseModel = appOperateService.getOrderDetailInfo(orderid);
        } catch (BusinessException e) {
            logger.error(e.getMessage(), e);
            logger.debug("修改订单失败："+e.getMessage());
            responseModel.setRetdesc(AppStaticVariable.NEWGOODSERROR_MESG);
            responseModel.setRetcode(AppStaticVariable.NEWGOODSERROR_CODE);
            return JSON.toJSONString(responseModel);
        }

        responseModel.setRetdesc(AppStaticVariable.SUCCEED_MESG);
        responseModel.setRetcode(AppStaticVariable.SUCCEED_CODE);
        return JSON.toJSONString(responseModel);
    }

    /**
     * 业务员报表查询
     * @param session
     * @param usercode
     * @param checkid
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "/reportanys" ,method = RequestMethod.POST)
    @ResponseBody
    public String reportsByDates(HttpSession session,String usercode,String checkid,String startDate,String endDate){

        String[] argnames = {"usercode","checkid","startDate","endDate"};
        String paracheck = checkParaNotNull(argnames, usercode,
                checkid, startDate, endDate);
        if(StringUtils.isNotEmpty(paracheck)){
            return paracheck;
        }

        logger.debug("获取报表请求日期：start"+startDate+",end"+endDate);

        String checkEff = checkUserEffectice(session, usercode, checkid);
        if(StringUtils.isNotEmpty(checkEff)){
            return checkEff;
        }
        CheckInfoModel infoModel = getSessionUserInfo(session,usercode);
        ReportRequestModel requestModel = new ReportRequestModel();
        requestModel.setUserid(infoModel.getUserid());
        requestModel.setStartDate(startDate);
        requestModel.setEndDate(endDate);
        ReportResponseModel responseModel = null;
        try {
            responseModel = appOperateService.getSumDatas(requestModel);
        } catch (BusinessException e) {
            e.printStackTrace();
            logger.debug("获取报表信息失败"+e.getMessage());
        }
        if(responseModel==null){
            BaseResponseModel baseResponseModel = new BaseResponseModel();
            baseResponseModel.setRetcode(AppStaticVariable.ORDERANALYERROR_CODE);
            baseResponseModel.setRetdesc(AppStaticVariable.ORDERANALYERROR_MESG);
            return JSONObject.toJSONString(baseResponseModel);
        }
        responseModel.setRetcode(AppStaticVariable.SUCCEED_CODE);
        responseModel.setRetdesc(AppStaticVariable.SUCCEED_MESG);
        return JSONObject.toJSONString(responseModel);
    }

    /**
     * 修改订单支付类型
     * @param session
     * @param usercode
     * @param checkid
     * @param orderid
     * @param paytype
     * @return
     */
    @RequestMapping(value = "/updatepaytype",method = RequestMethod.POST)
    @ResponseBody
    public String updatePayType(HttpSession session,String usercode,String checkid,String orderid,String paytype ) {

        String[] argnames = {"usercode", "checkid", "orderid", "paytype"};
        String paracheck = checkParaNotNull(argnames, usercode,
                checkid, orderid, paytype);
        if (StringUtils.isNotEmpty(paracheck)) {
            return paracheck;
        }

        String checkEff = checkUserEffectice(session, usercode, checkid);
        if (StringUtils.isNotEmpty(checkEff)) {
            return checkEff;
        }

        BaseResponseModel baseResponseModel = new BaseResponseModel();
        try {
            appOperateService.updatePayType(orderid,Short.parseShort(paytype));
        } catch (BusinessException e) {
            logger.error(e.getMessage(),e);
            logger.debug("修改订单支付类型失败："+e.getMessage());
            baseResponseModel.setRetcode(AppStaticVariable.UPDATEPAYTYPEERROR_CODE);
            baseResponseModel.setRetdesc(AppStaticVariable.UPDATEPAYTYPEERROR_MESG);
            return JSON.toJSONString(baseResponseModel);
        }
        baseResponseModel.setRetcode(AppStaticVariable.SUCCEED_CODE);
        baseResponseModel.setRetdesc(AppStaticVariable.SUCCEED_MESG);

        return JSON.toJSONString(baseResponseModel);
    }


    private String checkParaNotNull(String[] argnames,String... args){
        BaseResponseModel responseModel = new BaseResponseModel();
        StringBuilder paramsb = new StringBuilder();
        int i=0;
        for(String arg : args){
            if(arg==null || arg.length()==0){
                paramsb.append(argnames[i]).append(",");
            }
            i++;
        }
        if(paramsb.length()!=0){
            responseModel.setRetcode(AppStaticVariable.PARAMERROR_CODE);
            responseModel.setRetdesc(paramsb.substring(0, paramsb.length() - 1) + "参数不可为空！");
            return JSONObject.toJSONString(responseModel);
        }
        return "";
    }


    private CheckInfoModel getSessionUserInfo(HttpSession session,String userCode){
        CheckInfoModel sessionUserInfo = (CheckInfoModel)session.getAttribute(AppStaticVariable.LOGIN_SESSION_PRE + userCode);
        return sessionUserInfo;
    }

    /**
     * 校验用户请求的有效性
     */
    private String checkUserEffectice(HttpSession session,String userCode,String checkid){
        BaseResponseModel responseModel = new BaseResponseModel();
        responseModel.setRetcode(AppStaticVariable.CHECKEXCEPTION_CODE);
        responseModel.setRetdesc(AppStaticVariable.CHECKEXCEPTION_MESG);
        //TODO 记录日志
//        logger.debug("每次请求的checkid:" + checkid + "***************");
//        logger.debug("每次请求的sessionid:" + session.getId());
        CheckInfoModel infoModel =(CheckInfoModel)session.getAttribute(AppStaticVariable.LOGIN_SESSION_PRE + userCode);
        if(infoModel==null||!(checkid.equals(infoModel.getCheckid()))){
            return JSONObject.toJSONString(responseModel);
        }
        //更新登陆用户session
        session.setAttribute(
                AppStaticVariable.LOGIN_SESSION_PRE + userCode, infoModel);
        return "";
    }

}
