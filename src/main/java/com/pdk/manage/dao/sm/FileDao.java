package com.pdk.manage.dao.sm;

import com.pdk.manage.model.sm.File;
import java.util.List;

public interface FileDao {
    int deleteByPrimaryKey(String id);

    int insert(File record);

    File selectByPrimaryKey(String id);

    List<File> selectAll();

    int updateByPrimaryKey(File record);
}