package com.pdk.manage.service.sm;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pdk.manage.common.wapper.sm.SmUserDataTableQueryArgWapper;
import com.pdk.manage.dao.bd.AddressDao;
import com.pdk.manage.dao.sm.UserDao;
import com.pdk.manage.dao.sm.UserDescribeDao;
import com.pdk.manage.dao.sm.UserRegisterInfoDao;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.sm.User;
import com.pdk.manage.model.sm.UserRegisterInfo;
import com.pdk.manage.service.BaseService;
import com.pdk.manage.util.DBConst;
import com.pdk.manage.util.IdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

/**
 * Created by liuhaiming on 2015/8/14
 */
@Service
public class SmUserRegisterService extends BaseService<UserRegisterInfo> {

    private UserRegisterInfoDao getDao() {
        return (UserRegisterInfoDao) this.dao;
    }

    public String getModuleCode() {
        return IdGenerator.SM_MODULE_CODE;
    }

    public UserRegisterInfo getByUserId(String id) {
        List<UserRegisterInfo> regInfos = getDao().getByUserId(id);
        UserRegisterInfo regInfo = null;
        if ( regInfos != null && regInfos.size() > 0 ) {
            regInfo = regInfos.get(0);
        }

        return regInfo;
    }

    public int findAllBySourceId(String sourceId) {
        return getDao().findAllBySourceId(sourceId).size();
    }
}

