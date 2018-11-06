package com.yyzc.hy.shirodemo.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/*
 InitRealm 测试
 */
public class InitRealmTest {
    // 指定init realm
    IniRealm iniRealm = new IniRealm("classpath:shiro.ini");

    @Test
    public void testAuthentication(){

        // 创建SecurityManager
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm); // 设置realm

        // 主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("root", "secret");
        subject.login(token);

        // 判断是否认证通过
        System.out.println("isAuthenticated:" +subject.isAuthenticated());
        subject.checkRole("admin");

        // 退出登录
        subject.logout();

    }
}
