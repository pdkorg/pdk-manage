package com.pdk.manage.service.sm;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pdk.manage.dao.sm.UserDao;
import com.pdk.manage.dao.sm.UserDescribeDao;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.sm.Employee;
import com.pdk.manage.model.sm.User;
import com.pdk.manage.model.sm.UserDescribe;
import com.pdk.manage.service.BaseService;
import com.pdk.manage.util.DBConst;
import com.pdk.manage.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by liuhaiming on 2015/9/1
 */
@Service
public class SmUserDescribeService extends BaseService<UserDescribe> {

    private UserDescribeDao getDao() {
        return (UserDescribeDao) this.dao;
    }

    @Autowired
    private UserDao userDao;

    @Override
    public String getModuleCode() {
        return IdGenerator.SM_MODULE_CODE;
    }

    public List<UserDescribe> selectDescribeByUserId(String userId) throws BusinessException {
        return getDao().selectAllByUserId(userId);
    }

    public int delByDescribe(String userId, String describe) throws BusinessException {
        return getDao().delByDescribe(userId, describe);
    }

    public int saveDescribe(String userId, String describe) throws BusinessException {
        User user = userDao.selectByPrimaryKey(userId);

        UserDescribe userDescribe = new UserDescribe();
        userDescribe.setUserId(userId);
        userDescribe.setSourceId(user.getSourceId());
        userDescribe.setVdescribe(describe);

        return save(userDescribe);
    }
}

