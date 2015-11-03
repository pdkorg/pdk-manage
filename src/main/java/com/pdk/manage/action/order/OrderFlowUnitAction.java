package com.pdk.manage.action.order;

import com.pdk.manage.dto.flow.FlowTypeTreeNodeJson;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.flow.FlowUnit;
import com.pdk.manage.model.order.CountNumModel;
import com.pdk.manage.model.order.OrderFlowRequestMode;
import com.pdk.manage.model.order.OrderFlowUnitReqModel;
import com.pdk.manage.model.order.ShowOrderPageModel;
import com.pdk.manage.model.sm.Employee;
import com.pdk.manage.model.sm.Role;
import com.pdk.manage.service.flow.FlowUnitService;
import com.pdk.manage.service.order.OrderService;
import com.pdk.manage.service.sm.EmployeeService;
import com.pdk.manage.util.CommonConst;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Arrays;
import java.util.List;

/**
 * Created by 程祥 on 15/9/11.
 * Function：流程单元
 */

@Controller
@RequestMapping(value = "/order/flowUnit")
public class OrderFlowUnitAction {
   private static final Logger logger = LoggerFactory.getLogger("OrderLog");
    @Autowired
    private OrderService orderService;

    @Autowired
    private FlowUnitService flowUnitService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/index")
    public ModelAndView index(){
        return new ModelAndView("order/order_flow_unit");
    }

    /**
     * 获取构造树的数据
     * @param type 0代表主角色；2代表包含辅助角色
     * @return
     */
    @RequestMapping(value = "/treedata/{type}")
    @ResponseBody
    public List<Object> getFlowUnitTreeData(@PathVariable("type") Short type){

        logger.debug("type"+type);
        List<Object> result = new ArrayList<>();
        FlowTypeTreeNodeJson root = new FlowTypeTreeNodeJson("root", "流程环节");
        result.add(root);
        List<String> roleIds = new ArrayList<>();
        Employee employee = (Employee)session.getAttribute(CommonConst.SESSION_ATTR_KEY_LOGIN_EMPLOYEE);


        try {
            if(Employee.ROLE_FIND_TYPE_MAIN==type){
                roleIds.add(employee.getRoleId());
            }else {
                List<Role> roles = employeeService.findEmployeeRoles(employee.getId(), Employee.ROLE_FIND_TYPE_ALL);
                if(roles!=null&&roles.size()>0){
                    for(Role role:roles){
                        roleIds.add(role.getId());
                    }
                }
            }

            List<FlowUnit> flowUnitList =flowUnitService.getFlowUnitsByRoleIds(roleIds);
            if(flowUnitList==null || flowUnitList.size()==0){
                return result;
            }


            for(FlowUnit unit : flowUnitList){
                FlowTypeTreeNodeJson child = new FlowTypeTreeNodeJson(unit.getId(),unit.getCode()+"    "+unit.getName());
                root.addChild(child);
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return result;
        }
        return result;
    }


    /**
     *
     * @param flowUnitId
     * @param pageNum
     * @param type 是否包含辅助角色
     * @return
     */
    @RequestMapping(value = "/queryOrderData",method = RequestMethod.POST)
    @ResponseBody
    public ShowOrderPageModel getOrderDataByFlowUnitId(String flowUnitId,int pageNum,Short type){
        ShowOrderPageModel pageModel = new ShowOrderPageModel();
        pageModel.setPages(0);
        pageModel.setPageNum(0);
        if(StringUtils.isEmpty(flowUnitId)){
            return pageModel;
        }
        try {
            Employee employee = (Employee)session.getAttribute(CommonConst.SESSION_ATTR_KEY_LOGIN_EMPLOYEE);
            String roleId = employee.getRoleId();
            List<String> roleIds = new ArrayList<>();
            if(Employee.ROLE_FIND_TYPE_MAIN==type){
                roleIds.add(roleId);
            }else {
                List<Role> roles = employeeService.findEmployeeRoles(employee.getId(), Employee.ROLE_FIND_TYPE_ALL);
                if(roles!=null&&roles.size()>0){
                    for(Role role:roles){
                        roleIds.add(role.getId());
                    }
                }
            }
            OrderFlowRequestMode requestMode = new OrderFlowRequestMode();
            requestMode.setFlowUnitId(flowUnitId);
            requestMode.setRoleIds(roleIds);
            pageModel = orderService.getOrderDataByFlowArgs(requestMode, pageNum);
            return pageModel;
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @RequestMapping(value = "/getOrderNums",method = RequestMethod.POST)
    @ResponseBody
    public Object getOrderNums(String flowUnitArr,Short type){
        logger.debug("flowUnitArr:" + flowUnitArr);
        List<CountNumModel> list = new ArrayList<>();
        if(StringUtils.isEmpty(flowUnitArr)){
            return list;
        }
        System.out.println(flowUnitArr);
        String[] idArr = flowUnitArr.split(",");
        Employee employee = (Employee)session.getAttribute(CommonConst.SESSION_ATTR_KEY_LOGIN_EMPLOYEE);
        String roleId = employee.getRoleId();
        List<String> roleIds = new ArrayList<>();
        if(Employee.ROLE_FIND_TYPE_MAIN==type){
            roleIds.add(roleId);
        }else {
            List<Role> roles = employeeService.findEmployeeRoles(employee.getId(), Employee.ROLE_FIND_TYPE_ALL);
            if(roles!=null&&roles.size()>0){
                for(Role role:roles){
                    roleIds.add(role.getId());
                }
            }
        }
        try {
            OrderFlowUnitReqModel reqModel = new OrderFlowUnitReqModel();
            reqModel.setList(Arrays.asList(idArr));
            reqModel.setRoleList(roleIds);
            list = orderService.queryOrderNumByFlowUnitIds(reqModel);
        } catch (BusinessException e) {
            logger.error(e.getMessage(), e);
            logger.debug("根据流程单元id查询订单数目出错~");
            return new ArrayList<>();
        }
        return list;
    }

}
