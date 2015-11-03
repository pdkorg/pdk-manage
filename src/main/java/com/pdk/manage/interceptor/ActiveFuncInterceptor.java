package com.pdk.manage.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hubo on 2015/8/10.
 */
public class ActiveFuncInterceptor extends HandlerInterceptorAdapter {
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        String funcActiveCode = request.getParameter("funcActiveCode") != null ? request.getParameter("funcActiveCode") : "INDEX";

        request.setAttribute("funcActiveCode", funcActiveCode);
    }

}
