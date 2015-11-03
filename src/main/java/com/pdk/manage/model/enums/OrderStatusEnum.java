package com.pdk.manage.model.enums;

/**
 * Created by 程祥 on 15/8/26.
 * Function：订单状态枚举类
 */
public enum OrderStatusEnum {

    /** 订单状态 未指派、未定价、未支付、未完成、已完成、已取消*/
    UNDISTRIBUTE("未指派"),
    UNPRICE("未定价"),
    UNPAY("未支付"),
    UNFINISH("未完成"),
    FINISHED("已完成"),
    CANCELED("已取消");

    private String value;

    private OrderStatusEnum(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
