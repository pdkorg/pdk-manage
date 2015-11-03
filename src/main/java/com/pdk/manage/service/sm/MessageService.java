package com.pdk.manage.service.sm;

import com.pdk.manage.dao.MessageDao;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.sm.Employee;
import com.pdk.manage.model.sm.Message;
import com.pdk.manage.service.BaseService;
import com.pdk.manage.util.CommonConst;
import com.pdk.manage.util.DBConst;
import com.pdk.manage.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Created by liuhaiming on 2015/9/9
 */
@Service
public class MessageService extends BaseService<Message> {
    private MessageDao getDao() {
        return (MessageDao) this.dao;
    }

    @Autowired
    private HttpSession session;

    @Override
    public String getModuleCode() {
        return IdGenerator.SM_MODULE_CODE;
    }

    public List<Message> selectByEmployeeId(String employeeId) {
        return getDao().selectByEmployeeId(employeeId);
    }

    public List<Message> selectNewMessageByEmployeeId(String id) throws BusinessException {
        List<Message> newMsgs = getDao().selectNewMessageByEmployeeId(id);
        for ( Message msg : newMsgs ) {
            msg.setIsLoad(DBConst.BOOLEAN_TRUE);
            update(msg);
        }

        return newMsgs;
    }

    /**
     * 消息推送
     * @param msg
     * @return
     * @throws BusinessException
     */
    public int pushMessage(Message msg) throws BusinessException {
        Employee emp = (Employee) session.getAttribute(CommonConst.SESSION_ATTR_KEY_LOGIN_EMPLOYEE);
        // 设置推送消息默认值
        msg.setIsRead(DBConst.BOOLEAN_FALSE);
        msg.setIsLoad(DBConst.BOOLEAN_FALSE);
        msg.setFrEmployeeId(emp.getId());
        msg.setType(DBConst.MSG_TYPE_DB);

        return this.save(msg);
    }
}

