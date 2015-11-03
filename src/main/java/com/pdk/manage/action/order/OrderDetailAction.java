package com.pdk.manage.action.order;

import com.github.pagehelper.PageInfo;
import com.pdk.manage.common.wapper.order.OrderGoodsDataTableArgWapper;
import com.pdk.manage.dto.order.OrderGoodsJson;
import com.pdk.manage.model.bd.Unit;
import com.pdk.manage.model.flow.FlowTemplateInstanceUnit;
import com.pdk.manage.model.flow.FlowUnit;
import com.pdk.manage.model.order.OrderDetail;
import com.pdk.manage.model.order.OrderTable;
import com.pdk.manage.model.sm.Employee;
import com.pdk.manage.service.bd.UnitService;
import com.pdk.manage.service.flow.FlowTemplateInstanceUnitService;
import com.pdk.manage.service.order.OrderDetailService;
import com.pdk.manage.service.order.OrderService;
import com.pdk.manage.util.CommonConst;
import com.pdk.manage.util.DBConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 程祥 on 15/8/17.
 * Function：订单相关请求
 */
@Controller
@RequestMapping(value = "/order")
public class OrderDetailAction {

    private static final Logger log = LoggerFactory.getLogger("OrderLog");

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private FlowTemplateInstanceUnitService flowTemplateInstanceUnitService;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/addorder")
    public ModelAndView addorder(){

        ModelAndView mv = new ModelAndView("order/addorder");
        Employee e = (Employee)session.getAttribute(CommonConst.SESSION_ATTR_KEY_LOGIN_EMPLOYEE);
        mv.addObject("waiter",e.getName());

        return mv;

    }

