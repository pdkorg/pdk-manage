package com.pdk.manage.dto.sm;

import com.pdk.manage.common.tree.ITreeNode;
import com.pdk.manage.model.sm.Func;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能节点DTO
 * Created by hubo on 2015/8/10
 */
public class FuncDto extends Func implements ITreeNode, Serializable{

    public FuncDto(Func func) {
        setId(func.getId());
        setCode(func.getCode());
        setName(func.getName());
        setIncode(func.getIncode());
        setIcon(func.getIcon());
        setHref(func.getHref());
        setParentId(func.getParentId());
        setStatus(func.getStatus());
        setTs(func.getTs());
        setDr(func.getDr());
        setSq(func.getSq());
    }

    public FuncDto() {

    }

    private boolean start = false;

    private List<FuncDto> children = new ArrayList<>();

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public List<FuncDto> getChildren() {
        return children;
    }

    @Override
    public String getNodeId() {
        return getId();
    }

    public void addChildFunc(FuncDto child) {
        children.add(child);
    }
}
