package com.pdk.manage.dto;

import com.pdk.manage.model.sm.Message;
import com.pdk.manage.util.CommonConst;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by liuhaiming on 2015/9/9.
 */
public class InboxJson {
    private String id;

    private String fromName;

    private String headerImg;

    private String timeMsg;

    private String message;

    public InboxJson(Message msg) {
        id = msg.getId();
        fromName = msg.getFrEmployee().getName();
        timeMsg = getDispTime(msg.getCreateTime());
        message = msg.getMessage();

        if (StringUtils.isEmpty(msg.getFrEmployee().getHeaderImg())) {
            headerImg = CommonConst.DEFAULT_HEADER_IMAGE_PATH;
        } else {
            headerImg = CommonConst.HEADER_IMAGE_PATH + File.separator + msg.getFrEmployee().getHeaderImg();
        }

    }

    private String getDispTime(Date createTime) {
        Calendar currCal = Calendar.getInstance();

        long diff = currCal.getTime().getTime() - createTime.getTime();
        long second = diff / 1000;
        long minute = second / 60;
        long hour = minute / 60;
        long day = hour / 24;

        String timeMsg = "";
        if ( day >= 3 ) { // 3天以上的消息显示“很久前”
            timeMsg = "很久前";
        } else if ( day > 0 ) { // 1天以上的消息显示“n天前”
            timeMsg = day + "天前";
        } else if ( hour > 0 ) { // 1小时以上的消息显示“n小时前”
            timeMsg = hour + "小时前";
        } else if ( minute > 0 ) { // 1分钟以上的消息显示“n分钟前”
            timeMsg = minute + "分钟前";
        } else if ( second >= 15 ) { // 15秒以上的消息显示“n秒前”
            timeMsg = second + "秒前";
        } else { // 15秒以内的消息显示“刚刚”
            timeMsg = "刚刚";
        }

        return timeMsg;
    }

    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, -60*60*24*4-5);
        System.out.println( new InboxJson().getDispTime(cal.getTime()) );
    }

    public InboxJson() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getHeaderImg() {
        return headerImg;
    }

    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg;
    }

    public String getTimeMsg() {
        return timeMsg;
    }

    public void setTimeMsg(String timeMsg) {
        this.timeMsg = timeMsg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
