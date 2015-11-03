package com.pdk.manage.common.tree;

/**
 * 标识一个对象是否是树节点
 * Created by hubo on 2015/8/10
 */
public interface ITreeNode {
    /**
     * 返回当前树节点ID
     * @return 树节点ID
     */
    String getNodeId();

    /**
     * 返回当前树节点父级节点ID
     * @return 父级节点ID
     */
    String getParentId();

}
