package com.pdk.manage.action.order;

import com.pdk.manage.dto.flow.FlowTypeTreeNodeJson;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.flow.FlowTemplateUnit;
import com.pdk.manage.model.flow.FlowType;
import com.pdk.manage.model.flow.FlowUnit;
import com.pdk.manage.model.order.CountNumModel;
import com.pdk.manage.model.order.OrderFlowRequestMode;
import com.pdk.manage.model.order.ShowOrderPageModel;
import com.pdk.manage.model.sm.Employee;
import com.pdk.manage.model.sm.Role;
import com.pdk.manage.service.flow.FlowTemplateUnitService;
import com.pdk.manage.service.flow.FlowTypeService;
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
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by 程祥 on 15/9/8.
 * Function：订单跟踪，依据业务类型进行查询~
 * 所有服务以/order/flowtype*   开头
 */
@Controller
@RequestMapping(value = "/order/flowType")
public class OrderFlowTypeAction {

    private static final Logger logger = LoggerFactory.getLogger("OrderLog");

    @Autowired
    private OrderService orderService;

    @Autowired
    private FlowTemplateUnitService flowTemplateUnitService;

    @Autowired
    private FlowTypeService flowTypeService;

    @Autowired
    private FlowUnitService flowUnitService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/index")
    public ModelAndView orderFlowTypeIndex(){
        return new ModelAndView("order/order_flow_type");
    }


    /**
     * 根据角色获取树节点
     * @param type 0代表主角色，2代表所有角色（包含辅助角色）
     * @return
     */
    @RequestMapping(value = "/treedata/{type}")
    @ResponseBody
    public List<Object> getFlowTypeTreeData(@PathVariable Short type){
        List<Object> result = new ArrayList<>();
        FlowTypeTreeNodeJson root = new FlowTypeTreeNodeJson("root", "业务类型");

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

            //
            //1、先查询出所有的业务类型
            List<FlowType> flowTypeList = flowTypeService.selectAll();
            Map<String,FlowType> typeMap = hashObject(flowTypeList);
            //2、查询出所有的流程环节，hash化；
            List<FlowUnit> flowUnitList =flowUnitService.selectAll();
            Map<String,FlowUnit> unitMap = hashObject(flowUnitList);
            //3、查询出业务类型与流程环节的对应关系
            List<FlowTemplateUnit> flowTemplateUnitList =flowTemplateUnitService.queryTemplateUnitByRoles(roleIds);
            if(flowTemplateUnitList==null||flowTemplateUnitList.size()==0){
                result.add(root);
                return result;
            }
            Map<String,List<FlowTemplateUnit>> type2unitMap = new HashMap<>();
            for(FlowTemplateUnit templateUnit:flowTemplateUnitList){
                String typeId = templateUnit.getFlowTypeId();
                List<FlowTemplateUnit> tempList = type2unitMap.get(typeId);
                if(tempList==null){
                    tempList = new ArrayList<>();
                }
                tempList.add(templateUnit);
                type2unitMap.put(typeId, tempList);
            }
            //构造数模型
            for (FlowType flowType:flowTypeList){
                String typeId = flowType.getId();
                List<FlowTemplateUnit> unitIdList = type2unitMap.get(typeId);
                FlowTypeTreeNodeJson typeTreeNodeJson = new FlowTypeTreeNodeJson(
                        typeId,flowType.getCode()+"    "+flowType.getName());
                if(unitIdList==null||unitIdList.size()==0){
                    continue;
                }
                for (FlowTemplateUnit templateUnit : unitIdList){
                    FlowUnit unit = unitMap.get(templateUnit.getFlowUnitId());
                    if(unit==null){
                        continue;
                    }
                    FlowTypeTreeNodeJson unitTreeNodeJson = new FlowTypeTreeNodeJson(templateUnit.getId(),unit.getCode()+"    "+unit.getName());
                    typeTreeNodeJson.addChild(unitTreeNodeJson);
                }
//                typeTreeNodeJson.getState().put("opened", false);
                root.addChild(typeTreeNodeJson);
            }

        }catch (Exception e){
            logger.error(e.getMessage(),e);
            result.add(root);
            return result;
        }

        result.add(root);
        return result;
    }


    /**
     * 根据业务类型id和流程单元id查询流程实例id，然后根据流程实例id查询订单信息
     * @param flowTypeId
     * @param templateUnitId
     * @return
     */
    @RequestMapping(value = "/queryOrderData",method = RequestMethod.POST)
    @ResponseBody
    public ShowOrderPageModel getOrderDataByFlowArg(String flowTypeId,String templateUnitId,int pageNum){
        ShowOrderPageModel pageModel = new ShowOrderPageModel();
        pageModel.setPages(0);
        pageModel.setPageNum(0);
        if(StringUtils.isEmpty(templateUnitId)){

            return pageModel;
        }
        try {
            OrderFlowRequestMode requestMode = new OrderFlowRequestMode();
            requestMode.setFlowTypeId(flowTypeId);
            requestMode.setTemplateUnitId(templateUnitId);
            pageModel = orderService.getOrderDataByFlowArgs(requestMode,pageNum);
            return pageModel;
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }

        return pageModel;
    }


    @RequestMapping(value = "/getOrderNums",method = RequestMethod.POST)
    @ResponseBody
    public Object getOrderNums(String templataUnitIds){
        logger.debug("templataUnitIds:" + templataUnitIds);
        List<CountNumModel> list = new ArrayList<>();
        if(StringUtils.isEmpty(templataUnitIds)){
            return list;
        }
        String[] idArr = templataUnitIds.split(",");
        try {
            list = orderService.queryOrderNumByTemplateUnitIds(Arrays.asList(idArr));
            HashMap<String,CountNumModel> flowTypeNumMap = new HashMap<>();
            for(CountNumModel temp:list){
                String flowTypeId = temp.getFlowTypeId();
                CountNumModel flowTypeNum = flowTypeNumMap.get(flowTypeId);
                if(flowTypeNum==null){
                    flowTypeNum = new CountNumModel(flowTypeId,0);
                }
                flowTypeNum.setNum(flowTypeNum.getNum()+temp.getNum());
                flowTypeNumMap.put(flowTypeId,flowTypeNum);
            }
            list.addAll(flowTypeNumMap.values());

        } catch (BusinessException e) {
            logger.error(e.getMessage(), e);
            logger.debug("根据流程单元id查询订单数目出错~");
            return new ArrayList<>();
        }
        return list;
    }

    private <T> Map<String,T> hashObject(List<T> list) throws Exception{
        Map<String,T> map = new HashMap<>();
        if(list==null||list.size()==0){
            return map;
        }
        for (T t:list){
            Method method = t.getClass().getMethod("getId");
            String id = (String)method.invoke(t);
            map.put(id,t);
        }
        return map;
    }


}
