package com.pdk.manage.model.app.response;

/**
 * Created by 程祥 on 15/10/14.
 * Function：
 */
public class UpdateDeliveryStatusModel extends BaseResponseModel{
    private String deliveryStatus;
    private Short isFinished;
    private Long ts;

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Short getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Short isFinished) {
        this.isFinished = isFinished;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }
}
