package com.pdk.manage.service;

import com.pdk.manage.dto.sm.FuncDto;
import com.pdk.manage.model.sm.Func;

import java.util.List;

/**
 * Created by liuhaiming on 2015/8/9.
 */
public interface FuncService {

    List<Func> findAllFunc();

    List<FuncDto> getFuncTreeList();

    List<FuncDto> getPermissionFuncTreeList(String roleId);

    List<Func> getPermissionFunc(List<String> roleIds);

}
