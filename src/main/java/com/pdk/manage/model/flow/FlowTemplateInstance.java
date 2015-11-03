package com.pdk.manage.model.flow;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pdk.manage.model.IBaseVO;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "pdk_flow_template_instance")
public class FlowTemplateInstance implements IBaseVO, Serializable {
    @Id
    private String id;

    @Column(name = "flow_type_id")
    private String flowTypeId;

    @Column(name = "template_id")
    private String templateId;

    private Short status;

    private String memo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ts;

    private Short dr;

    @Transient
    List<FlowTemplateInstanceUnit> flowTemplateInstanceUnitList;

    public List<FlowTemplateInstanceUnit> getFlowTemplateInstanceUnitList() {
        return flowTemplateInstanceUnitList;
    }

    public void setFlowTemplateInstanceUnitList(List<FlowTemplateInstanceUnit> flowTemplateInstanceUnitList) {
        this.flowTemplateInstanceUnitList = flowTemplateInstanceUnitList;
    }

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
}