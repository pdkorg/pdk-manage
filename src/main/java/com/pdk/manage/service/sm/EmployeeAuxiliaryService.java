package com.pdk.manage.service.sm;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pdk.manage.dao.sm.EmployeeAuxiliaryDao;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.sm.EmployeeAuxiliary;
import com.pdk.manage.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hubo on 2015/8/7.
 */
@Service
public class EmployeeAuxiliaryService extends BaseService<EmployeeAuxiliary> {
    public EmployeeAuxiliaryDao getDao() {
        return (EmployeeAuxiliaryDao) this.dao;
    }

    public PageInfo<EmployeeAuxiliary> selectAuxiliaryByEmployeeId(String searchText, int pageNum, int pageSize, String orderBy, String employeeId) {
        PageInfo<EmployeeAuxiliary> pageInfo;
        PageHelper.startPage(pageNum, pageSize, orderBy);

        if(StringUtils.isEmpty(StringUtils.trim(searchText))) {
            pageInfo = new PageInfo<>(getDao().selectAuxiliaryByEmployeeId(employeeId));
        } else {
            pageInfo = new PageInfo<>(getDao().selectLikeAuxiliaryByEmployeeId(searchText, employeeId));
        }
        return pageInfo;
    }

    public void saveAuxiliaryService(String[] roleIds, String employeeId) throws BusinessException {
        List<EmployeeAuxiliary> dbAuxiliarys = getDao().selectAuxiliaryByEmployeeId(employeeId);
        Set<String> roleIdSet = new HashSet<>();
        for ( EmployeeAuxiliary dbAuxiliary : dbAuxiliarys ) {
            roleIdSet.add(dbAuxiliary.getRoleId());
        }

        List<EmployeeAuxiliary> newAuxiliaryLst = new ArrayList<>();
        EmployeeAuxiliary newAuxiliary;
        for (String roleId : roleIds) {
            if ( roleIdSet.contains(roleId) ) {
                continue;
            }

            newAuxiliary = new EmployeeAuxiliary();

            newAuxiliary.setRoleId(roleId);
            newAuxiliary.setEmployeeId(employeeId);
            newAuxiliaryLst.add(newAuxiliary);
        }

        save(newAuxiliaryLst);
    }
}
