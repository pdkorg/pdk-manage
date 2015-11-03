package com.pdk.manage.service.sm;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pdk.manage.dao.sm.EmployeeDao;
import com.pdk.manage.dao.sm.RoleDao;
import com.pdk.manage.model.sm.Role;
import com.pdk.manage.service.BaseService;
import com.pdk.manage.util.IdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liuhaiming on 2015/8/14
 */
@Service
public class SmRoleService extends BaseService<Role> {
    @Autowired
    private EmployeeDao employeeDao;

    private RoleDao getDao() {
        return (RoleDao) this.dao;
    }

    @Override
    public String getModuleCode() {
        return IdGenerator.SM_MODULE_CODE;
    }

    public List<Role> selectByIds(List<String> roleLst) {
        return getDao().selectByIds(roleLst.toArray(new String[0]));
    }

    public boolean isCodeRepeat(Role role) {
        int count = getDao().repeatCountSelect(role);
        return count > 0;
    }

    public List<Role> selectStatusEnable() {
        return getDao().selectStatusEnable();
    }

    public boolean isRefrence(List<String> ids) {
        return employeeDao.refrencedCountSelect(ids, Role.class.getName()) > 0;
    }

    public List<Role> selectAuxiliaryByEmployeeId(String employeeId) {
        return getDao().selectAuxiliaryByEmployeeId(employeeId);
    }

    public PageInfo<Role> select4Auxiliary(String employeeId, String searchText, int pageNum, int pageSize, String orderBy) {
        PageInfo<Role> pageInfo = null;
        PageHelper.startPage(pageNum, pageSize, orderBy);

        if(StringUtils.isEmpty(StringUtils.trim(searchText))) {
            pageInfo = new PageInfo<>(getDao().select4Auxiliary(employeeId));
        } else {
            pageInfo = new PageInfo<>(getDao().selectLike4Auxiliary(searchText, employeeId));
        }
        return pageInfo;
    }
}

