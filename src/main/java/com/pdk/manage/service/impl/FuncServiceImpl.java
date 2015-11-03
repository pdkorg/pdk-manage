package com.pdk.manage.service.impl;

import com.pdk.manage.common.tree.TreeNode;
import com.pdk.manage.dao.sm.FuncDao;
import com.pdk.manage.dto.sm.FuncDto;
import com.pdk.manage.model.sm.Employee;
import com.pdk.manage.model.sm.Func;
import com.pdk.manage.model.sm.Role;
import com.pdk.manage.service.FuncService;
import com.pdk.manage.service.sm.EmployeeService;
import com.pdk.manage.util.TreeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by liuhaiming on 2015/8/9
 */
@Service
public class FuncServiceImpl implements FuncService {

    private static Logger log = LoggerFactory.getLogger(FuncServiceImpl.class);

    @Autowired
    private FuncDao funcDao;

    @Override
    public List<Func> findAllFunc() {
        return funcDao.selectAll();
    }

    private List<FuncDto> getFuncTreeList(List<Func> funcList) {
        log.info("PermissionFunc Func list : ", funcList);

        List<FuncDto> funcDtoList = new ArrayList<>(funcList.size());


        List<FuncDto> result = new ArrayList<>();

        for (Func func : funcList) {
            FuncDto funcDto = new FuncDto(func);
            funcDtoList.add(funcDto);
        }

        TreeNode<FuncDto> root = TreeUtil.buildTree(funcDtoList);

        for(TreeNode<FuncDto> node : root.getChilds()) {
            FuncDto func = node.getValue();
            result.add(func);
            for (TreeNode<FuncDto> childNode : node.getChilds()) {
                func.addChildFunc(childNode.getValue());
            }
        }

        log.info("Func Tree list : ", result);

        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<FuncDto> getFuncTreeList() {
        List<Func> funcList = findAllFunc();
        return getFuncTreeList(funcList);
    }

    @Autowired
    private HashSet<String> excludeURISet;
    @Autowired
    private HashSet<String> folderURISet;
    @Autowired
    private String adminRoleId;

    @Autowired
    private EmployeeService employeeService;

    @Override
    @SuppressWarnings("unchecked")
    public List<FuncDto> getPermissionFuncTreeList(String employeeId) {
        List<Role> roleLst = employeeService.findEmployeeRoles(employeeId, Employee.ROLE_FIND_TYPE_ALL);
        if (roleLst.size() == 0) {
            return null;
        }

        List<String> roleIds = new ArrayList<>();
        for ( Role role : roleLst ) {
            roleIds.add(role.getId());
            if ( adminRoleId.equals(role.getId()) ) {
                return getFuncTreeList();
            }
        }

        List<Func> funcList = getPermissionFunc(roleIds);
        List<FuncDto> permissionFuncs = getFuncTreeList(funcList);
        for (int idx = permissionFuncs.size()-1; idx >= 0; idx--) {
            // 过滤掉所有节点都没有权限的模块
            if (permissionFuncs.get(idx).getChildren() == null || permissionFuncs.get(idx).getChildren().size() == 0) {
                if ( folderURISet.contains( permissionFuncs.get(idx).getCode() ) ) { // 首页不能过滤
                    permissionFuncs.remove( idx );
                }
            }
        }

        return permissionFuncs;
    }

    @Override
    public List<Func> getPermissionFunc(List<String> roleIds) {
        List<String> funcCodes = new ArrayList<>();
        for ( String funcCode : folderURISet ) {
            funcCodes.add(funcCode);
        }
        return funcDao.selectPermissionFunc(roleIds, funcCodes);
    }

}
