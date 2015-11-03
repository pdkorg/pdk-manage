package com.pdk.manage.action.bd;

import com.github.pagehelper.PageInfo;
import com.pdk.manage.common.wapper.bd.BdUnitDataTableQueryArgWapper;
import com.pdk.manage.dto.bd.GoodsTypeJson;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.bd.GoodsType;
import com.pdk.manage.service.bd.GoodsTypeService;
import com.pdk.manage.util.DBConst;
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
 * Created by liangyh on 2015/8/15.
 */
@Controller
@RequestMapping("/bd")
public class GoodsTypeAction {


    private static final Logger log = LoggerFactory.getLogger(GoodsTypeAction.class);

    @Autowired
    private GoodsTypeService service;

    @RequestMapping("/bd_goodsType")
    public String index() {
        return "bd/bd_goodsType";
    }

    @ModelAttribute
    public void getFlowType(@RequestParam(value = "id", required = false) String id, Map<String, Object> map) {
        if(StringUtils.isNotEmpty(id)) {
            map.put("goodsType", service.get(id));
        }
    }

    @RequestMapping(value = "/bd_goodsType/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> get(@PathVariable("id") String id) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", "success");
        result.put("data", service.get(id));
        return result;
    }

    @RequestMapping(value = "/bd_goodsType", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@Valid GoodsType goodsType, Errors errors, Map<String, Object> map) {

        Map<String, Object> result = new HashMap<>();

        if(errors.getErrorCount() > 0) {
            result.put("result", "error");
            for (FieldError err : errors.getFieldErrors()) {
                map.put("err_" + err.getField(), err.getDefaultMessage());
            }
            return result;
        }

        GoodsType validategoodstype = new GoodsType();
        validategoodstype.setCode(goodsType.getCode());
        if(service.isCodeRepeat(validategoodstype)) {
            result.put("result", "error");
            result.put("errorMsg", "编码重复，请修改后保存!");
            return result;
        }

        validategoodstype = new GoodsType();
        validategoodstype.setName(goodsType.getName());
        if(service.isCodeRepeat(validategoodstype)) {
            result.put("result", "error");
            result.put("errorMsg", "名称重复，请修改后保存!");
            return result;
        }

        try {
            service.save(goodsType);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
        }

        return result;
    }

    @RequestMapping(value = "/bd_goodsType", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> update(@Valid GoodsType goodsType, Errors errors, Map<String, Object> map) {

        Map<String, Object> result = new HashMap<>();

        if(errors.getErrorCount() > 0) {
            result.put("result", "error");
            for (FieldError err : errors.getFieldErrors()) {
                map.put("err_" + err.getField(), err.getDefaultMessage());
            }
            return result;
        }

        GoodsType validategoodstype = new GoodsType();
        validategoodstype.setCode(goodsType.getCode());
        if(service.isCodeRepeat(validategoodstype,goodsType.getId())) {
            result.put("result", "error");
            result.put("errorMsg", "编码重复，请修改后保存!");
            return result;
        }

        validategoodstype = new GoodsType();
        validategoodstype.setName(goodsType.getName());
        if(service.isCodeRepeat(validategoodstype,goodsType.getId())) {
            result.put("result", "error");
            result.put("errorMsg", "名称重复，请修改后保存!");
            return result;
        }

        try {
            service.update(goodsType);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/bd_goodsType", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> batchDelete(@RequestParam("ids") String[] ids, @RequestParam(value = "ts", required = false) Long[] ts) {

        Map<String, Object> result = new HashMap<>();

        List<GoodsType> goodsTypeList = new ArrayList<>();
        List<String> goodsTypeIds = new ArrayList<>();

        for (int i = 0; i < ids.length; i++) {
            GoodsType f = new GoodsType();
            f.setId(ids[i]);
            if(ts != null && ts[i] != null) {
                f.setTs(new Date(ts[i]));
            }
            goodsTypeList.add(f);
            goodsTypeIds.add(ids[i]);
        }

        try {
            boolean isRefed = service.isRefrence(goodsTypeIds);
            if ( isRefed ) {
                result.put("result", "error");
                result.put("errorMsg", "商品种类被商品引用，请修改后保存!");
                return result;
            }

            service.delete(goodsTypeList);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/bd_goodsType/status/{status}", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> updateStatus(@PathVariable Short status, @RequestParam("ids") String[] ids, @RequestParam(value = "ts", required = false) Long[] ts) {

        Map<String, Object> result = new HashMap<>();

        List<GoodsType> goodsTypeList = new ArrayList<>();

        for (int i = 0; i < ids.length; i++) {
            GoodsType f = new GoodsType();
            f.setId(ids[i]);
            if(ts != null && ts[i] != null) {
                f.setTs(new Date(ts[i]));
            }
            goodsTypeList.add(f);
        }

        try {
            if(status == DBConst.STATUS_ENABLE) {
                service.enable(goodsTypeList);
            }else {
                service.disable(goodsTypeList);
            }
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        }

        return result;

    }


    @RequestMapping("/bd_goodsType/table_data")
    public @ResponseBody Map<String, Object> list( BdUnitDataTableQueryArgWapper arg) {

        Map<String, Object> result = new HashMap<>();

        PageInfo<GoodsType> pageInfo = null;
        try {
            pageInfo = service.selectLikePage(arg.getSearchText(), arg.getPageNum(), arg.getLength(), arg.getOrderStr());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        List<GoodsTypeJson> data = new ArrayList<>();

        int index = 1;

        for (GoodsType goodsType : pageInfo.getList()) {
            data.add(new GoodsTypeJson(index, goodsType));
            index++;
        }

        result.put("draw", arg.getDraw());
        result.put("recordsTotal", pageInfo.getTotal());
        result.put("recordsFiltered", pageInfo.getTotal());
        result.put("data", data);

        return result;

    }


}
