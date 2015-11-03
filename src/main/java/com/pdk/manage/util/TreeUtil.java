package com.pdk.manage.util;

import com.pdk.manage.common.tree.ITreeNode;
import com.pdk.manage.common.tree.TreeNode;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hubo on 2015/8/10
 */
public class TreeUtil {
    /**
     * 构建树
     * @param values 需要组建树的数据List
     * @param <T> 数据类型
     * @return 树的根节点
     */
    public static <T extends ITreeNode> TreeNode<T> buildTree(List<T> values) {

        TreeNode<T> root = new TreeNode<>(null, null);

        Map<String, TreeNode<T>> nodeMap = new HashMap<>();
        List<T> emptyValueList = new ArrayList<>();

        for (T value : values) {

            TreeNode<T> node = null;

            if(StringUtils.isEmpty(value.getParentId())) {

                node = new TreeNode<>(root, value);

                root.addChild(node);

            } else {

                TreeNode parentNode = nodeMap.get(value.getParentId());

                if(parentNode == null) {
                    emptyValueList.add(value);
                    node = new TreeNode<>(null, value);
                } else {
                    node = new TreeNode<>(parentNode, value);
                    parentNode.addChild(node);
                }
            }

            nodeMap.put(value.getNodeId(), node);

        }
        /*
         *
         */
        if(!emptyValueList.isEmpty()) {

            for (T value : emptyValueList) {

                TreeNode<T> node;

                TreeNode parentNode = nodeMap.get(value.getParentId());

                if(parentNode == null) {

                    node = new TreeNode<>(root, value);

                    root.addChild(node);

                } else {

                    node = nodeMap.get(value.getNodeId());

                    parentNode.addChild(node);

                    node.setParentNode(parentNode);

                }
            }
        }

        return root;
    }

}
