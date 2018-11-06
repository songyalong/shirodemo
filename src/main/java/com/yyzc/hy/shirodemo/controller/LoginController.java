package com.yyzc.hy.shirodemo.controller;

import com.yyzc.hy.shirodemo.entity.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
        subject.login(usernamePasswordToken);
        ModelAndView modelAndView = new ModelAndView("index");
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
}
