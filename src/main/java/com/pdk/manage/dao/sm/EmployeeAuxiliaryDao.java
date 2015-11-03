package com.pdk.manage.dao.sm;

import com.pdk.manage.dao.common.BaseDao;
import com.pdk.manage.model.sm.EmployeeAuxiliary;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeAuxiliaryDao extends BaseDao<EmployeeAuxiliary> {
    List<EmployeeAuxiliary> selectAuxiliaryByEmployeeId(@Param("employeeId") String employeeId);
    List<EmployeeAuxiliary> selectLikeAuxiliaryByEmployeeId(@Param("searchText") String searchText, @Param("employeeId") String employeeId);
}