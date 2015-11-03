package com.pdk.manage.interceptor;

import com.pdk.manage.dto.sm.FuncDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hubo on 2015/8/8
 */
public class LoginInterceptor  implements HandlerInterceptor{

    private static Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Boolean isLogin =  (Boolean)request.getSession().getAttribute("isLogin");

        if(isLogin == null || !isLogin){
            log.info("没有登录,直接跳转登录页面");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return false;
        }
//        else {
//            boolean isPermission = isPermissionFunc(request.getSession(), request.getServletPath());
//
//            if ( !isPermission ) { // TODO:针对没有权限的url暂时返回到登陆页面，后续应该跳转到错误页面上。
//                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
//            }
//            return isPermission;
//        }
        return true;
    }

    @Autowired
    private HashSet<String> excludeURISet;

    /**
     * 校验url请求是否有权限，由于规范url格式如下【模块编码/节点编码/。。。】，所以根据请求url进行前缀验证，来判断是否有请求权限
     * @param session
     * @param servletPath
     * @return
     */
    private boolean isPermissionFunc(HttpSession session, String servletPath) {
        if ( excludeURISet.contains(servletPath) ) {
            return true;
        }

        List<FuncDto> funcLst = (List<FuncDto>) session.getAttribute("funcList");
        Set<String> funcUriSet = (Set<String>) session.getAttribute("funcUriSet");
        if ( funcUriSet == null ) {
            funcUriSet = new HashSet<>();
        }

        if ( funcUriSet.contains(servletPath) ) {
            return true;
        }

        String url = null;
        for ( FuncDto modelFunc : funcLst ) {
            for ( FuncDto func : modelFunc.getChildren() ) {
                url = func.getHref();
                url = ( url.startsWith("/") ? url : "/" + url );
                if ( servletPath.startsWith(url) ) {
                    funcUriSet.add(servletPath);
                    session.setAttribute("funcUriSet", funcUriSet);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
