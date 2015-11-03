package com.pdk.manage.dao.sm;

import com.pdk.manage.dao.common.BaseDao;
import com.pdk.manage.model.sm.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

@Repository
public interface UserDao extends BaseDao<User> {
    List<User> selectByCondition(
              @Param("qryType") Short qryType
            , @Param("qryName") String qryName
            , @Param("fromDate") Date fromDate
            , @Param("toDate") Date toDate
            , @Param("qryAddr") String qryAddr);


    List<User> selectLikeByCondition(@Param("searchText") String searchText
            , @Param("qryType") Short qryType
            , @Param("qryName") String qryName
            , @Param("fromDate") Date fromDate
            , @Param("toDate") Date toDate
            , @Param("qryAddr") String qryAddr);

    List<User> selectRef();

    List<User> selectLikeRef(@Param("searchText") String searchText);

    List<User> findBySourceId(@Param("sourceId") String sourceId);

    List<User> findAllBySourceId(@Param("sourceId") String sourceId);

    User selectById(@Param("id") String id);

    User findUserBySourceId(@Param("sourceId") String sourceId);

    User findOldUserBySourceId(@Param("sourceId") String sourceId);

    List<User> findUserByIds(@Param("ids") List<String> ids);

    int selectNewUserCount(@Param("currDate") Date currDate);

    int selectNewUserKeepCount(@Param("currDate") Date currDate);
}