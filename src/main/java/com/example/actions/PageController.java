package com.example.actions;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 页面控制器
 * 根据url返回相应的页面
 * @author rzd
 * @date 2018/3/2
 */
@RestController
public class PageController {
    @RequestMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @RequestMapping("/home")
    public ModelAndView home() {
        return new ModelAndView("home");
    }
}
