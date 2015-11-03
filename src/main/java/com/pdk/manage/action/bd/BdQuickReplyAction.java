package com.pdk.manage.action.bd;

import com.github.orderbyhelper.OrderByHelper;
import com.github.pagehelper.PageInfo;
import com.pdk.manage.common.wapper.bd.BdQuickReplyDataTableQueryArgWapper;
import com.pdk.manage.dto.bd.QuickReplyJson;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.bd.QuickReply;
import com.pdk.manage.model.flow.FlowUnit;
import com.pdk.manage.service.bd.QuickReplyService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuhaiming on 2015/9/2.
 */
@Controller
@RequestMapping("/bd")
public class BdQuickReplyAction {
    private static final Logger log = LoggerFactory.getLogger(BdQuickReplyAction.class);

    @Autowired
    private QuickReplyService quickReplyService;

    @ModelAttribute
    public void getQuickReply(@RequestParam(value = "id", required = false) String id, Map<String, Object> map) {
        if( StringUtils.isNotEmpty(id) ) {
            map.put( "quickReply", quickReplyService.get(id) );
        }
    }

    @RequestMapping("/bd_quick_reply/table_data")
    public @ResponseBody Map<String, Object> list( BdQuickReplyDataTableQueryArgWapper arg ) {
        Map<String, Object> result = new HashMap<>();
        PageInfo<QuickReply> quickReply = null;
        try {
            quickReply = quickReplyService.selectLikePage(arg.getSearchText(), arg.getPageNum(), arg.getLength(), "sort asc, " + arg.getOrderStr());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

            List<QuickReplyJson> data = new ArrayList<>();

        int index = 1;
        for (QuickReply info : quickReply.getList()) {
            data.add(new QuickReplyJson(index, info));
            index++;
        }

        result.put("draw", arg.getDraw());
        result.put("recordsTotal", data.size());
        result.put("recordsFiltered", data.size());
        result.put("data", data);

        return result;
    }

    @RequestMapping(value = "/bd_quick_reply", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> allList() {
        Map<String, Object> result = new HashMap<>();
        List<QuickReply> quickReply = null;
        try {
            OrderByHelper.orderBy("sort asc");
            quickReply = quickReplyService.selectAll();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        result.put("data", quickReply);

        return result;
    }

    @RequestMapping(value = "/bd_quick_reply", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@Valid QuickReply quickReply, Errors errors, Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();

        if (errors.getErrorCount() > 0) {
            result.put("result", "error");
            for (FieldError err : errors.getFieldErrors()) {
                map.put("err_" + err.getField(), err.getDefaultMessage());
            }
            return result;
        }

        try {
            quickReplyService.save(quickReply);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
        }

        return result;
    }

    @RequestMapping(value = "/bd_quick_reply", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> update(@Valid QuickReply quickReply, Errors errors, Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();

        if (errors.getErrorCount() > 0) {
            result.put("result", "error");
            for (FieldError err : errors.getFieldErrors()) {
                map.put("err_" + err.getField(), err.getDefaultMessage());
            }
            return result;
        }

        try {
            quickReplyService.update(quickReply);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/bd_quick_reply/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> get(@PathVariable("id") String id) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", "success");

        QuickReply quickReply = quickReplyService.get(id);
        QuickReplyJson json = null;
        if (quickReply == null) {
            json  = new QuickReplyJson();
        } else {
            json = new QuickReplyJson(1, quickReply);
        }
        result.put("data", json);
        return result;
    }
}