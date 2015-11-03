package com.pdk.manage.dao.order;

import com.pdk.manage.dao.common.BaseDao;
import com.pdk.manage.model.order.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderTipMapper extends BaseDao<OrderTip> {
    List<OrderTip> selectByEmployeeId(@Param("employeeId") String employeeId);
}