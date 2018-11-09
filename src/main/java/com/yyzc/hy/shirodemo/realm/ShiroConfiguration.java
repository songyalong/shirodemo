package com.yyzc.hy.shirodemo.realm;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.sun.tracing.ProbeName;
import com.yyzc.hy.shirodemo.realm.MyShiroRealm;
import com.yyzc.hy.shirodemo.redis.JedisCacheManager;
import com.yyzc.hy.shirodemo.redis.RedisSessionDao;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: songyalong
 * @Description:
 * @Date: Created in ${time}${date}
 * @Modified By:
 */
@Configuration
public class ShiroConfiguration {

    @Bean
    public MyShiroRealm myShiroRealm(){
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        return myShiroRealm;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(myShiroRealm());
        // 会话管理
        defaultWebSecurityManager.setSessionManager(sessionManager());
        // 缓存管理
        defaultWebSecurityManager.setCacheManager(jedisCacheManager());
        // 设置记录我
        defaultWebSecurityManager.setRememberMeManager(rememberMeManager());
        return defaultWebSecurityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        Map<String,String> map = new HashMap<String, String>();
        //登出
        map.put("/logout","logout");
        //对所有用户认证
        map.put("/**","user");
        //登录
        shiroFilterFactoryBean.setLoginUrl("/login");
        //首页
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/error");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

    @Bean
    public SessionDAO sessionDAO(){
        return new RedisSessionDao();
    }

    @Bean
    public SessionManager sessionManager(){
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setSessionDAO(sessionDAO());
        // 设置会话监听器
//        defaultWebSessionManager.setSessionListeners();
        //会话验证
//        defaultWebSessionManager.setSessionValidationScheduler();
        // 设置cookie
        defaultWebSessionManager.setSessionIdCookie(simpleCookie());
        return defaultWebSessionManager;
    }

    @Bean
    public JedisCacheManager jedisCacheManager(){
        return new JedisCacheManager();
    }

    /*
        设置cookie和cookie的存活时长
     */
    @Bean
    public SimpleCookie simpleCookie(){
        SimpleCookie simpleCookie = new SimpleCookie("REDISSESSION");
        simpleCookie.setMaxAge(2592000);
        return simpleCookie;
    }

    /*
    记住我管理器
     */
    @Bean
    public RememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(simpleCookie());
        return cookieRememberMeManager;
    }

}
