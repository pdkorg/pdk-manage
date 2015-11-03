package com.pdk.manage.model.order;

import java.util.List;

/**
 * Created by 程祥 on 15/10/22.
 * Function：
 */
public class OrderFlowUnitReqModel {
    private List<String> list;
    private List<String> roleList;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public List<String> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }
}
