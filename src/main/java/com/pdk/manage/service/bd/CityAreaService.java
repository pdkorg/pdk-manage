package com.pdk.manage.service.bd;

import com.pdk.manage.dao.bd.AddressDao;
import com.pdk.manage.dao.bd.CityAreaDao;
import com.pdk.manage.model.bd.Address;
import com.pdk.manage.model.bd.CityArea;
import com.pdk.manage.service.BaseService;
import com.pdk.manage.util.IdGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liuhaiming on 2015/8/31.
 */
@Service
public class CityAreaService extends BaseService<CityArea> {
    private CityAreaDao getDao() {
        return (CityAreaDao) this.dao;
    }

    @Override
    public String getModuleCode() {
        return IdGenerator.BD_MODULE_CODE;
    }

    public List<CityArea> selectAllCity() {
        return getDao().selectAllCity();
    }

    public List<CityArea> selectAreaByCityId(String cityId) {
        return getDao().selectAreaByCityId(cityId);
    }
}
