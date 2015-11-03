package com.pdk.manage.dao.sm;

import com.pdk.manage.dao.common.BaseDao;
import com.pdk.manage.dao.common.BusinessLogicMapper;
import com.pdk.manage.model.sm.Employee;
import com.pdk.manage.model.sm.Org;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface OrgDao extends BaseDao<Org>, BusinessLogicMapper<Org> {
    List<Org> selectByIds(@Param("orgIds") String[] orgIds);

    List<Org> selectEnableData();

    int selectByInnerCode(String incode);
}