    /**
     * 订单详情页面
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/orderdetail/{orderId}")
    public ModelAndView orderdetail( @PathVariable("orderId") String orderId){
        ModelAndView mv = new ModelAndView("order/orderdetail");
        List<OrderTable> list = null;
        List<OrderDetail> detaillist = null;
        try {
            list = orderService.getOrderDataByOrderId(orderId);
            detaillist = orderDetailService.getGoodsByOrderId(orderId);

        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        OrderTable data = new OrderTable();
        if(list!=null&&list.size()>0){
            data = list.get(0);
        }

        //判断当前登录者是否有权限修改订单:管理员或者当前订单第一个流程还未开始
        List<FlowTemplateInstanceUnit> instanceUnitList = flowTemplateInstanceUnitService.getFlowTemplateInstanceUnitList(data.getFlowInstanceId());
        Employee loginer = (Employee)session.getAttribute(CommonConst.SESSION_ATTR_KEY_LOGIN_EMPLOYEE);

        boolean isFirstFlowEnd = false;
        if(instanceUnitList!=null&&instanceUnitList.size()>0&&instanceUnitList.get(0)!=null){
            FlowTemplateInstanceUnit unit = instanceUnitList.get(0);
            if(unit.getIsFinish()==null||unit.getIsFinish().equals("N")){
                isFirstFlowEnd = true;
            }
        }
        if((!DBConst.POSITION_WAITER.equals(loginer.getPositionId()))||isFirstFlowEnd){
            mv.addObject("isShowBtn",1);
        }else {
            mv.addObject("isShowBtn",0);
        }
        if(DBConst.POSITION_WAITER.equals(loginer.getPositionId())){
            //客服不可以看到业务流程按钮
            mv.addObject("isManager",0);
        }else {
            mv.addObject("isManager",1);
        }

        mv.addObject("obj",data);
        mv.addObject("body",detaillist);
        return mv;
    }

    @RequestMapping(value = "/orderheadmny")
    @ResponseBody
    public Map<String,Object> getHeadMnyData(String orderId){
        Map<String,Object> result = new HashMap<>();

        List<OrderTable> list = null;
        try {
            list = orderService.getOrderDataByOrderId(orderId);

        }catch (Exception e){
            log.error(e.getMessage(), e);
            result.put("message","查询订单金额出错！");
            result.put("result","error");
            return result;
        }
        OrderTable data = new OrderTable();
        if(list!=null||list.size()==0){
            data = list.get(0);
        }
        result.put("obj", data);
        result.put("result", "success");

        return result;
    }

    /**
     * 订单详情页面
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/editorder/{orderId}")
    public ModelAndView editorderdetail( @PathVariable("orderId") String orderId){
        ModelAndView mv = new ModelAndView("order/editorder");
        List<OrderTable> list = null;
        List<OrderDetail> detaillist = null;
        try {
            list = orderService.getOrderDataByOrderId(orderId);
            detaillist = orderDetailService.getGoodsByOrderId(orderId);

        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        OrderTable data = new OrderTable();
        if(list!=null||list.size()==0){
            data = list.get(0);
        }
        //添加 单位的select2  笨方法。。。
        if (detaillist!=null&&detaillist.size()>0){
            String option = generateUnitListHtml();
            for (OrderDetail detail:detaillist){
                String unitHtml = "<select class='form-control select2me' name='unit' data-unit='"+detail.getUnitId()+"'>";
                unitHtml += "<option value=''>请选择</option>";
                unitHtml += option;
                unitHtml += "</select>";
                detail.setUnitListHtml(unitHtml);
            }
        }
        mv.addObject("obj",data);
        mv.addObject("body",detaillist);
        return mv;
    }

    private String generateUnitListHtml(){
        StringBuilder result = new StringBuilder();

        List<Unit> data = new ArrayList<>();
        try {
            data = unitService.selectAll();
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        if(data==null||data.size()==0){
            return "";
        }
        for (Unit unit:data){
            result.append("<option value='"+unit.getName()+"'> ");
            result.append(unit.getName());
            result.append("</option>");
        }
        return result.toString();

    }

    /**
     * 获取订单商品信息
     */
    @RequestMapping(value = "/goodsdetail/{orderid}")
    @ResponseBody
    public Map<String, Object> getOrderGoodsData(
            OrderGoodsDataTableArgWapper arg, @PathVariable("orderid") String orderid){

        Map<String,Object> result = new HashMap<>();
        PageInfo<OrderDetail> pageInfo = null;
        OrderDetail query = new OrderDetail();
        query.setOrderId(orderid);
        query.setNum(null);
        query.setOthMny(null);
        query.setServiceMny(null);
        query.setPrice(null);
        query.setGoodsMny(null);
        query.setDr(DBConst.DR_NORMAL);
        try {
            pageInfo = orderDetailService.selectPage(arg.getPageNum(),
                    arg.getLength(),arg.getOrderStr(),query);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

        int index = 1;
        List<OrderGoodsJson> data = new ArrayList<>();
        if(pageInfo!=null){
            for(OrderDetail goods : pageInfo.getList()){
                data.add(new OrderGoodsJson(index,goods.getId(),goods.getName(),goods.getNum(),goods.getUnitId(),goods.getBuyAdress(),
                        goods.getGoodsMny(),goods.getServiceMny(),goods.getOthMny(),goods.getMemo()));
                index++;
            }
        }

        result.put("draw", arg.getDraw());
        result.put("recordsTotal", pageInfo.getTotal());
        result.put("recordsFiltered", pageInfo.getTotal());
        result.put("data", data);

        return result;
    }

    /**
     * 通过订单id获取订单商品列表
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/getGoodsByOrderId")
    @ResponseBody
    public Map<String,Object> getAllGoodsData(String orderId){
        Map<String,Object> result = new HashMap<>();
        try {
            List<OrderDetail> list = orderDetailService.getGoodsByOrderId(orderId);
            result.put("list",list);
            result.put("result","success");
        }catch (Exception e){
            log.error(e.getMessage(),e);
            result.put("result","error");
            result.put("message","查询出错！");
        }
        return result;

    }




}
