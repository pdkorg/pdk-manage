package com.pdk.manage.service.sm;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pdk.manage.dao.sm.EmployeeDao;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.sm.Employee;
import com.pdk.manage.model.sm.Role;
import com.pdk.manage.service.BaseService;
import com.pdk.manage.service.bd.PositionService;
import com.pdk.manage.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hubo on 2015/8/7.
 */
@Service
public class EmployeeService extends BaseService<Employee> {
    @Autowired
    private SmRoleService smRoleService;

    @Autowired
    private SmOrgService smOrgService;

    @Autowired
    private PositionService positionService;

    /**
     * 根据员工username找到员工
     * @param username
     * @return
     */
    public Employee getEmployee(String username) {
        if(username == null) {
            return null;
        }
        Employee employee = getDao().selectByLoginName(username);
        if ( employee != null ) {
            if (StringUtils.isNotEmpty(employee.getOrgId())) {
                employee.setOrg( smOrgService.get(employee.getOrgId()) );
            }

            if (StringUtils.isNotEmpty(employee.getRoleId())) {
                employee.setRole( smRoleService.get(employee.getRoleId()) );
            }

            if (StringUtils.isNotEmpty(employee.getPositionId())) {
                employee.setPosition( positionService.get(employee.getPositionId()) );
            }

        }

        return employee;
    }

    public Employee getEmployeeById(String id) {
        return getDao().getEmployee(id);
    }

    /**
     * 修改密码
     * @param employeeId 员工ID
     * @param newPassword 新的密码
     */
    public void modifyPassword(String employeeId, String newPassword) {
        getDao().updatePasswordByPrimaryKey(employeeId, newPassword);
    }


    private EmployeeDao getDao() {
        return (EmployeeDao)this.dao;
    }

    public boolean isCodeRepeat(Employee employee) {
        int count = getDao().repeatCountSelect(employee);

        return count > 0;
    }

    public PageInfo<Employee> mySelectLikePage(String searchText, int pageNum, int pageSize, String orderBy
            , String qryCode, String qryName, String[] roleIds, String orgId) {
        PageInfo<Employee> employeePageInfo = null;
        PageHelper.startPage(pageNum, pageSize, orderBy);
        if ( StringUtils.isEmpty(searchText) ) {
            employeePageInfo = new PageInfo<>(getDao().selectByCondition(qryCode, qryName, roleIds, orgId));
        } else {
            employeePageInfo = new PageInfo<>(getDao().selectLikeByCondition(searchText, qryCode, qryName, roleIds, orgId));
        }


        return employeePageInfo;
    }

    public PageInfo<Employee> mySelectLikePageByPositionId(String searchText, int pageNum, int pageSize, String orderBy, String positionId) {
        PageInfo<Employee> employeePageInfo = null;
        PageHelper.startPage(pageNum, pageSize, orderBy);
        if ( StringUtils.isEmpty(searchText) ) {
            employeePageInfo = new PageInfo<>(getDao().mySelectPageByPositionId(positionId));
        } else {
            employeePageInfo = new PageInfo<>(getDao().mySelectLikePageByPositionId(searchText, positionId));
        }

        return employeePageInfo;
    }

    /**
     * 根据类型获取员工角色
     *
     * @param type
     * 0: 主角色
     * 1：辅助角色
     * 2：主、辅角色
     *
     * @return
     */
    public List<Role> findEmployeeRoles(String employeeId, Short type) {
        Employee employee = get(employeeId);
        List<Role> roleLst = null;
        if ( type == Employee.ROLE_FIND_TYPE_MAIN ) {
            roleLst = findMainRole(employee);
        } else if ( type == Employee.ROLE_FIND_TYPE_AUXILIARY ) {
            roleLst = findAuxiliaryRole(employee);
        } else if ( type == Employee.ROLE_FIND_TYPE_ALL ) {
            roleLst = findAllRole(employee);
        }

        return roleLst;
    }

    public List<Role> findAllRole(Employee employee) {
        List<Role> roleLst = new ArrayList<>();
        roleLst.addAll( findMainRole(employee) );
        roleLst.addAll( findAuxiliaryRole(employee) );

        return roleLst;
    }

    public List<Role> findMainRole(Employee employee) {
        List<Role> roleLst = new ArrayList<>();
        Role role = smRoleService.get(employee.getRoleId());
        if (role != null) {
            roleLst.add( role );
        }

        return roleLst;
    }

    public List<Role> findAuxiliaryRole(Employee employee) {
        List<Role> roleLst = smRoleService.selectAuxiliaryByEmployeeId(employee.getId());

        return roleLst;
    }
}
