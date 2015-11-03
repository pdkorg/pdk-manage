package com.pdk.manage.util;

/**
 * Created by hubo on 15/8/11
 */
public class IdGenerator {

    public static final String SYSTEM_ID = "0001";
    /**
     * 系统管理模块编码
     */
    public static final String SM_MODULE_CODE = "SM";
    /**
     * 基础数据模块编码
     */
    public static final String BD_MODULE_CODE = "BD";
    /**
     * 流程管理模块编码
     */
    public static final String FL_MODULE_CODE = "FL";
    /**
     * 订单管理模块编码
     */
    public static final String OR_MODULE_CODE = "OR";
    /**
     * 优惠券管理模块编码
     */
    public static final String CP_MODULE_CODE = "CP";

    /**
     * 优惠券编码前缀
     */
    public static final String CODE_PER_MARK_COUPON = "C";

//    public static String generateId() {
//        String seqNum = SequenceGenerator.getInstance().nextId();
//        return SYSTEM_ID + SM_MODULE_CODE + seqNum;
//    }

    public static String generateId(String moduleCode) {
        if(moduleCode == null || moduleCode.length() != 2) {
            throw new IllegalArgumentException("module code illegal !");
        }
        return SYSTEM_ID + moduleCode + SequenceGenerator.getInstance().nextId();
    }

    //生成订单号
    public static String generateCode() {
        return "D"+SequenceGenerator.getInstance().nextId();
    }

    public static String generateCode(String mark) {
        return mark + SequenceGenerator.getInstance().nextId();
    }

}
