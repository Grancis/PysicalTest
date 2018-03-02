package com.example.actions;

import com.example.services.LoginService;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录登出相关的action
 *
 * @author rzd
 * @date 2018/3/1
 */
@RestController
public class LoginController {
    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    private LoginService loginService;

    /**
     * 根据表单数据运行爬虫登录体育学院
     * 并将登录成功后的httpClient写入session, 以便后续操作.
     *
     * @param account  账号
     * @param password 密码
     * @param session  session
     * @return 如果成功跳转到主页面, 如果发现异常则重定向到错误提示页面
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(name = "account") String account,
                              @RequestParam(name = "password") String password,
                              HttpSession session) {
        try {
            CloseableHttpClient httpClient = loginService.httpClientAfterLogin(account, password);
            session.setAttribute("client", httpClient);
            System.out.println("setAttribute");
            return new ModelAndView("redirect:home");
        } catch (IOException e) {
            // 登录失败, 重定向到错误页面
            return new ModelAndView("redirect:error");
        }
    }


    /**
     * 取出session中的httpClient
     * 然后运行爬虫访问体育学院的登出url
     * 最后清理session
     *
     * @param session 根据cookie中的sessionID获取的session
     * @return 如果成功重定向到登录界面, 如果发现异常则定向到错误提示页面
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logOut(HttpSession session) {
        CloseableHttpClient httpClient = (CloseableHttpClient) session.getAttribute("client");
        try {
            loginService.logOut(httpClient);
        } catch (IOException e) {
            // 登出失败, 重定向到错误页面
            return new ModelAndView("redirect:error");
        } finally {
            session.invalidate();
        }
        return new ModelAndView("redirect:index");
    }
}
