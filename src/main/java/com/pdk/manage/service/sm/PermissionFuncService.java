package com.pdk.manage.service.sm;

import com.pdk.manage.dao.sm.PermissionFuncDao;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.sm.PermissionFunc;
import com.pdk.manage.service.BaseService;
import org.springframework.stereotype.Service;

import java.security.Permission;
import java.util.*;

/**
 * Created by hubo on 2015/8/7.
 */
@Service
public class PermissionFuncService extends BaseService<PermissionFunc> {
    public PermissionFuncDao getDao() {
        return (PermissionFuncDao) this.dao;
    }
    public void savePermissioFuncList(String roleId, String[] funcIds) throws BusinessException {
        List<PermissionFunc> insLst = new ArrayList<>();
        List<PermissionFunc> delLst = new ArrayList<>();

        PermissionFunc tmp = null;
        Map<String, PermissionFunc> dbFuncMap = getRoleFuncSet(roleId);
        Set<String> funcSet = new HashSet<>();
        for ( String funcId : funcIds ) {
            funcSet.add(funcId);
            if ( !dbFuncMap.containsKey(funcId) ) {
                tmp = new PermissionFunc();
                tmp.setRoleId(roleId);
                tmp.setFuncId(funcId);
                insLst.add(tmp);
                save(tmp);
            }
        }

        for ( String funcId : dbFuncMap.keySet() ) {
            if ( !funcSet.contains(funcId) ) {
                delLst.add(dbFuncMap.get(funcId));
            }
        }

        if (delLst.size() > 0) {
            delete(delLst);
        }
    }

    public Map<String, PermissionFunc> getRoleFuncSet(String roleId) {
        Map<String, PermissionFunc> dbFuncMap = new HashMap<>();
        List<PermissionFunc> pFuncs = getByRoleId(roleId);
        for (PermissionFunc pFunc : pFuncs) {
            dbFuncMap.put(pFunc.getFuncId(), pFunc);
        }

        return dbFuncMap;
    }
    public List<PermissionFunc> getByRoleId(String roleId) {
        return getDao().getByRoleId(roleId);
    }
}
