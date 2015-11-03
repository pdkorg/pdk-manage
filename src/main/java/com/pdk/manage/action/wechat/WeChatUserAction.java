package com.pdk.manage.action.wechat;

import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.sm.User;
import com.pdk.manage.service.sm.SmUserService;
import com.pdk.manage.util.DBConst;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 用户相关对外接口
 * Created by liuhaiming on 2015/8/30.
 */
@Controller
@RequestMapping("/wechat")
public class WeChatUserAction {
    private static final Logger log = LoggerFactory.getLogger(WeChatUserAction.class);

    @Autowired
    private SmUserService userService;

    @RequestMapping(value = "/sm_user", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(User user) {
        Map<String, Object> result = new HashMap<>();

        if ( StringUtils.isEmpty( user.getSourceId() ) ) {
            result.put("result", "error");
            result.put("errorCode", "2");
            result.put("msg", "没有找到sourceId");

            return result;
        }

        try {
            while ( true ) {
                if ( idLock.add(user.getSourceId()) ) {
                    try {
                        if (StringUtils.isEmpty(user.getName())) {
                            user.setName("未开放权限用户");
                        }

                        user.setType(DBConst.USER_TYPE_WEIXIN);
                        user.setRegisterTime(Calendar.getInstance().getTime());
                        user.setStatus(DBConst.STATUS_ENABLE);
                        user.setUnRegisterTime(null);

                        int size = userService.saveRegisterInfo(user);
                        if ( size > 0 ) {
                            result.put("result", "success");
                        } else {
                            result.put("result", "error");
                            result.put("errorCode", "1");
                            result.put("msg", "多次关注请求，第二次以后不进行处理！");
                        }

                        break;
                    } catch (Exception e) {
                        result.put("result", "error");
                        result.put("errorCode", "1");
                        result.put("msg", e.getMessage());
                        log.error(e.getMessage(), e);
                        break;
                    } finally {
                        idLock.remove(user.getSourceId());
                        synchronized ( this ) {
                            this.notifyAll();
                        }

                    }
                } else {
                    synchronized ( this ) {
                        try {
                            this.wait();
                        } catch (Exception e) {
                            result.put("result", "error");
                            result.put("errorCode", "1");
                            result.put("msg", e.getMessage());
                            log.error(e.getMessage(), e);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return result;
    }

    private Set<String> idLock = Collections.synchronizedSet(new HashSet<String>());
    @RequestMapping(value = "/sm_user/un_register/{sourceId}", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> unRegisterTime(@PathVariable String sourceId) {
        Map<String, Object> result = new HashMap<>();
        if ( StringUtils.isEmpty( sourceId ) ) {
            result.put("result", "error");
            result.put("errorCode", "2");
            result.put("msg", "没有找到sourceId");

            return result;
        }

        try {
            while ( true ) {
                if ( idLock.add(sourceId) ) {
                    try {
                        userService.updateRegisterInfo(sourceId);
                        result.put("result", "success");

                        break;
                    } catch (Exception e) {
                        result.put("result", "error");
                        result.put("errorCode", "1");
                        result.put("msg", e.getMessage());
                        log.error(e.getMessage(), e);
                        break;
                    } finally {
                        idLock.remove(sourceId);
                        synchronized ( this ) {
                            this.notifyAll();
                        }

                    }
                } else {
                    synchronized ( this ) {
                        try {
                            this.wait();
                        } catch (Exception e) {
                            result.put("result", "error");
                            result.put("errorCode", "1");
                            result.put("msg", e.getMessage());
                            log.error(e.getMessage(), e);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return result;
    }

    /**
     * 微信接口，获取用户注册次数
     * @param sourceId
     * @return
     */
    @RequestMapping(value="/sm_user/select_user_count/{sourceId}")
    @ResponseBody
    public Map<String, Object> getUserCount(@PathVariable String sourceId) {
        Map<String, Object> result = new HashMap<>();
        if ( StringUtils.isEmpty( sourceId ) ) {
            result.put("result", "error");
            result.put("errorCode", "2");
            result.put("msg", "没有找到sourceId");

            return result;
        }

        try {
            int userCnt = userService.findUserCountBySourceId(sourceId);
            result.put("count", userCnt);
            result.put("errorCode", "1");
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("msg", e.getMessage());
            result.put("errorCode", "1");
            result.put("result", "error");
        }

        return result;
    }

    @RequestMapping(value="/sm_user/select_user/{sourceId}")
    @ResponseBody
    public Map<String, Object> getUserBySourceId(@PathVariable String sourceId) {
        Map<String, Object> result = new HashMap<>();
        if ( StringUtils.isEmpty( sourceId ) ) {
            result.put("result", "error");
            result.put("errorCode", "2");
            result.put("msg", "没有找到sourceId");

            return result;
        }

        try {
            User user = userService.findUserBySourceId(sourceId);
            result.put("user", user);
            result.put("result", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            result.put("msg", e.getMessage());
            result.put("errorCode", "1");
            result.put("result", "error");
        }

        return result;
    }
}