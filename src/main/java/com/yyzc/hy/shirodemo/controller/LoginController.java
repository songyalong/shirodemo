package com.yyzc.hy.shirodemo.controller;

import com.yyzc.hy.shirodemo.entity.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: songyalong
 * @Description:
 * @Date: Created in ${time}${date}
 * @Modified By:
 */
@RestController
public class LoginController {
    static Logger logger = LoggerFactory.getLogger(LoginController.class);
    @RequestMapping(value = "/login")
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView("login");
        return modelAndView;
    }
    @PostMapping(value = "/login")
    public ModelAndView login(UserInfo userInfo){
        Subject subject = SecurityUtils.getSubject();
        logger.info(userInfo.toString());
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userInfo.getName(), userInfo.getPassword());
        usernamePasswordToken.setRememberMe(userInfo.isMemberMe());
        subject.login(usernamePasswordToken);
        usernamePasswordToken.setRememberMe(userInfo.isMemberMe());
        System.out.println("isMemberMe = " + userInfo.isMemberMe());
        System.out.println(subject.isRemembered());
        ModelAndView modelAndView = new ModelAndView("index");
        Session session = subject.getSession();
        System.out.println("id = "+ session.getId());
        System.out.println("host = "+ session.getHost());
        System.out.println("timeOut = "+ session.getTimeout());
        return modelAndView;
    }
    @GetMapping(value = "/index")
    public String index(){
        return "index";
    }

    //登出
    @GetMapping(value = "/logout")
    public String logout(){
        return "logout";
    }

    //错误页面展示
    @PostMapping(value = "/error")
    public String error(){
        return "error ok!";
    }

    // 跳转页面
    @GetMapping(value = "/forword")
    public ModelAndView forword(){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        System.out.println("session: " + session.getId());

        ModelAndView modelAndView = new ModelAndView("forword");
        return modelAndView;
    }
}
