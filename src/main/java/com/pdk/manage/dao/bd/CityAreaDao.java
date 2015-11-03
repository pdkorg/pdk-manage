package com.pdk.manage.dao.bd;

import com.pdk.manage.dao.common.BaseDao;
import com.pdk.manage.model.bd.CityArea;
import com.pdk.manage.model.bd.Position;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityAreaDao extends BaseDao<CityArea> {

    List<CityArea> selectAllCity();

    List<CityArea> selectAreaByCityId(@Param("cityId") String cityId);
}