package com.pdk.manage.model.bd;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pdk.manage.annotation.TableSearchColumn;
import com.pdk.manage.model.IBaseVO;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by liuhaiming on 2015/8/30.
 */
@Table(name = "pdk_bd_quick_reply")
public class QuickReply implements IBaseVO {
    @Id
    private String id;

    @NotNull
    @Size(max=400)
    @TableSearchColumn
    private String info;

    private Short sort;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ts;

    private Short dr;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Short getSort() {
        return sort;
    }

    public void setSort(Short sort) {
        this.sort = sort;
    }

    @Override
    public Date getTs() {
        return ts;
    }

    @Override
    public void setTs(Date ts) {
        this.ts = ts;
    }

    @Override
    public Short getDr() {
        return dr;
    }

    @Override
    public void setDr(Short dr) {
        this.dr = dr;
    }
}
