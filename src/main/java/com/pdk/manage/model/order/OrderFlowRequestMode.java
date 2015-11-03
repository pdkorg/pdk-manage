package com.pdk.manage.model.order;

import java.util.List;

/**
 * Created by 程祥 on 15/10/22.
 * Function：流程环节查询订单请求model
 */
public class OrderFlowRequestMode {
    private String flowTypeId;
    private String templateUnitId;
    private String flowUnitId;
    private List<String> roleIds;


    public String getFlowTypeId() {
        return flowTypeId;
    }

    public void setFlowTypeId(String flowTypeId) {
        this.flowTypeId = flowTypeId;
    }

    public String getTemplateUnitId() {
        return templateUnitId;
    }

    public void setTemplateUnitId(String templateUnitId) {
        this.templateUnitId = templateUnitId;
    }

    public String getFlowUnitId() {
        return flowUnitId;
    }

    public void setFlowUnitId(String flowUnitId) {
        this.flowUnitId = flowUnitId;
    }

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }
}
