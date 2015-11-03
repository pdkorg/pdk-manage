package com.pdk.manage.dao;

import com.pdk.manage.dao.common.BaseDao;
import com.pdk.manage.model.sm.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MessageDao extends BaseDao<Message> {
    List<Message> selectByEmployeeId(@Param("employeeId") String employeeId);

    List<Message> selectNewMessageByEmployeeId(@Param("employeeId") String employeeId);
}
