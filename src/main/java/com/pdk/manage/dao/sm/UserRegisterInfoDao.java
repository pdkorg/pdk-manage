package com.pdk.manage.dao.sm;

import com.pdk.manage.dao.common.BaseDao;
import com.pdk.manage.model.sm.User;
import com.pdk.manage.model.sm.UserRegisterInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRegisterInfoDao extends BaseDao<UserRegisterInfo> {
    List<UserRegisterInfo> getByUserId(@Param("userId")String userId);

    List<UserRegisterInfo> findAllBySourceId(String sourceId);
}