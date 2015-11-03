package com.pdk.manage.action.bd;

import com.github.pagehelper.PageInfo;
import com.pdk.manage.common.wapper.bd.BdShopDataTableQueryArgWapper;
import com.pdk.manage.dto.bd.ShopJson;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.bd.Shop;
import com.pdk.manage.service.bd.ShopService;
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
 * Created by liangyh on 2015/8/17.
 */
@Controller
@RequestMapping("/bd")
public class ShopAction {
    private static final Logger log = LoggerFactory.getLogger(ShopAction.class);

    @Autowired
    private ShopService service;

    @RequestMapping("/bd_shop")
    public String index() {
        return "bd/bd_shop";
    }

    @ModelAttribute
    public void getFlowType(@RequestParam(value = "id", required = false) String id, Map<String, Object> map) {
        if(StringUtils.isNotEmpty(id)) {
            map.put("shop", service.get(id));
        }
    }

    @RequestMapping(value = "/bd_shop/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> get(@PathVariable("id") String id) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", "success");
        result.put("data", service.get(id));
        return result;
    }

    @RequestMapping(value = "/bd_shop", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@Valid Shop shop, Errors errors, Map<String, Object> map) {

        Map<String, Object> result = new HashMap<>();

        if(errors.getErrorCount() > 0) {
            result.put("result", "error");
            for (FieldError err : errors.getFieldErrors()) {
                map.put("err_" + err.getField(), err.getDefaultMessage());
            }
            return result;
        }

        Shop validateshop = new Shop();
        validateshop.setCode(shop.getCode());
        if(service.isCodeRepeat(validateshop)) {
            result.put("result", "error");
            result.put("errorMsg", "编码重复，请修改后保存!");
            return result;
        }

        validateshop = new Shop();
        validateshop.setName(shop.getName());
        if(service.isCodeRepeat(validateshop)) {
            result.put("result", "error");
            result.put("errorMsg", "名称重复，请修改后保存!");
            return result;
        }

        try {
            service.save(shop);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
        }

        return result;
    }

    @RequestMapping(value = "/bd_shop", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> update(@Valid Shop shop, Errors errors, Map<String, Object> map) {

        Map<String, Object> result = new HashMap<>();

        if(errors.getErrorCount() > 0) {
            result.put("result", "error");
            for (FieldError err : errors.getFieldErrors()) {
                map.put("err_" + err.getField(), err.getDefaultMessage());
            }
            return result;
        }

        Shop validateshop = new Shop();
        validateshop.setCode(shop.getCode());
        if(service.isCodeRepeat(validateshop,shop.getId())) {
            result.put("result", "error");
            result.put("errorMsg", "编码重复，请修改后保存!");
            return result;
        }

        validateshop = new Shop();
        validateshop.setName(shop.getName());
        if(service.isCodeRepeat(validateshop,shop.getId())) {
            result.put("result", "error");
            result.put("errorMsg", "名称重复，请修改后保存!");
            return result;
        }

        try {
            service.update(shop);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/bd_shop", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> batchDelete(@RequestParam("ids") String[] ids, @RequestParam(value = "ts", required = false) Long[] ts) {

        Map<String, Object> result = new HashMap<>();

        List<Shop> shopList = new ArrayList<>();

        for (int i = 0; i < ids.length; i++) {
            Shop f = new Shop();
            f.setId(ids[i]);
            if(ts != null && ts[i] != null) {
                f.setTs(new Date(ts[i]));
            }
            shopList.add(f);
        }

        try {
            service.delete(shopList);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/bd_shop/status/{status}", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> updateStatus(@PathVariable Short status, @RequestParam("ids") String[] ids, @RequestParam(value = "ts", required = false) Long[] ts) {

        Map<String, Object> result = new HashMap<>();

        List<Shop> shopList = new ArrayList<>();

        for (int i = 0; i < ids.length; i++) {
            Shop f = new Shop();
            f.setId(ids[i]);
            if(ts != null && ts[i] != null) {
                f.setTs(new Date(ts[i]));
            }
            shopList.add(f);
        }

        try {
            if(status == DBConst.STATUS_ENABLE) {
                service.enable(shopList);
            }else {
                service.disable(shopList);
            }
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        }

        return result;

    }


    @RequestMapping("/bd_shop/table_data")
    public @ResponseBody Map<String, Object> list( BdShopDataTableQueryArgWapper arg) {

        Map<String, Object> result = new HashMap<>();

        PageInfo<Shop> pageInfo = null;
        try {
            pageInfo = service.selectLikePage(arg.getSearchText(), arg.getPageNum(), arg.getLength(), arg.getOrderStr());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        List<ShopJson> data = new ArrayList<>();

        int index = 1;

        for (Shop shop : pageInfo.getList()) {
            data.add(new ShopJson(index, shop.getId(), shop.getCode(), shop.getName(),shop.getInfo(), shop.getStatus(), shop.getMemo(), shop.getTs()));
            index++;
        }

        result.put("draw", arg.getDraw());
        result.put("recordsTotal", pageInfo.getTotal());
        result.put("recordsFiltered", pageInfo.getTotal());
        result.put("data", data);

        return result;

    }


}
