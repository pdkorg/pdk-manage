package com.pdk.manage.dao.bd;

import com.pdk.manage.dao.common.BaseDao;
import com.pdk.manage.model.bd.Address;
import com.pdk.manage.model.bd.CityArea;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressDao extends BaseDao<Address> {

    List<Address> selectByUserId(@Param("userId") String userId, @Param("OrderByStr") String OrderByStr);

    List<Address> selectLikeByUserId(@Param("userId") String userId, @Param("searchText") String searchText, @Param("OrderByStr") String OrderByStr);

    List<Address> selectAllByUserId(@Param("userId") String userId);

    void updateIsDefault(@Param("userId") String userId, @Param("id") String id);

    Address selectDefaultByUserId(@Param("userId") String userId);
}