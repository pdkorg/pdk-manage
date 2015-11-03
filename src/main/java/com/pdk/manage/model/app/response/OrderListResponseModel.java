package com.pdk.manage.model.app.response;

import java.util.List;

/**
 * Created by 程祥 on 15/8/8.
 * Function：订单列表响应model
 */
public class OrderListResponseModel extends BaseResponseModel {

    private List<OrderInfoResponseModel> list;

    public List<OrderInfoResponseModel> getList() {
        return list;
    }

    public void setList(List<OrderInfoResponseModel> list) {
        this.list = list;
    }
}
