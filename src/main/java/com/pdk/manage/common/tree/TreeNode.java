package com.pdk.manage.common.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 树节点
 * Created by hubo on 2015/8/10
 */
public class TreeNode<E> {

    private E value;

    public List<TreeNode> getChilds() {
        return childs;
    }

    private List<TreeNode> childs = new ArrayList<>();

    private TreeNode parentNode;

    public TreeNode(TreeNode parentNode, E value) {
        this.value = value;
        this.parentNode = parentNode;
    }

    public void addChild(TreeNode node) {
        childs.add(node);
    }

    public void setParentNode(TreeNode parentNode) {
        this.parentNode = parentNode;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public E getValue() {
        return value;
    }

    public TreeNode getParentNode() {
        return parentNode;
    }


}
