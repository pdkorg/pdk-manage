package com.pdk.manage.util;

/**
 * Created by hubo on 2015/8/13
 */
public class DBConst {

    public static final String ID = "id";

    public static final String DR = "dr";

    public static final String TS = "ts";

    public static final String STATUS = "status";

    /** 状态-启用 */
    public static final Short STATUS_ENABLE = 0;

    /** 状态-停用 */
    public static final Short STATUS_DISABLE = 1;

    /** 状态-取消关注 */
    public static final Short STATUS_UNATTENTION = 2;

    /** 布尔类型true */
    public static final String BOOLEAN_TRUE = "Y";

    /** 布尔类型false */
    public static final String BOOLEAN_FALSE = "N";

    /** 删除标识-正常 */
    public static final Short DR_NORMAL = 0;

    /** 删除标识-删除 */
    public static final Short DR_DEL = 1;

    /** 性别-男 */
    public static final Short SEX_MALE = 1;

    /** 性别-女 */
    public static final Short SEX_FEMALE = 2;

    /** 用户类型-微信用户 */
    public static final Short USER_TYPE_WEIXIN = 0;

    /** 用户类型-注册用户 */
    public static final Short USER_TYPE_REGISTERED = 1;

    /** 订单状态 未指派、未定价、未完成、已完成、已取消*/
    public static final Short ORDER_STATUS_UNDISTRIBUTE=0;
    public static final String ORDER_UNDISTRIBUTE_NAME = "未指派";
    public static final Short ORDER_STATUS_UNPRICE=1;
    public static final String ORDER_UNPRICE_NAME = "未定价";
    public static final Short ORDER_STATUS_UNFINISH=2;
    public static final String ORDER_UNFINISH_NAME = "未完成";
    public static final Short ORDER_STATUS_FINISHED=3;
    public static final String ORDER_FINISHED_NAME = "已完成";
    public static final Short ORDER_STATUS_CANCELED=4;
    public static final String ORDER_CANCELED_NAME = "已取消";

    /** 订单流程动作 指派、定价、支付、送达 */
    public static final Short FLOW_ACTION_DISTRIBUTE=0;
    public static final String FLOW_ACTION_DISTR_NAME="指派";
    public static final Short FLOW_ACTION_PRICE=1;
    public static final String FLOW_ACTION_PRICE_NAME="定价";
    public static final Short FLOW_ACTION_PAY=2;
    public static final String FLOW_ACTION_PAY_NAME="支付";
    public static final Short FLOW_ACTION_DELIVER_FINISH=3;
    public static final String FLOW_ACTION_DELIVER_FINISH_NAME="送达";

    /** 订单支付状态 */
    public static final Short ORDER_UNPAY=0;
    public static final Short ORDER_PAYED=1;
    public static final String ORDER_UNPAY_NAME = "未支付";
    public static final String ORDER_PAYED_NAME="已支付";
    /** 订单流程完成标识 */
    public static final Short ORDER_FLOW_FINISHED =1;
    public static final Short ORDER_FLOW_UNFINISH =0;

    /** 支付方式 */
    public static final Short PAY_WEBCHAT=0;
    public static final String PAY_WEBCHAT_NAME ="微信支付";
    public static final Short PAY_CASH=1;
    public static final String PAY_CASH_NAME="现金支付";

    /** 预置数据 */
    public static final String POSITION_MANAGER="PDKPREDATA0000000001";
    public static final String POSITION_WAITER="PDKPREDATA0000000002";
    public static final String POSITION_YEWUYUAN="PDKPREDATA0000000003";

    /** 消息类型--待办 */
    public static final Short MSG_TYPE_DB = 0;

    /** 优惠券状态 -- 未使用 */
    public static final Short COUPON_STATUS_UNUSE = 0;

    /** 优惠券状态 -- 已使用 */
    public static final Short COUPON_STATUS_USERD = 1;

    /** 优惠券状态 -- 已过期 */
    public static final Short COUPON_STATUS_OVERDUE = 2;

    /** 优惠券派送类型 -- 延迟发放 */
    public static final Short COUPON_SENDTYPE_DELAY = 0;
    /** 优惠券派送类型 -- 时间段 */
    public static final Short COUPON_SENDTYPE_BETWEEN = 1;

    /** 活动派送类型 -- 手动 */
    public static final Short COUPON_SENDTYPE_MANUAL = 0;
    /** 活动派送类型 -- 系统 */
    public static final Short COUPON_SENDTYPE_AUTOMATIC = 1;

    /** 优惠券派送状态 -- 系统自动发送，未启动 */
    public static final Short COUPON_ACTIVITY_STATUS_AUTO_UNSTART = 0;

    /** 优惠券派送状态 -- 系统自动发送，已启动 */
    public static final Short COUPON_ACTIVITY_STATUS_AUTO_STARTING =1;

    /** 优惠券派送状态 -- 已派送 */
    public static final Short COUPON_ACTIVITY_STATUS_SENDED = 2;

    /** 消费次数 */
    public static final Short RULE_TYPE_TIMES = 0;
    /** 消费金额 */
    public static final Short RULE_TYPE_MNY = 1;
    /** 用户首关日期 */
    public static final Short RULE_TYPE_REGISTER_TIME = 2;
    /** 下单日期 */
    public static final Short RULE_TYPE_ORDER_BEGIN_DATE = 3;
    /** 消费类型 */
    public static final Short RULE_TYPE_FLOW_TYPE = 4;
    /** 指定用户 */
    public static final Short RULE_TYPE_REF_USER = 5;

    /** 大于 */
    public static final Short RULE_OPT_TYPE_GREATER_THEN = 0;
    /** 大于等于 */
    public static final Short RULE_OPT_TYPE_GREATER_THEN_EQUAL = 1;
    /** 等于  */
    public static final Short RULE_OPT_TYPE_EQUAL_TO = 2;
    /** 小于等于 */
    public static final Short RULE_OPT_TYPE_LESS_THEN = 3;
    /** 小于 */
    public static final Short RULE_OPT_TYPE_LESS_THEN_EQUAL = 4;

    /** 规则连接方式 -- 和 */
    public static final Short RULE_CONN_TYPE_AND = 0;

    /** 规则连接方式 -- 或 */
    public static final Short RULE_CONN_TYPE_OR = 1;

    /** 发送出去的优惠券状态-未使用 */
    public static final Short STATUS_UNUSE = 0;

    /** 发送出去的优惠券状态-已使用 */
    public static final Short STATUS_SENDED = 1;

    /** 特殊活动优惠券类型-首次关注发送优惠券 */
    public static final String ACTIVITY_SEND_CODE_FST_REGISTER = "SGHD";
    /** 特殊活动优惠券类型-人员评价发送优惠券 */
    public static final String ACTIVITY_SEND_CODE_REVIEW = "PJHD";
    /** 特殊活动优惠券类型-分享发送优惠券 */
    public static final String ACTIVITY_SEND_CODE_SHARE = "FXHD";

    /** 区域预置- 北京 */
    public static final String CITY_AREA_BJ = "0001BD201508311509210001";
    /** 区域预置- 东城区 */
    public static final String CITY_AREA_DC = "0001BD201508311509210002";
}
