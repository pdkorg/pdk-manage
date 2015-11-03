package com.pdk.manage.dao.sm;

import com.pdk.manage.dao.common.BaseDao;
import com.pdk.manage.dao.common.BusinessLogicMapper;
import com.pdk.manage.model.sm.Employee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeDao extends BaseDao<Employee>, BusinessLogicMapper<Employee> {

    Employee selectByLoginName(String loginName);

    int updatePasswordByPrimaryKey(@Param("id") String id, @Param("newPassword") String newPassword);

    List<Employee> selectByCondition(@Param("code") String code, @Param("name") String name, @Param("roleIds") String[] roleIds, @Param("orgId") String orgId);

    List<Employee> selectLikeByCondition(@Param("searchText") String searchText, @Param("code") String code, @Param("name") String name, @Param("roleIds") String[] roleIds, @Param("orgId") String orgId);

    List<Employee> mySelectPageByPositionId(@Param("positionId") String positionId);
    List<Employee> mySelectLikePageByPositionId(@Param("searchText") String searchText, @Param("positionId") String positionId);

    Employee getEmployee(@Param("id") String id);
}