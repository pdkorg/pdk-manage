package com.pdk.manage.service.flow;

import com.pdk.manage.dao.flow.FlowTemplateInstanceDao;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.flow.FlowTemplate;
import com.pdk.manage.model.flow.FlowTemplateInstance;
import com.pdk.manage.model.flow.FlowTemplateInstanceUnit;
import com.pdk.manage.model.flow.FlowTemplateUnit;
import com.pdk.manage.model.order.OrderTable;
import com.pdk.manage.service.BaseService;
import com.pdk.manage.service.order.OrderService;
import com.pdk.manage.util.CommonUtil;
import com.pdk.manage.util.DBConst;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 流程实例服务类
 * Created by hubo on 2015/8/28
 */
@Service
public class FlowTemplateInstanceService extends BaseService <FlowTemplateInstance> {

    private static Logger log = LoggerFactory.getLogger(FlowTemplateInstanceService.class);

    @Autowired
    private FlowTemplateService flowTemplateService;
    @Autowired
    private FlowTemplateInstanceUnitService flowTemplateInstanceUnitService;
    @Autowired
    private OrderService orderService;

    private BlockingQueue<FlowUnitMessage> msgQueue;

    private ExecutorService exec;

    private int flowUnitMsgWorkerCount = 2;

    private int threadCount = 10;

    private int queueCount = 2000;

    public FlowTemplateInstanceService () {
        exec = Executors.newFixedThreadPool(threadCount);
        msgQueue = new LinkedBlockingQueue<>(queueCount);
        for (int i = 0; i < flowUnitMsgWorkerCount; i++) {
            exec.execute(new FlowUnitMsgWorker());
        }
    }

    @PreDestroy
    public void destroy() {
        exec.shutdownNow();
    }

    private class FlowUnitMsgWorker implements Runnable{
        @Override
        public void run() {

            while (!Thread.interrupted()) {
                try {
                    FlowUnitMessage msg = msgQueue.take();
                    sendFlowUnitMsg(msg);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.error(e.getMessage(), e);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }

        private void sendFlowUnitMsg(FlowUnitMessage msg) throws IOException {

            CloseableHttpClient httpClient = HttpClients.createDefault();

            Map<String, String> args = new HashMap<>();
            args.put("type", "1");
            args.put("content", msg.getMsg());

            HttpResponse response = httpClient.execute(postForm(CommonUtil.getMsgServerPath() + "/token/notice/" + msg.getTargetUserId(), args));

            if(HttpStatus.SC_OK == response.getStatusLine().getStatusCode()){
                log.info("发送流程模板消息成功! {}", msg);
            } else {
                log.error("发送流程模板消息错误，错误码：{}", response.getStatusLine().getStatusCode());
            }
        }

        private HttpPost postForm(String url, Map<String, String> params){

            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> nameValuePairs = new ArrayList <>();

            Set<String> keySet = params.keySet();
            for(String key : keySet) {
                nameValuePairs.add(new BasicNameValuePair(key, params.get(key)));
            }

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                log.error(e.getMessage(), e);
            }

            return httpPost;
        }
    }

    private class FlowUnitMessage {

        private final String msgTemplate;

        private final OrderTable order;

        private String msg;

        public FlowUnitMessage(String msgTemplate, OrderTable order) {
            this.msgTemplate = msgTemplate;
            this.order = order;
            msg = transform();
        }

        public String transform() {

            String msg = msgTemplate;

            String baseSearchText = "${order.";

            int index;

            while ((index = msg.indexOf(baseSearchText)) > 0) {

                index = index + baseSearchText.length();

                StringBuilder fieldName = new StringBuilder();

                while(index <= msg.length() && msg.charAt(index) != '}') {
                    fieldName.append(msg.charAt(index));
                    index++;
                }

                try {
                    Object value = PropertyUtils.getSimpleProperty(order, fieldName.toString());
                    msg = msg.replace(baseSearchText + fieldName.toString() + "}", value == null ? "" : value.toString());
                } catch (Exception e) {
                    log.error("fieldName : {}", fieldName.toString());
                    log.error(e.getMessage(), e);
                }

            }

            return msg;
        }

        public String getMsg() {
            return msg;
        }

        public String getTargetUserId() {
            return order.getUserId();
        }

        @Override
        public String toString() {
            return "FlowUnitMessage{msg='" + getMsg() + "'" + ", targetUserId='" + getTargetUserId() + "'}";
        }
    }

