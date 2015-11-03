package com.pdk.manage.service.bd;

import com.pdk.manage.model.bd.Unit;
import com.pdk.manage.service.BaseService;
import com.pdk.manage.util.IdGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liangyh on 2015/8/11.
 */

@Service
public class UnitService extends BaseService<Unit> {

    @Override
    public String getModuleCode() {
        return IdGenerator.BD_MODULE_CODE;
    }
}
