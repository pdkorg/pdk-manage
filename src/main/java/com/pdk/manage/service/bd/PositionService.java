package com.pdk.manage.service.bd;

import com.pdk.manage.model.bd.Position;
import com.pdk.manage.model.bd.Shop;
import com.pdk.manage.service.BaseService;
import com.pdk.manage.util.IdGenerator;
import org.springframework.stereotype.Service;

/**
 * Created by liuhaiming on 2015/8/29.
 */
@Service
public class PositionService extends BaseService<Position> {
    @Override
    public String getModuleCode() {
        return IdGenerator.BD_MODULE_CODE;
    }

}
