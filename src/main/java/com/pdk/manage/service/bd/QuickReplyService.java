package com.pdk.manage.service.bd;

import com.pdk.manage.dao.bd.AddressDao;
import com.pdk.manage.dao.bd.QuickReplyDao;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.bd.Address;
import com.pdk.manage.model.bd.QuickReply;
import com.pdk.manage.service.BaseService;
import com.pdk.manage.util.DBConst;
import com.pdk.manage.util.IdGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liuhaiming on 2015/8/30.
 */
@Service
public class QuickReplyService extends BaseService<QuickReply> {
    private QuickReplyDao getDao() {
        return (QuickReplyDao) this.dao;
    }

    @Override
    public String getModuleCode() {
        return IdGenerator.BD_MODULE_CODE;
    }
}
