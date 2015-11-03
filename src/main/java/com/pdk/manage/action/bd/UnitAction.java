package com.pdk.manage.action.bd;

import com.github.pagehelper.PageInfo;
import com.pdk.manage.common.wapper.bd.BdUnitDataTableQueryArgWapper;
import com.pdk.manage.dto.bd.BdUnitJson;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.bd.Unit;
import com.pdk.manage.service.bd.UnitService;
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
 * Created by liangyh on 2015/8/10.
 */
@Controller
@RequestMapping("/bd")
public class UnitAction {

    private static final Logger log = LoggerFactory.getLogger(UnitAction.class);
    @Autowired
    private UnitService unitService;


    @RequestMapping("/bd_unit")
    public String index() {
       return "/bd/bd_unit";
    }



    @RequestMapping(value = "/bd_unit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@Valid Unit unit, Errors errors, Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();

        if(errors.getErrorCount() > 0) {
            result.put("result", "error");
            for (FieldError err : errors.getFieldErrors()) {
                map.put("err_" + err.getField(), err.getDefaultMessage());
            }
            return result;
        }
        Unit validateUnit = new Unit();
        validateUnit.setCode(unit.getCode());
        if(unitService.isCodeRepeat(validateUnit)) {
            result.put("result", "error");
            result.put("errorMsg", "编码重复，请修改后保存!");
            return result;
        }

        validateUnit = new Unit();
        validateUnit.setName(unit.getName());
        if(unitService.isCodeRepeat(validateUnit)) {
            result.put("result", "error");
            result.put("errorMsg", "名称重复，请修改后保存!");
            return result;
        }

        try {
            unitService.save(unit);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
        }

        return result;
    }


    @RequestMapping("/bd_unit/table_data")
    public @ResponseBody Map<String, Object> list( BdUnitDataTableQueryArgWapper arg) {

        Map<String, Object> result = new HashMap<>();

        PageInfo<Unit> pageInfo = null;
        try {
            pageInfo = unitService.selectLikePage(arg.getSearchText(), arg.getPageNum(), arg.getLength(), arg.getOrderStr());
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<BdUnitJson> data = new ArrayList<>();

        int index = 1;

        for (Unit unit : pageInfo.getList()) {
            data.add(new BdUnitJson(index, unit.getId(), unit.getCode(), unit.getName(), unit.getStatus(), unit.getMemo(), unit.getTs()));
            index++;
        }

        result.put("draw", arg.getDraw());
        result.put("recordsTotal", pageInfo.getTotal());
        result.put("recordsFiltered", pageInfo.getTotal());
        result.put("data", data);

        return result;

    }

    @RequestMapping(value = "/bd_unit", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> update(@Valid Unit unit, Errors errors, Map<String, Object> map) {

        Map<String, Object> result = new HashMap<>();

        if(errors.getErrorCount() > 0) {
            result.put("result", "error");
            for (FieldError err : errors.getFieldErrors()) {
                map.put("err_" + err.getField(), err.getDefaultMessage());
            }
            return result;
        }
        Unit validateUnit = new Unit();
        validateUnit.setCode(unit.getCode());
        if(unitService.isCodeRepeat(validateUnit,unit.getId())) {
            result.put("result", "error");
            result.put("errorMsg", "编码重复，请修改后保存!");
            return result;
        }

        validateUnit = new Unit();
        validateUnit.setName(unit.getName());
        if(unitService.isCodeRepeat(validateUnit,unit.getId())) {
            result.put("result", "error");
            result.put("errorMsg", "名称重复，请修改后保存!");
            return result;
        }

        try {
            unitService.update(unit);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        }

        return result;
    }


    @ModelAttribute
    public void getBdUnit(@RequestParam(value = "id", required = false) String id, Map<String, Object> map) {
        if(StringUtils.isNotEmpty(id)) {
            map.put("unit", unitService.get(id));
        }
    }

    @RequestMapping(value = "/bd_unit/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> get(@PathVariable("id") String id) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", "success");
        result.put("data", unitService.get(id));
        return result;
    }


    @RequestMapping(value = "/bd_unit", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> batchDelete(@RequestParam("ids") String[] ids, @RequestParam(value = "ts", required = false) Long[] ts) {

        Map<String, Object> result = new HashMap<>();

        List<Unit> unitList = new ArrayList<>();

        for (int i = 0; i < ids.length; i++) {
            Unit unit = new Unit();
            unit.setId(ids[i]);
            if(ts != null && ts[i] != null) {
                unit.setTs(new Date(ts[i]));
            }
            unitList.add(unit);
        }

        try {
            unitService.delete(unitList);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/bd_unit/status/{status}", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> updateStatus(@PathVariable Short status, @RequestParam("ids") String[] ids, @RequestParam(value = "ts", required = false) Long[] ts) {

        Map<String, Object> result = new HashMap<>();

        List<Unit> shopList = new ArrayList<>();

        for (int i = 0; i < ids.length; i++) {
            Unit f = new Unit();
            f.setId(ids[i]);
            if(ts != null && ts[i] != null) {
                f.setTs(new Date(ts[i]));
            }
            shopList.add(f);
        }

        try {
            if(status == DBConst.STATUS_ENABLE) {
                unitService.enable(shopList);
            }else {
                unitService.disable(shopList);
            }
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        }

        return result;

    }

}
