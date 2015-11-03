package com.pdk.manage.action.bd;

import com.github.pagehelper.PageInfo;
import com.pdk.manage.common.wapper.bd.BDGoodsDataTableQueryArgWapper;
import com.pdk.manage.dto.bd.GoodsEditJson;
import com.pdk.manage.dto.bd.GoodsJson;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.bd.Goods;
import com.pdk.manage.service.bd.GoodsService;
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
public class GoodsAction {
    private static final Logger log = LoggerFactory.getLogger(GoodsAction.class);

    @Autowired
    private GoodsService service;

    @RequestMapping("/bd_goods")
    public String index() {
        return "bd/bd_goods";
    }

    @ModelAttribute
    public void getFlowType(@RequestParam(value = "id", required = false) String id, Map<String, Object> map) {
        if(StringUtils.isNotEmpty(id)) {
            map.put("goods", service.get(id));
        }
    }

    @RequestMapping(value = "/bd_goods/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> get(@PathVariable("id") String id) {
        Map<String, Object> result = new HashMap<>();
        GoodsEditJson goodsJson = new GoodsEditJson(1,service.qryGoodsById(id));
        result.put("result", "success");
        result.put("data", goodsJson);
        return result;
    }

    @RequestMapping(value = "/bd_goods", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@Valid Goods goods, Errors errors, Map<String, Object> map) {

        Map<String, Object> result = new HashMap<>();

        if(errors.getErrorCount() > 0) {
            result.put("result", "error");
            for (FieldError err : errors.getFieldErrors()) {
                map.put("err_" + err.getField(), err.getDefaultMessage());
            }
            return result;
        }

//        Goods validategoods = new Goods();
//        validategoods.setCode(goods.getCode());
//        validategoods.setName(goods.getName());
//        if(service.isCodeRepeat(validategoods)) {
        if ( service.isCodeRepeat(goods) ) {
            result.put("result", "error");
            result.put("errorMsg", "编码或名称重复，请修改后保存!");
            return result;
        }

        try {
            service.save(goods);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
        }

        return result;
    }

    @RequestMapping(value = "/bd_goods", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> update(@Valid Goods goods, Errors errors, Map<String, Object> map) {

        Map<String, Object> result = new HashMap<>();

        if(errors.getErrorCount() > 0) {
            result.put("result", "error");
            for (FieldError err : errors.getFieldErrors()) {
                map.put("err_" + err.getField(), err.getDefaultMessage());
            }
            return result;
        }

//        Goods validategoods = new Goods();
//        validategoods.setCode(goods.getCode());
//        validategoods.setName(goods.getName());
//        if(service.isCodeRepeat(validategoods,goods.getId())) {
        if (service.isCodeRepeat(goods)) {
            result.put("result", "error");
            result.put("errorMsg", "编码或名称重复，请修改后保存!");
            return result;
        }

        try {
            service.update(goods);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/bd_goods", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> batchDelete(@RequestParam("ids") String[] ids, @RequestParam(value = "ts", required = false) Long[] ts) {

        Map<String, Object> result = new HashMap<>();

        List<Goods> goodsList = new ArrayList<>();

        for (int i = 0; i < ids.length; i++) {
            Goods f = new Goods();
            f.setId(ids[i]);
            if(ts != null && ts[i] != null) {
                f.setTs(new Date(ts[i]));
            }
            goodsList.add(f);
        }

        try {
            service.delete(goodsList);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/bd_goods/status/{status}", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> updateStatus(@PathVariable Short status, @RequestParam("ids") String[] ids, @RequestParam(value = "ts", required = false) Long[] ts) {

        Map<String, Object> result = new HashMap<>();

        List<Goods> goodsList = new ArrayList<>();

        for (int i = 0; i < ids.length; i++) {
            Goods f = new Goods();
            f.setId(ids[i]);
            if(ts != null && ts[i] != null) {
                f.setTs(new Date(ts[i]));
            }
            goodsList.add(f);
        }

        try {
            if(status == DBConst.STATUS_ENABLE) {
                service.enable(goodsList);
            }else {
                service.disable(goodsList);
            }
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        }

        return result;

    }


    @RequestMapping("/bd_goods/table_data")
    public @ResponseBody Map<String, Object> list( BDGoodsDataTableQueryArgWapper arg) {

        Map<String, Object> result = new HashMap<>();

        PageInfo<Goods> pageInfo = null;
        try {
            pageInfo = service.mySelectLikePage(arg.getSearchText(), arg.getPageNum(), arg.getLength(), arg.getOrderStr());
//            pageInfo = service.qryByPage(arg.getPageNum(), arg.getLength(), arg.getOrderStr(), arg.getQryCode(), arg.getQryName(),arg.getGoodstypeid());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        List<GoodsJson> data = new ArrayList<>();

        int index = 1;

        for (Goods goods : pageInfo.getList()) {
//            data.add(new GoodsJson(index, goods.getId(), goods.getCode(), goods.getName(), goods.getStatus(), goods.getMemo(), goods.getTs()));
            data.add(new GoodsJson(index,goods));
            index++;
        }

        result.put("draw", arg.getDraw());
        result.put("recordsTotal", pageInfo.getTotal());
        result.put("recordsFiltered", pageInfo.getTotal());
        result.put("data", data);
        return result;

    }


}
