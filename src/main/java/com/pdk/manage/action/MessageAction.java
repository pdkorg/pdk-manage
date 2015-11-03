package com.pdk.manage.action;

import com.pdk.manage.dto.InboxJson;
import com.pdk.manage.model.sm.Employee;
import com.pdk.manage.model.sm.Message;
import com.pdk.manage.service.sm.MessageService;
import com.pdk.manage.util.CommonConst;
import com.pdk.manage.util.DBConst;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuhaiming on 2015/9/9.
 */
@Controller
@RequestMapping("/msg")
public class MessageAction {
    private static Logger log = LoggerFactory.getLogger(MessageAction.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private HttpSession session;

    @ModelAttribute
    public void getMessage(@RequestParam(value = "id", required = false) String id, Map<String, Object> map) {
        if( StringUtils.isNotEmpty(id) ) {
            map.put( "message", messageService.get(id) );
        }
    }

    @RequestMapping(value="/msg_datas")
    @ResponseBody
    public Map<String, Object> list() {
        return loadMessageList();
    }

    @RequestMapping(value="/msg_datas", method = RequestMethod.PUT)
     @ResponseBody
     public Map<String, Object> updRead(@RequestParam("messageId") String messageId) {
        Map<String, Object> result = new HashMap<>();

        try {
            Message msg = messageService.get(messageId);
            msg.setIsRead(DBConst.BOOLEAN_TRUE);
            messageService.update(msg);
            result = loadMessageList();
        } catch (Exception e) {
            result.put("result", "error");
            result.put("errMsg", e.getMessage());
            log.error(e.getMessage(), e);
        }

        return result;
    }

    private Map<String, Object> loadMessageList() {
        Map<String, Object> result = new HashMap<>();

        try {
            Employee employee = (Employee) session.getAttribute(CommonConst.SESSION_ATTR_KEY_LOGIN_EMPLOYEE);
            List<Message> msgs = messageService.selectByEmployeeId(employee.getId());

            List<InboxJson> jsons = new ArrayList<>();
            List<InboxJson> newJsons = new ArrayList<>();

            for (Message msg : msgs) {
                jsons.add( new InboxJson(msg) );
            }

            List<Message> newMsgs = messageService.selectNewMessageByEmployeeId(employee.getId());
            for (Message msg : newMsgs) {
                newJsons.add( new InboxJson(msg) );
            }

            result.put("messages", jsons);
            result.put("newMessages", newJsons);
            result.put("result", "success");
        } catch (Exception e) {
            result.put("result", "error");
            result.put("errMsg", e.getMessage());
            log.error(e.getMessage(), e);
        }

        return result;
    }

}
