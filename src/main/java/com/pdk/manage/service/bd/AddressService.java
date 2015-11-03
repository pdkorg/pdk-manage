package com.pdk.manage.service.bd;

import com.pdk.manage.dao.bd.AddressDao;
import com.pdk.manage.dao.bd.CityAreaDao;
import com.pdk.manage.dao.sm.UserDao;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.bd.Address;
import com.pdk.manage.model.bd.CityArea;
import com.pdk.manage.model.bd.Position;
import com.pdk.manage.service.BaseService;
import com.pdk.manage.util.DBConst;
import com.pdk.manage.util.IdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liuhaiming on 2015/8/30.
 */
@Service
public class AddressService extends BaseService<Address> {
    private AddressDao getDao() {
        return (AddressDao) this.dao;
    }

    @Autowired
    private CityAreaService cityAreaService;

    @Override
    public String getModuleCode() {
        return IdGenerator.BD_MODULE_CODE;
    }

    public List<Address> selectByUserId(String userId, String searchText, String OrderByStr) {
        List<Address> rtnAddrLst = null;
        if (StringUtils.isEmpty(searchText)) {
            rtnAddrLst = getDao().selectByUserId(userId, OrderByStr);
        } else {
            rtnAddrLst = getDao().selectLikeByUserId(userId, searchText, OrderByStr);
        }

        return rtnAddrLst;
    }

    public List<Address> selectAllByUserId(String userId) {
        return getDao().selectAllByUserId(userId);
    }

    public Address selectDefaultByUserId(String userId) {
        return getDao().selectDefaultByUserId(userId);
    }

    public int update2Default(Address address) throws BusinessException {
        if ( DBConst.BOOLEAN_TRUE.equals(address.getIsDefault()) ) {
            getDao().updateIsDefault(address.getUserId(), address.getId());
        }

        resetFullAddress(address);
        return super.update(address);
    }

    public int saveAddress(Address address) throws BusinessException {
        Address defAddress = getDao().selectDefaultByUserId(address.getUserId());
        if ( defAddress == null ) {
            address.setIsDefault(DBConst.BOOLEAN_TRUE);
        }

        if ( DBConst.BOOLEAN_TRUE.equals(address.getIsDefault()) ) {
            getDao().updateIsDefault(address.getUserId(), address.getId());
        }

        resetFullAddress(address);
        return save(address);
    }

    private void resetFullAddress(Address address) {
        CityArea city = cityAreaService.get(address.getCityId());
        CityArea area = cityAreaService.get(address.getAreaId());

        address.setFullAddress( city.getName() + " " + area.getName() + " " + address.getStreet() );
    }
}
