package com.pdk.manage.model.flow;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pdk.manage.model.IBaseVO;
import com.pdk.manage.model.sm.Role;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Table(name = "pdk_flow_template_unit")
public class FlowTemplateUnit implements IBaseVO, Serializable {
    @Id
    private String id;

    @NotNull
    @Column(name = "template_id")
    private String templateId;

    @NotNull
    @Column(name = "flow_type_id")
    private String flowTypeId;

    @NotNull
    @Column(name = "flow_unit_id")
    private String flowUnitId;

    @NotNull
    @Column(name = "role_id")
    private String roleId;

    private Short status;

    private String memo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ts;

    private Short dr;

    @Column(name = "is_push_msg")
    private String isPushMsg;

    @Column(name = "msg_template")
    @Size(max = 400)
    private String msgTemplate;

    private Short iq;

    @Transient
    private Role role;
    @Transient
    private FlowUnit flowUnit;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * @return template_id
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * @param templateId
     */
    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    /**
     * @return flow_type_id
     */
    public String getFlowTypeId() {
        return flowTypeId;
    }

    /**
     * @param flowTypeId
     */
    public void setFlowTypeId(String flowTypeId) {
        this.flowTypeId = flowTypeId == null ? null : flowTypeId.trim();
    }

    /**
     * @return flow_unit_id
     */
    public String getFlowUnitId() {
        return flowUnitId;
    }

    /**
     * @param flowUnitId
     */
    public void setFlowUnitId(String flowUnitId) {
        this.flowUnitId = flowUnitId == null ? null : flowUnitId.trim();
    }

    /**
     * @return role_id
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * @param roleId
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    /**
     * @return status
     */
    public Short getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * @return memo
     */
    public String getMemo() {
        return memo;
    }

    /**
     * @param memo
     */
    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    /**
     * @return ts
     */
    public Date getTs() {
        return ts;
    }

    /**
     * @param ts
     */
    public void setTs(Date ts) {
        this.ts = ts;
    }

    /**
     * @return dr
     */
    public Short getDr() {
        return dr;
    }

    /**
     * @param dr
     */
    public void setDr(Short dr) {
        this.dr = dr;
    }

    /**
     * @return is_push_msg
     */
    public String getIsPushMsg() {
        return isPushMsg;
    }

    /**
     * @param isPushMsg
     */
    public void setIsPushMsg(String isPushMsg) {
        this.isPushMsg = isPushMsg == null ? null : isPushMsg.trim();
    }

    /**
     * @return msg_template
     */
    public String getMsgTemplate() {
        return msgTemplate;
    }

    /**
     * @param msgTemplate
     */
    public void setMsgTemplate(String msgTemplate) {
        this.msgTemplate = msgTemplate == null ? null : msgTemplate.trim();
    }

    /**
     * @return iq
     */
    public Short getIq() {
        return iq;
    }

    /**
     * @param iq
     */
    public void setIq(Short iq) {
        this.iq = iq;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public FlowUnit getFlowUnit() {
        return flowUnit;
    }

    public void setFlowUnit(FlowUnit flowUnit) {
        this.flowUnit = flowUnit;
    }
}