package com.pdk.manage.service.getui;


import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.pdk.manage.action.app.AppServiceController;
import com.pdk.manage.model.app.CheckInfoModel;
import com.pdk.manage.model.app.response.OrderDetailResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;


/**
 * Created by chengxiang on 15/8/6.
 * 发送个推服务的工具类
 */
public class GetTuiSendMessUtil {

    private Logger logger = LoggerFactory.getLogger(AppServiceController.class);

    private final String appId = "AKEHzPyYDg6UAp2xP4TxY2";
    private final String appkey = "njU14u0Qmc5w3nYIldINp8";
    private final String master = "86Ayw6KurE8O1nVtNPNgX2";
    private final String host = "http://sdk.open.api.igexin.com/apiex.htm";

    /**
     * 发送透传消息到业务员
     * @param orderdetail
     */
    public void sendMessToApp(String employcode,OrderDetailResponseModel orderdetail) throws Exception{
        IGtPush push = new IGtPush(host, appkey, master);

        CheckInfoModel model = AppServiceController.inworkers.get(employcode);

        if(model==null){
            logger.debug("个推--------》"+employcode+"model为空");
            if(AppServiceController.inworkers.keySet()!=null){
                logger.debug("正在工作的小跑君ID：" + Arrays.toString(AppServiceController.inworkers.keySet().toArray(new String[0])));
                Iterator<String> it = AppServiceController.inworkers.keySet().iterator();
                Hashtable<String,CheckInfoModel> map = AppServiceController.inworkers;
                while (it.hasNext()){
                    CheckInfoModel info = map.get(it.next());
                    logger.debug("usercode:"+info.getUserCode()+"***clientId:"+info.getClientid());
                }
            }

            return;
        }
        logger.debug("个推--------》clientid:"+model.getClientid()+"---employeeid:"+employcode);

        TransmissionTemplate template = transmissionTemplateDemo(orderdetail);

        String clientId = model.getClientid();
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(clientId);
        SingleMessage message = new SingleMessage();
        message.setData(template);
        message.setOffline(true);
        message.setOfflineExpireTime(24 * 1000 * 3600);
        message.setPushNetWorkType(0);
        IPushResult ret = push.pushMessageToSingle(message,target);

        System.out.println(ret.getResponse().toString());
    }


    private TransmissionTemplate transmissionTemplateDemo(OrderDetailResponseModel orderdetail)
            throws Exception {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appkey);
        //1代表立即启动app，2代表等待进入
        template.setTransmissionType(2);
        template.setTransmissionContent("{\"busTypeName\":\""
                +orderdetail.getBusTypeName()+"\",\"orderid\":\""
                +orderdetail.getOrderid()+"\",\"cusaddress\":\""
                +orderdetail.getCusaddress()+"\",\"ordercode\":\""
                +orderdetail.getOrdercode()+"\"}");
        return template;
    }

    public void sendMessToApp(String clientId) throws Exception{
        IGtPush push = new IGtPush(host, appkey, master);


        TransmissionTemplate template = transmissionTemplateDemo();


        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(clientId);
        SingleMessage message = new SingleMessage();
        message.setData(template);
        message.setOffline(true);
        message.setOfflineExpireTime(24 * 1000 * 3600);
        message.setPushNetWorkType(0);
        IPushResult ret = push.pushMessageToSingle(message,target);

        System.out.println(ret.getResponse().toString());
    }


    private TransmissionTemplate transmissionTemplateDemo()
            throws Exception {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appkey);
        //1代表立即启动app，2代表等待进入
        template.setTransmissionType(2);
        template.setTransmissionContent("{\"busTypeName\":\"aaaa\",\"orderid\":\"aaaaacode\",\"buyaddress\":\"北京beijing\"}");
        return template;
    }
}
