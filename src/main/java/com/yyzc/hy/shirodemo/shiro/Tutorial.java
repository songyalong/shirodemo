package com.yyzc.hy.shirodemo.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: songyalong
 * @Description: shiro 测试
 * @Date: Created in ${time}${date}
 * @Modified By:
 */
public class Tutorial {
    private static Logger logger = LoggerFactory.getLogger(Tutorial.class);

    public static void main(String[] args) {
        logger.info("My first shiro application ");
        IniSecurityManagerFactory iniSecurityManagerFactory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = iniSecurityManagerFactory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute("guest", "guest");
        if(!subject.isAuthenticated()){
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("guest", "guest");
            usernamePasswordToken.setRememberMe(true);
            subject.login(usernamePasswordToken);
            if(subject.isAuthenticated()){
                logger.info("登录成功 Principal = {}， Principals = {}", subject.getPrincipal(), subject.getPrincipals());
                if(subject.hasRole("client")){
                    logger.info("have role ={}", "admin");
                }else{
                    logger.info("no admin role");
                }
                if(subject.isPermitted("look:desk")){
                    logger.info("permitted look desk");
                }else {
                    logger.info("no look desk");
                }
                subject.logout();
            }
        }
        System.exit(0);
    }




}
