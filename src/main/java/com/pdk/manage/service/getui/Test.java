package com.pdk.manage.service.getui;




/**
 * Created by chengxiang on 15/8/6.
 */
public class Test {

    static String appId = "AKEHzPyYDg6UAp2xP4TxY2";
    static String appkey = "njU14u0Qmc5w3nYIldINp8";
    static String master = "86Ayw6KurE8O1nVtNPNgX2";
    static String host = "http://sdk.open.api.igexin.com/apiex.htm";

    public static void main(String[] args) throws Exception {
//89236c97d03c55a397a3f3044d32edac

        GetTuiSendMessUtil util = new GetTuiSendMessUtil();
        util.sendMessToApp("89236c97d03c55a397a3f3044d32edac");


/**
        IGtPush push = new IGtPush(host, appkey, master);
		 TransmissionTemplate template = TransmissionTemplateDemo();

        Target target = new Target();
        target.setAppId(appId);
        target.setClientId("89236c97d03c55a397a3f3044d32edac");
        SingleMessage message = new SingleMessage();
        message.setData(template);
        message.setOffline(true);
        message.setOfflineExpireTime(24 * 1000 * 3600);
        message.setPushNetWorkType(0);
        IPushResult ret = push.pushMessageToSingle(message,target);

        System.out.println(ret.getResponse().toString());
         */
    }

    /**
    public static TransmissionTemplate TransmissionTemplateDemo()
            throws Exception {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appkey);
        template.setTransmissionType(2);
        template.setTransmissionContent("{\"key\":\"value\"}");
        return template;
    }

    public static LinkTemplate linkTemplateDemo() throws Exception {
        LinkTemplate template = new LinkTemplate();
        template.setAppId(appId);
        template.setAppkey(appkey);
        template.setTitle("ddd");
        template.setText("ddd");
        template.setLogo("icon.png");
        template.setLogoUrl("");
        template.setIsRing(true);
        template.setIsVibrate(true);
        template.setIsClearable(true);
        template.setUrl("http://www.baidu.com");
        // template.setPushInfo("actionLocKey", 1, "message", "sound",
        // "payload",
        // "locKey", "locArgs", "launchImage",1);
        return template;
    }

**/
}
