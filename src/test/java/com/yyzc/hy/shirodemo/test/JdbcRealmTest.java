package com.yyzc.hy.shirodemo.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class JdbcRealmTest {

    DruidDataSource druidDataSource = new DruidDataSource();
    {
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/rbac?serverTimezone=GMT%2B8");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("123123");
    }
    // 指定jdbc realm

    @Test
    public void testAuthentication(){

        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(druidDataSource);

        // 创建SecurityManager
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm); // 设置realm

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
