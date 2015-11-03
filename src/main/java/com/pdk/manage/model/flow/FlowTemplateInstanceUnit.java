package com.pdk.manage.model.flow;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pdk.manage.model.IBaseVO;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "pdk_flow_template_instanceunit")
public class FlowTemplateInstanceUnit implements IBaseVO, Serializable {
    @Id
    private String id;

    @Column(name = "template_instance_id")
    private String templateInstanceId;

    @Column(name = "flow_type_id")
    private String flowTypeId;

    @Column(name = "flow_unit_id")
    private String flowUnitId;

    @Transient
    private FlowUnit flowUnit;

    @Column(name = "template_unit_id")
    private String templateUnitId;

    private Short status;

    private String memo;

    @Column(name = "is_finish")
    private String isFinish;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "finish_time")
    private Date finishTime;

    @Column(name = "op_employee_id")
    private String opEmployeeId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "op_time")
    private Date opTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ts;

    private Short dr;

    @Column(name = "is_push_msg")
    private String isPushMsg;

    @Column(name = "msg_template")
    private String msgTemplate;

    private Short iq;

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
     * @return template_instance_id
     */
    public String getTemplateInstanceId() {
        return templateInstanceId;
    }

    /**
     * @param templateInstanceId
     */
    public void setTemplateInstanceId(String templateInstanceId) {
        this.templateInstanceId = templateInstanceId == null ? null : templateInstanceId.trim();
    }

    /**
     * @return flowtype_id
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
     * @return flowunit_id
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
     * @return template_unit_id
     */
    public String getTemplateUnitId() {
        return templateUnitId;
    }

    /**
     * @param templateUnitId
     */
    public void setTemplateUnitId(String templateUnitId) {
        this.templateUnitId = templateUnitId == null ? null : templateUnitId.trim();
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
     * @return is_finish
     */
    public String getIsFinish() {
        return isFinish;
    }

    /**
     * @param isFinish
     */
    public void setIsFinish(String isFinish) {
        this.isFinish = isFinish == null ? null : isFinish.trim();
    }

    /**
     * @return finish_time
     */
    public Date getFinishTime() {
        return finishTime;
    }

    /**
     * @param finishTime
     */
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    /**
     * @return op_employee_id
     */
    public String getOpEmployeeId() {
        return opEmployeeId;
    }

    /**
     * @param opEmployeeId
     */
    public void setOpEmployeeId(String opEmployeeId) {
        this.opEmployeeId = opEmployeeId == null ? null : opEmployeeId.trim();
    }

    /**
     * @return op_time
     */
    public Date getOpTime() {
        return opTime;
    }

    /**
     * @param opTime
     */
    public void setOpTime(Date opTime) {
        this.opTime = opTime;
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

    public FlowUnit getFlowUnit() {
        return flowUnit;
    }

    public void setFlowUnit(FlowUnit flowUnit) {
        this.flowUnit = flowUnit;
    }

}