package com.example.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 * 拦截未登录的访问, 重定向到登录界面
 *
 * @author rzd
 * @date 2018/3/1
 */
public class LoginInterceptor implements HandlerInterceptor {
    private static final String LOGIN_URL = "/login";
    private static final String INDEX_URL = "/index";

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        // 登录界面不设置拦截
        if (INDEX_URL.equals(httpServletRequest.getRequestURI())) {
            return true;
        }
        // 根据session中是否有httpClient判断是否登录
        boolean ok = httpServletRequest.getSession().getAttribute("client") != null;

        // 拦截重复登录
        if (LOGIN_URL.equals(httpServletRequest.getRequestURI())) {
            if (ok) {
                httpServletResponse.sendRedirect("/home");
                return false;
            } else {
                return true;
            }
        } else {
            if (ok) {
                return true;
            } else {
                httpServletResponse.sendRedirect("/index");
                return false;
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
