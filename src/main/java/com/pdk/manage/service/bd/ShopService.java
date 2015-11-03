package com.pdk.manage.service.bd;

import com.pdk.manage.model.bd.Shop;
import com.pdk.manage.service.BaseService;
import com.pdk.manage.util.IdGenerator;
import org.springframework.stereotype.Service;

/**
 * Created by liangyh on 2015/8/17.
 */
@Service
public class ShopService extends BaseService<Shop> {
    @Override
    public String getModuleCode() {
        return IdGenerator.BD_MODULE_CODE;
    }

}