    /**
     * 根据业务类型生成流程实例
     * @param flowTypeId 业务类型ID
     * @return 流程实例ID
     * @throws BusinessException
     */
    @Transactional(rollbackFor = Exception.class)
    public FlowTemplateInstance generateFlowTemplateInstance(String flowTypeId) throws BusinessException {

        if(StringUtils.isEmpty(flowTypeId)) {
            throw new BusinessException("generateFlowTemplateInstance error : flowTypeId is null");
        }

        FlowTemplate template = flowTemplateService.getFlowTemplate(flowTypeId);

        FlowTemplateInstance instance = new FlowTemplateInstance();

        instance.setFlowTypeId(flowTypeId);
        instance.setTemplateId(template.getId());
        instance.setStatus(template.getStatus());
        instance.setMemo(template.getMemo());

        save(instance);

        List<FlowTemplateUnit> flowTemplateUnits = template.getFlowTemplateUnitList();

        if(flowTemplateUnits.size() > 0) {

            List<FlowTemplateInstanceUnit> instanceUnits = new ArrayList<>();
            for (FlowTemplateUnit flowTemplateUnit : flowTemplateUnits) {
                FlowTemplateInstanceUnit unit = new FlowTemplateInstanceUnit();
                unit.setTemplateInstanceId(instance.getId());
                unit.setFlowTypeId(flowTypeId);
                unit.setFlowUnitId(flowTemplateUnit.getFlowUnitId());
                unit.setTemplateUnitId(flowTemplateUnit.getId());
                unit.setStatus(flowTemplateUnit.getStatus());
                unit.setMemo(flowTemplateUnit.getMemo());
                unit.setIsPushMsg(flowTemplateUnit.getIsPushMsg());
                unit.setMsgTemplate(flowTemplateUnit.getMsgTemplate());
                unit.setIq(flowTemplateUnit.getIq());
                instanceUnits.add(unit);
            }

            flowTemplateInstanceUnitService.save(instanceUnits);

            instance.setFlowTemplateInstanceUnitList(instanceUnits);
        }

        return instance;
    }

    /**
     * 完成当前流程环节并返回当前流程环节
     * @param orderId 流程实例ID
     * @return 当前流程环节
     * @throws BusinessException
     */
    public FlowTemplateInstanceUnit finishCurrFlowUnit(String orderId, String opId, Date opTime, Date finishTime) throws BusinessException{

        List<OrderTable> orderTableList = orderService.getOrderDataByOrderId(orderId);

        if(orderTableList == null || orderTableList.size() == 0) {
            return null;
        }

        final OrderTable order = orderTableList.iterator().next();

        List<FlowTemplateInstanceUnit> instanceUnitList = flowTemplateInstanceUnitService.getFlowTemplateInstanceUnitList(order.getFlowInstanceId());

        int currIndex = -1;

        for (int i = 0; i < instanceUnitList.size(); i++) {
            currIndex = i;
            final FlowTemplateInstanceUnit unit = instanceUnitList.get(currIndex);
            if(unit.getIsFinish() == null || unit.getIsFinish().equals("N")) {
                unit.setIsFinish("Y");
                unit.setOpEmployeeId(opId);
                unit.setOpTime(opTime);
                unit.setFinishTime(finishTime);
                flowTemplateInstanceUnitService.update(unit);

                if(unit.getIsPushMsg() != null && unit.getIsFinish().equals("Y")) {
                    exec.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                msgQueue.put(new FlowUnitMessage(unit.getMsgTemplate(), order));
                            } catch (InterruptedException e) {
                                log.error(e.getMessage(), e);
                            }
                        }
                    });
                }

                break;
            }
        }

        return currIndex + 1 == instanceUnitList.size() ? null : instanceUnitList.get(currIndex + 1);
    }

    /**
     * 查询当前流程环节
     * @param flowInstanceId 流程实例ID
     * @return 当前流程环节
     */
    public FlowTemplateInstanceUnit queryCurrFlowUnit(String flowInstanceId) {

        if(flowInstanceId == null) {
            throw new IllegalArgumentException("method queryCurrFlowUnit arg flowInstanceId is null!");
        }

        List<FlowTemplateInstanceUnit> instanceUnitList = flowTemplateInstanceUnitService.getFlowTemplateInstanceUnitList(flowInstanceId);

        for (FlowTemplateInstanceUnit unit : instanceUnitList) {
            if (unit.getIsFinish() == null || unit.getIsFinish().equals("N")) {
                return unit;
            }
        }
        return null;
    }

    /**
     * 查询订单状态（用于给客户进行显示）
     * <p>现阶段只有两种，一种是已定价，一种是未定价</p>
     * @return 是否已经定价
     */
    public boolean queryOrderStatusToUser(String flowInstanceId) {

        List<FlowTemplateInstanceUnit> instanceUnitList = flowTemplateInstanceUnitService.getFlowTemplateInstanceUnitList(flowInstanceId);

        return isPriced(instanceUnitList);
    }

    public boolean isPriced(List<FlowTemplateInstanceUnit> instanceUnitList) {

        boolean isPriced = false;

        for (FlowTemplateInstanceUnit unit : instanceUnitList) {
            if (unit.getIsFinish() != null && unit.getIsFinish().equals("Y")
                    && DBConst.FLOW_ACTION_PRICE.equals(unit.getFlowUnit().getFlowActionCode())) {
                isPriced = true;
                break;
            }
        }

        return isPriced;
    }

    /**
     * 查询订单状态（用于给客户进行显示）
     * <p>现阶段只有两种，一种是已定价，一种是未定价</p>
     * @return 是否已经定价
     */
    public Map<String, Boolean> queryOrderStatusToUser(List<String> flowInstanceIds) {

        Map<String, Boolean> result = new HashMap<>();

        List<FlowTemplateInstance> flowTemplateInstanceList = this.getDao().queryFlowTemplateInstances(flowInstanceIds);

        for (FlowTemplateInstance flowTemplateInstance : flowTemplateInstanceList) {
            result.put(flowTemplateInstance.getId(), isPriced(flowTemplateInstance.getFlowTemplateInstanceUnitList()));
        }

        return result;
    }

    public FlowTemplateInstanceDao getDao() {
        return (FlowTemplateInstanceDao) this.dao;
    }



}
