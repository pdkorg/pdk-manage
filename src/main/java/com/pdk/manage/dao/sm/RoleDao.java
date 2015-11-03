package com.pdk.manage.dao.sm;

import com.pdk.manage.dao.common.BaseDao;
import com.pdk.manage.dao.common.BusinessLogicMapper;
import com.pdk.manage.model.sm.Employee;
import com.pdk.manage.model.sm.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface RoleDao extends BaseDao<Role>, BusinessLogicMapper<Role> {
    List<Role> selectByIds(@Param("roleIds") String[] roleIds);

    List<Role> selectStatusEnable();

    List<Role> select4Auxiliary(@Param("employeeId") String employeeId);

    List<Role> selectLike4Auxiliary(@Param("searchText") String searchText, @Param("employeeId") String employeeId);

    List<Role> selectAuxiliaryByEmployeeId(@Param("employeeId") String employeeId);
}
