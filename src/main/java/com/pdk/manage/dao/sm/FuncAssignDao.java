package com.pdk.manage.dao.sm;

import com.pdk.manage.model.sm.FuncAssign;
import java.util.List;

public interface FuncAssignDao {
    int deleteByPrimaryKey(String id);

    int insert(FuncAssign record);

    FuncAssign selectByPrimaryKey(String id);

    List<FuncAssign> selectAll();

    int updateByPrimaryKey(FuncAssign record);
}