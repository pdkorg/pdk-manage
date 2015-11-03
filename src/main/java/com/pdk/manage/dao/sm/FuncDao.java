package com.pdk.manage.dao.sm;

import com.pdk.manage.model.sm.Func;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncDao {
    int deleteByPrimaryKey(String id);

    int insert(Func record);

    Func selectByPrimaryKey(String id);

    List<Func> selectAll();

    int updateByPrimaryKey(Func record);

    List<Func> selectPermissionFunc(@Param("roleIds") List<String> roleIds, @Param("folderFunc") List<String> folderFunc);
}