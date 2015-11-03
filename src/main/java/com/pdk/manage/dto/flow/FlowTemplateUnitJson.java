package com.pdk.manage.dto.flow;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by hubo on 2015/8/17
 */
public class FlowTemplateUnitJson {

    @JsonProperty(value = "DT_RowId", index = 0)
    private String id;

    private String checked = "<input type='checkbox' class='checkboxes' value='1'/>";

    private int index;

    private String flowUnitName;

    private String roleName;

    private String pushMsg;

    private String memo;

    private Date ts;

    public FlowTemplateUnitJson(String id, int index, String flowUnitName, String roleName, String pushMsg, String memo, Date ts) {
        this.id = id;
        this.index = index;
        this.flowUnitName = flowUnitName;
        this.roleName = roleName;
        this.memo = memo;
        this.ts = ts;

        if(pushMsg != null && pushMsg.equals("Y")) {
            this.pushMsg = "是";
        } else {
            this.pushMsg = "否";
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getFlowUnitName() {
        return flowUnitName;
    }

    public void setFlowUnitName(String flowUnitName) {
        this.flowUnitName = flowUnitName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getTs() {
        return ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }

    public String getPushMsg() {
        return pushMsg;
    }

    public void setPushMsg(String pushMsg) {
        this.pushMsg = pushMsg;
    }
}
