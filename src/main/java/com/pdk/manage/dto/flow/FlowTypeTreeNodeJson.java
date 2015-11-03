package com.pdk.manage.dto.flow;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hubo on 2015/8/15
 */
public class FlowTypeTreeNodeJson {

    private String id;

    @JsonProperty("text")
    private String codeAndName;

    @JsonProperty("state")
    private Map<String, Object> state = new HashMap<>();

    public FlowTypeTreeNodeJson(String id, String codeAndName) {
        this.id = id;
        this.codeAndName = codeAndName;
        state.put("opened", true);
    }

    public List<FlowTypeTreeNodeJson> getChildren() {
        return children;
    }

    public void addChild(FlowTypeTreeNodeJson child) {
        children.add(child);
    }

    List<FlowTypeTreeNodeJson> children = new ArrayList<>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodeAndName() {
        return codeAndName;
    }

    public void setCodeAndName(String codeAndName) {
        this.codeAndName = codeAndName;
    }

    public Map<String, Object> getState() {
        return state;
    }

    public void setState(Map<String, Object> state) {
        this.state = state;
    }

}
