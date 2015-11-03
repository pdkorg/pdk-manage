package com.pdk.manage.action.sm;

import com.github.pagehelper.PageInfo;
import com.pdk.manage.common.wapper.sm.SmEmployeeReviewDataTableQueryArgWapper;
import com.pdk.manage.dto.sm.EmployeeReviewJson;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.sm.EmployeeReview;
import com.pdk.manage.model.sm.User;
import com.pdk.manage.service.sm.SmEmployeeReviewService;
import com.pdk.manage.service.sm.SmUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * Created by liuhm on 2015/9/18
 */
@Controller
@RequestMapping("/sm")
public class SmEmployeeReviewAction {
    private static final Logger log = LoggerFactory.getLogger(SmEmployeeReviewAction.class);

    @Autowired
    SmUserService userService;

    @Autowired
    private SmEmployeeReviewService employeeReviewService;

    @ModelAttribute
    public void getEmployeeReview(@RequestParam(value = "id", required = false) String id, Map<String, Object> map) {
        if (StringUtils.isNotEmpty(id)) {
            map.put("employeeReview", employeeReviewService.get(id));
        }
    }

    @RequestMapping("/employee_review/detail_table_data/{employeeId}")
    public @ResponseBody Map<String, Object> detailReviewList( SmEmployeeReviewDataTableQueryArgWapper arg, @PathVariable String employeeId ) {

        Map<String, Object> result = new HashMap<>();

        PageInfo<EmployeeReview> pageInfo = null;
        try {
            pageInfo = employeeReviewService.selectLikePageByEmployeeId(arg.getSearchText(), arg.getPageNum(), arg.getLength(), arg.getOrderStr(), employeeId);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        int index = 1;
        List<EmployeeReviewJson> jsons = new ArrayList<>();
        for (EmployeeReview data : pageInfo.getList()) {
            jsons.add( new EmployeeReviewJson(index, data) );
            index++;
        }

        result.put("draw", arg.getDraw());
        result.put("recordsTotal", pageInfo.getTotal());
        result.put("recordsFiltered", pageInfo.getTotal());
        result.put("data", jsons);

        return result;

    }

}