package com.pdk.manage.action;

import com.pdk.manage.dto.sm.FuncDto;
import com.pdk.manage.model.sm.Employee;
import com.pdk.manage.service.sm.EmployeeService;
import com.pdk.manage.service.FuncService;
import com.pdk.manage.util.CommonConst;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by hubo on 2015/7/18
 */
@Controller
public class LoginAction {

    Logger logger = LoggerFactory.getLogger(LoginAction.class);

    @Autowired
    private FuncService funcService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String username, String password, String validateCode, Map<String, Object> map) {

        logger.info("username : {}  password :{}", username, password);
        System.out.println(username);

        String veifyCode = (String) session.getAttribute(ValidateCodeImageAction.RANDOMCODEKEY);

        if(!validateCode.equals(veifyCode)) {
            map.put("errorMsg", "验证码错误!");
            map.put("username", username);
            return "login";
        }

        Employee e = employeeService.getEmployee(username);

        if(e == null || username == null || password == null || !e.getPassword().equals(DigestUtils.md5Hex(password))) {
            map.put("errorMsg", "用户名或密码错误!");
            map.put("username", username);
            return "login";
        }

        logger.info("login success !");

        session.setAttribute(CommonConst.SESSION_ATTR_KEY_IS_LOGIN, true);

        session.setAttribute(CommonConst.SESSION_ATTR_KEY_LOGIN_EMPLOYEE, e);

        List<FuncDto> permissionFuncs = funcService.getPermissionFuncTreeList( e.getId() );
        session.setAttribute(CommonConst.SESSION_ATTR_KEY_PERMISSION_FUNCS, permissionFuncs);

        return "redirect:/";
    }

    @RequestMapping("/logout")
     public String logout() {

        session.removeAttribute(CommonConst.SESSION_ATTR_KEY_PERMISSION_FUNCS);

        session.removeAttribute(CommonConst.SESSION_ATTR_KEY_IS_LOGIN);

        session.removeAttribute(CommonConst.SESSION_ATTR_KEY_LOGIN_EMPLOYEE);

        session.invalidate();

        return "redirect:/";
    }
}
