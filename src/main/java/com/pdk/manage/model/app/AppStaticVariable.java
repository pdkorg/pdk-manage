package com.pdk.manage.model.app;

import java.util.HashMap;

/**
 * Created by 程祥 on 15/8/9.
 * Function：App常用静态变量值
 */
public class AppStaticVariable {

    //登陆状态session前缀
    public static final String LOGIN_SESSION_PRE="APPLOGIN";
    //处于工作状态人员的集合session 名称
    public static final String INWORK_SESSION_NAME = "APPINWORKSESSIONNAME";

    //状态码
    public static final String SUCCEED_CODE = "0";
    public static final String PARAMERROR_CODE = "-1";
    public static final String CHECKEXCEPTION_CODE = "-2";
    public static final String SYSTEMERROR_CODE = "-3";
    public static final String TS_ERROR = "-4";

    public static final String LOGIN_FAILED_CODE="100";
    public static final String UPDATEPWERROR_CODE = "102";
    public static final String OLDPASSERROR_CODE = "103";
    public static final String ORDERDETAILERROR_CODE = "104";
    public static final String BUSISTATERROR_CODE = "105";
    public static final String UPDATELOCERROR_CODE = "106";
    public static final String NEWGOODSERROR_CODE = "107";
    public static final String ORDERANALYERROR_CODE = "108";
    public static final String UPDATEPAYTYPEERROR_CODE = "109";
    //状态提示内容
    public static final String SUCCEED_MESG = "成功~";
    public static final String LOGIN_FAILED_MESG="账号或者密码不正确，请您确认后重新登录!";
    public static final String CHECKEXCEPTION_MESG = "当前登录态与系统不一致，请重新登陆!";
    public static final String UPDATELOCERROR_MESG = "更新业务员位置失败~";
    public static final String ORDERDETAILERROR_MESG="查询订单详情请求失败";
    public static final String OLDPASSERROR_MESG = "原密码错误或不存在当前用户~";
    public static final String BUSISTATERROR_MESG = "更新流程状态数据库操作失败~";
    public static final String NEWGOODSERROR_MESG = "修改订单商品失败";
    public static final String ORDERANALYERROR_MESG = "获取订单统计信息失败~";
    public static final String UPDATEPAYTYPEERROR_MESG = "修改订单支付类型失败~";

    //业务流程id对应的名称
    public static final HashMap<String,String[][]> DISTMAP = new HashMap<String,String[][]>(){
        {
            String[][] ordinarydist = {{"10","20","30","40","50"},{"配单","等待","到店","出店","完成"}};
            String[][] a2dist = {{"10","20","30","40","4310","4320","4330","50"},{"配单","等待","到店","出店","送时","取到","取出","完成"}};
            String[][] a4dist = {{"10","20","30","40","4410","4420","50"},{"配单","等待","到店","出店","取时","取回","完成"}};
            put("other",ordinarydist);
            put("A3",a2dist);
            put("A4",a4dist);
        }
    };
    public static final HashMap<String,String> DISTNAMES = new HashMap<String,String>(){
        {
            put("10","配单");
            put("20","等待");
            put("30","到店");
            put("40","出店");
            put("50","完成");
            put("4310","送时");
            put("4320","取到");
            put("4330","取出");
            put("4410","取时");
            put("4420","取回");
        }
    };

}
