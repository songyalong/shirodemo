package com.yyzc.hy.shirodemo.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * @Author: songyalong
 * @Description: Shiro 测试
 * @Date: Created in ${time}${date}
 * @Modified By:
 */
public class ShiroTest {

    // 指定realm
    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser(){
        simpleAccountRealm.addAccount("mark", "123456");
    }

    @Test
    public void testAuthentication(){

        // 创建SecurityManager
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm); // 设置realm

        // 主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("mark", "123456");
        subject.login(token);

        // 判断是否认证通过
        System.out.println("isAuthenticated:" +subject.isAuthenticated());

        // 退出登录
        subject.logout();
    }
}
