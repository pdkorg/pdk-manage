package com.pdk.manage.service.bd;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pdk.manage.dao.bd.GoodsDao;
import com.pdk.manage.model.bd.Goods;
import com.pdk.manage.model.sm.Employee;
import com.pdk.manage.service.BaseService;
import com.pdk.manage.util.DBConst;
import com.pdk.manage.util.IdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Created by liangyh on 2015/8/15.
 */
@Service
public class GoodsService  extends BaseService<Goods> {
    @Override
    public String getModuleCode() {
        return IdGenerator.BD_MODULE_CODE;
    }


    /**
     * 单表分页查询
     *
     * @param pageNum 页数
     * @param pageSize 每页个数
     * @return 结果集
     */
    public PageInfo<Goods> qryByPage(int pageNum, int pageSize, String orderBy, String qryCode, String qryName, String goodsTypeId) throws Exception{

        PageHelper.startPage(pageNum, pageSize, orderBy);


        return new PageInfo<>(getDao().selectByCondition(qryCode, qryName, goodsTypeId));
    }


    /**
     * 根据主键查询
     * @param id
     * @return
     */
    public Goods qryGoodsById(String id) {
        return getDao().getGoods(id);
    }

    private GoodsDao getDao() {
        return (GoodsDao)this.dao;
    }

    public PageInfo<Goods> mySelectLikePage(String searchText, int pageNum, int pageSize, String orderBy) {
        PageInfo<Goods> goodsPageInfo = null;
        PageHelper.startPage(pageNum, pageSize, orderBy);
        if (StringUtils.isEmpty(searchText)) {
            goodsPageInfo = new PageInfo<>(getDao().mySelect());
        } else {
            goodsPageInfo = new PageInfo<>(getDao().mySelectLike(searchText));
        }


        return goodsPageInfo;
    }

    public boolean isCodeRepeat(Goods goods) {
        int count = getDao().repeatCountSelect(goods);
        return count > 0;
    }
}
