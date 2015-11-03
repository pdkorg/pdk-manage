package com.pdk.manage.service.sm;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pdk.manage.dao.sm.EmployeeReviewDao;
import com.pdk.manage.model.sm.Employee;
import com.pdk.manage.model.sm.EmployeeReview;
import com.pdk.manage.service.BaseService;
import com.pdk.manage.util.IdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by liuhaiming on 2015/8/14
 */
@Service
public class SmEmployeeReviewService extends BaseService<EmployeeReview> {

    private EmployeeReviewDao getDao() {
        return (EmployeeReviewDao) this.dao;
    }

    @Override
    public String getModuleCode() {
        return IdGenerator.SM_MODULE_CODE;
    }

    public double getEmployeeReview(String employeeId) {
        List<EmployeeReview> employeeReviews = getDao().selectByEmployeeId(employeeId);
        double result = 0;
        if (employeeReviews.size() > 0) {
            for (EmployeeReview employeeReview : employeeReviews) {
                result += employeeReview.getScore() == null ? 0 : employeeReview.getScore();
            }

            result /= employeeReviews.size();
        }

        BigDecimal decimal   =   new   BigDecimal(result);
        return decimal.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public PageInfo<EmployeeReview> selectLikePageByEmployeeId(String searchText, int pageNum, int pageSize, String orderBy, String employeeId) {
        PageHelper.startPage(pageNum, pageSize, orderBy);
        PageInfo pageInfo = null;
        if (StringUtils.isEmpty(searchText)) {
            pageInfo = new PageInfo<>(getDao().selectByEmployeeId(employeeId));
        } else {
            pageInfo = new PageInfo<>(getDao().selectLikeByEmployeeId(searchText, employeeId));
        }

        return pageInfo;
    }

    public boolean hasEmployeeReviewByOrder(String sourceId, String orderId) {
        List<EmployeeReview> employeeReviews = getDao().selectReviewByOrder(sourceId, orderId);
        return employeeReviews.size() > 0;
    }
}