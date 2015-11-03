package com.pdk.manage.service.bd;

import com.pdk.manage.dao.bd.GoodsDao;
import com.pdk.manage.dao.bd.GoodsTypeDao;
import com.pdk.manage.model.bd.Goods;
import com.pdk.manage.model.bd.GoodsType;
import com.pdk.manage.model.sm.Role;
import com.pdk.manage.service.BaseService;
import com.pdk.manage.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liangyh on 2015/8/15.
 */
@Service
public class GoodsTypeService extends BaseService<GoodsType> {
    private GoodsTypeDao getDao() {
        return (GoodsTypeDao) this.dao;
    }

    @Autowired
    private GoodsDao goodsDao;

    @Override
    public String getModuleCode() {
        return IdGenerator.BD_MODULE_CODE;
    }

    public boolean isRefrence(List<String> goodsTypeIds) {
        return goodsDao.refrencedCountSelect(goodsTypeIds, GoodsType.class.getName()) > 0;
    }
}
