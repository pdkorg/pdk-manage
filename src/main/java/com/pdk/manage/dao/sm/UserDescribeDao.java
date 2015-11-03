package com.pdk.manage.dao.sm;

import com.pdk.manage.dao.common.BaseDao;
import com.pdk.manage.model.sm.UserDescribe;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDescribeDao extends BaseDao<UserDescribe> {
    List<UserDescribe> selectAllByUserId(@Param("userId") String userId);

    int delByDescribe(@Param("userId") String userId, @Param("describe") String describe);
}