package com.pdk.manage.dto.sm;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pdk.manage.model.sm.Org;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuhaiming on 2015/8/11.
 */
public class OrgJson {
    public static final String ICON_LEAF = "fa fa-file icon-state-warning";

    private String id;

    private String text;

    private String icon;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ts;

    private Map<String, Object> state = new HashMap<>();

    private List<OrgJson> children;

    public OrgJson(Org org) {
        text = org.getCode() + " " + org.getName();
        id = org.getId();
        ts = org.getTs();
        state.put("opened", true);
    }

    public OrgJson(String orgText) {
        text = orgText;
        id = "root";
        state.put("opened", true);
    }

    public List<OrgJson> getChildren() {
        return children;
    }

    public void setChildren(List<OrgJson> children) {
        this.children = children;
    }

    public Map<String, Object> getState() {
        return state;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTs() {
        return ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }
}
