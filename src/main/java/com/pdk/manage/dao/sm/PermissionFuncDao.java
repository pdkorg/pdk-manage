package com.pdk.manage.dao.sm;

import com.pdk.manage.dao.common.BaseDao;
import com.pdk.manage.model.sm.PermissionFunc;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface PermissionFuncDao extends BaseDao<PermissionFunc> {
    List<PermissionFunc> getByRoleId(@Param("roleId") String roleId);
}