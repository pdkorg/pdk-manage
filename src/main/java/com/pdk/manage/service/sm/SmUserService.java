package com.pdk.manage.service.sm;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pdk.manage.common.wapper.sm.SmUserDataTableQueryArgWapper;
import com.pdk.manage.common.wapper.sm.SmUserRefDataTableQueryArgWapper;
import com.pdk.manage.dao.bd.AddressDao;
import com.pdk.manage.dao.sm.UserDao;
import com.pdk.manage.dao.sm.UserDescribeDao;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.sm.User;
import com.pdk.manage.model.sm.UserRegisterInfo;
import com.pdk.manage.service.BaseService;
import com.pdk.manage.service.coupon.CouponActivityService;
import com.pdk.manage.util.DBConst;
import com.pdk.manage.util.IdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by liuhaiming on 2015/8/14
 */
@Service
public class SmUserService extends BaseService<User> {
    private static final Logger log = LoggerFactory.getLogger(SmUserService.class);

    private UserDao getDao() {
        return (UserDao) this.dao;
    }

    @Autowired
    private AddressDao addrDao;

    @Autowired
    private UserDescribeDao describeDao;

    @Autowired
    private SmUserRegisterService regService;

    @Autowired
    private CouponActivityService couponActivityService;

    @Override
    public String getModuleCode() {
        return IdGenerator.SM_MODULE_CODE;
    }

    public User findBySourceId(String sourceId) {
        User rtn = null;
        List<User> userLst = getDao().findBySourceId(sourceId);
        if ( userLst != null && userLst.size() > 0 ) {
            rtn = userLst.get(0);
        }
        return rtn;
    }

    public int findUserCountBySourceId(String sourceId) throws BusinessException {
//        return getDao().findAllBySourceId(sourceId).size();
        return regService.findAllBySourceId(sourceId);
    }

    public User findUserBySourceId(String sourceId) throws BusinessException {
        return getDao().findUserBySourceId(sourceId);
    }

    public User findOldUserBySourceId(String sourceId) throws BusinessException {
        return getDao().findOldUserBySourceId(sourceId);
    }

    public PageInfo<User> mySelectLikePage(SmUserDataTableQueryArgWapper arg) {
        PageInfo<User> userPageInfo = null;
        PageHelper.startPage(arg.getPageNum(), arg.getLength(), arg.getOrderStr());
        Date fromDate = arg.getFromDate();
        Date toDate = arg.getToDate();

        Calendar cal = Calendar.getInstance();
        if (fromDate != null) {
            cal.setTime(fromDate);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            fromDate = cal.getTime();
        }

        if (toDate != null) {
            cal.setTime(toDate);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            toDate = cal.getTime();
        }

        if (StringUtils.isEmpty(arg.getSearchText())) {
            userPageInfo = new PageInfo<>(getDao().selectByCondition(arg.getQryType(), arg.getQryName(), fromDate, toDate, arg.getQryAddr()));
        } else {
            String orderBy = arg.getOrderStr().replace("a.full_address", "disT.address_full_address");
            PageHelper.startPage(arg.getPageNum(), arg.getLength(), orderBy);
            userPageInfo = new PageInfo<>(getDao().selectLikeByCondition(arg.getSearchText(), arg.getQryType(), arg.getQryName(), fromDate, toDate, arg.getQryAddr()));
        }

        return userPageInfo;
    }

    public PageInfo<User> mySelectRefLikePage(SmUserRefDataTableQueryArgWapper arg) {
        PageInfo<User> userPageInfo = null;
        PageHelper.startPage(arg.getPageNum(), arg.getLength(), arg.getOrderStr());
        if (StringUtils.isEmpty(arg.getSearchText())) {
            userPageInfo = new PageInfo<>( getDao().selectRef() );
        } else {
            userPageInfo = new PageInfo<>( getDao().selectLikeRef(arg.getSearchText()) );
        }

        return userPageInfo;
    }

    public User selectById(String id) {
        return getDao().selectById(id);
    }

    public User selectAllInfoById(String id) {
        User user = selectById(id);

        user.setAddressList(addrDao.selectAllByUserId(id));
        user.setDescribeList(describeDao.selectAllByUserId(id));
        return user;
    }

    public void register(User oldUser, User user) throws BusinessException {
        user.setId(oldUser.getId());
        user.setRegisterTime(oldUser.getRegisterTime());
        user.setUnRegisterTime(null);

        user.setName(StringUtils.isEmpty(user.getName()) ? oldUser.getName() : user.getName());
        user.setRealName(StringUtils.isEmpty(user.getRealName()) ? oldUser.getRealName() : user.getRealName());
        user.setSex(user.getSex() == null ? oldUser.getSex() : user.getSex());
        user.setAge(user.getAge() == null ? oldUser.getAge() : user.getAge());
        user.setPhone(StringUtils.isEmpty(user.getPhone()) ? oldUser.getPhone() : user.getPhone());
        user.setRealName(StringUtils.isEmpty(user.getRealName()) ? oldUser.getRealName() : user.getRealName());
        user.setRealName(StringUtils.isEmpty(user.getRealName()) ? oldUser.getRealName() : user.getRealName() );
        user.setHeaderImg(StringUtils.isEmpty(user.getHeaderImg()) ? oldUser.getHeaderImg() : user.getHeaderImg());
        user.setEventKey(StringUtils.isEmpty(user.getEventKey()) ? oldUser.getEventKey() : user.getEventKey());
        user.setMemo(StringUtils.isEmpty(user.getMemo()) ? oldUser.getMemo() : user.getMemo());
        user.setDr( user.getDr() == null ? DBConst.DR_NORMAL : user.getDr() );
        user.setTs( oldUser.getTs() );

        update(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public synchronized int saveRegisterInfo(User user) throws BusinessException {
        // 查找用户是否存在
        User oldUser = findOldUserBySourceId(user.getSourceId());
        if ( oldUser != null ) {
            log.info(" ======================== 用户二次关注 ==================================== ");
            if ( oldUser.getStatus() == DBConst.STATUS_ENABLE ) {
                return 0;
            }
            register(oldUser, user);
        } else {
            log.info(" ======================== 用户首关 ==================================== ");
            save(user);
            couponActivityService.sendCouponsByRule(DBConst.ACTIVITY_SEND_CODE_FST_REGISTER, user);
        }

        UserRegisterInfo regInfo = new UserRegisterInfo();
        regInfo.setUserId(user.getId());
        regInfo.setSourceId(user.getSourceId());
        regInfo.setRegisterTime(Calendar.getInstance().getTime());

        return regService.save(regInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    public synchronized int updateRegisterInfo(String sourceId) throws BusinessException {
        User dbUser = findBySourceId(sourceId);
        if (dbUser == null) {
            return 0;
        }

        dbUser.setStatus(DBConst.STATUS_UNATTENTION);
        dbUser.setUnRegisterTime( Calendar.getInstance().getTime() );

        update(dbUser);

        UserRegisterInfo regInfo = regService.getByUserId(dbUser.getId());
        if ( regInfo == null ) {
            return 0;
        }

        regInfo.setUnRegisterTime(dbUser.getUnRegisterTime());
        return regService.update(regInfo);
    }

    public List<User> findUserByIds(List<String> ids) {
        return getDao().findUserByIds(ids);
    }
}

