package com.pdk.manage.dao.sm;

import com.pdk.manage.dao.common.BaseDao;
import com.pdk.manage.model.sm.EmployeeReview;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeReviewDao extends BaseDao<EmployeeReview> {
    List<EmployeeReview> selectByEmployeeId(@Param("employeeId") String employeeId);
    List<EmployeeReview> selectLikeByEmployeeId(@Param("searchText") String searchText, @Param("employeeId") String employeeId);

    List<EmployeeReview> selectReviewByOrder(@Param("sourceId") String sourceId, @Param("orderId") String orderId);
}
