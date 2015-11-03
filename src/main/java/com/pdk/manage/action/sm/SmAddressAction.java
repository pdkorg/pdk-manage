package com.pdk.manage.action.sm;

import com.pdk.manage.common.wapper.bd.BdAddressDataTableQueryArgWapper;
import com.pdk.manage.dto.bd.AddressJson;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.bd.Address;
import com.pdk.manage.model.sm.User;
import com.pdk.manage.service.bd.AddressService;
import com.pdk.manage.service.sm.SmUserService;
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
 * Created by liuhaiming on 2015/8/30.
 */
@Controller
@RequestMapping("/sm")
public class SmAddressAction {
    private static final Logger log = LoggerFactory.getLogger(SmAddressAction.class);

    @Autowired
    private AddressService addressService;

    @Autowired
    private SmUserService smUserService;

    @ModelAttribute
    public void getAddress(@RequestParam(value = "id", required = false) String id, Map<String, Object> map) {
        if( StringUtils.isNotEmpty(id) ) {
            map.put( "address", addressService.get(id) );
        }
    }


    @RequestMapping("/sm_address/table_data/{userId}")
    public @ResponseBody Map<String, Object> list(@PathVariable("userId") String userId, BdAddressDataTableQueryArgWapper arg ) {
        Map<String, Object> result = new HashMap<>();
        List<Address> addresses = null;
        try {
            addresses = addressService.selectByUserId(userId, arg.getSearchText(), arg.getOrderStr());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        List<AddressJson> data = new ArrayList<>();

        int index = 1;
        for (Address address : addresses) {
            data.add(new AddressJson(index, address));
            index++;
        }

        result.put("draw", arg.getDraw());
        result.put("recordsTotal", data.size());
        result.put("recordsFiltered", data.size());
        result.put("data", data);

        return result;
    }

    @RequestMapping(value = "/sm_address", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@Valid Address address, Errors errors, Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();

        if (errors.getErrorCount() > 0) {
            result.put("result", "error");
            for (FieldError err : errors.getFieldErrors()) {
                map.put("err_" + err.getField(), err.getDefaultMessage());
            }
            return result;
        }

        try {
            addressService.saveAddress(address);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
        }

        return result;
    }

    @RequestMapping(value = "/sm_address", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> update(@Valid Address address, Errors errors, Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();

        if (errors.getErrorCount() > 0) {
            result.put("result", "error");
            for (FieldError err : errors.getFieldErrors()) {
                map.put("err_" + err.getField(), err.getDefaultMessage());
            }
            return result;
        }

        try {
            addressService.update2Default(address);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/sm_address/set_default_address/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> setDefaultAddress(@PathVariable("id") String id) {
        Map<String, Object> result = new HashMap<>();

        try {
            Address address = addressService.get(id);
            address.setIsDefault(DBConst.BOOLEAN_TRUE);

            addressService.update2Default(address);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/sm_address", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> batchDelete(@RequestParam("ids") String[] ids, @RequestParam(value = "ts", required = false) Long[] ts) {

        Map<String, Object> result = new HashMap<>();

        List<Address> addressList = new ArrayList<>();

        for (int i = 0; i < ids.length; i++) {
            Address address = new Address();
            address.setId(ids[i]);
            if(ts != null && ts[i] != null) {
                address.setTs(new Date(ts[i]));
            }
            addressList.add(address);
        }

        try {
            Address delAddress = addressService.get(ids[0]);
            Address newDefAddr = null;
            List<Address> addrLst = addressService.selectAllByUserId(delAddress.getUserId());
            Set<String> idSet = new HashSet<>();
            for ( Address addr : addressList ) {
                idSet.add(addr.getId());
            }

            boolean isDelDefault = false;
            for ( Address addr : addrLst ) {
                if (idSet.contains(addr.getId()) ) {
                    isDelDefault = true;
                } else {
                    newDefAddr = addr;
                }

                if (isDelDefault && newDefAddr != null) {
                    break;
                }
            }

            addressService.delete(addressList);
            if (newDefAddr != null) {
                newDefAddr.setIsDefault(DBConst.BOOLEAN_TRUE);
                addressService.update(newDefAddr);
            }

            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("result", "error");
            result.put("errorMsg", e.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/sm_address/{selectUserId}/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> get(@PathVariable("selectUserId") String selectUserId, @PathVariable("id") String id) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", "success");

        Address address = addressService.get(id);
        AddressJson json = null;
        if (address == null) {
            User user = smUserService.get(selectUserId);
            json  = new AddressJson();
            // 新增时，设置默认值
            json.setReceiver(user.getRealName());
            json.setPhone(user.getPhone());
            json.setCityId(DBConst.CITY_AREA_BJ);
            json.setAreaId(DBConst.CITY_AREA_DC);
        } else {
            json = new AddressJson(1, address);
        }
        result.put("data", json);
        return result;
    }
}