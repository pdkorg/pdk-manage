package com.pdk.manage.action.cs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by hubo on 2015/10/20
 */
@Controller
@RequestMapping("/cs/chat/msg")
public class ChatMsgAction {

    @RequestMapping("/index")
    public String index() {
        return "cs/chatmsg";
    }

}
