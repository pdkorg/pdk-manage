package com.pdk.manage.dto.sm;

import com.pdk.manage.model.sm.Role;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuhaiming on 2015/8/11.
 */
public class RoleTreeJson {
    public static final String ICON_LEAF = "fa fa-file icon-state-warning";

    private String id;

    private String text;

    private String icon;

    private Map<String, Object> state = new HashMap<>();

    private List<RoleTreeJson> children;

    public RoleTreeJson(Role role) {
        text = role.getCode() + " " + role.getName();
        id = role.getId();
        state.put("opened", true);
    }

    public RoleTreeJson(String orgText) {
        text = orgText;
        id = "root";
        state.put("opened", true);
    }

    public List<RoleTreeJson> getChildren() {
        return children;
    }

    public void setChildren(List<RoleTreeJson> children) {
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
}
