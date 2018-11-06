package com.yyzc.hy.shirodemo.realm;

import com.yyzc.hy.shirodemo.entity.Role;
import com.yyzc.hy.shirodemo.entity.UserInfo;
import com.yyzc.hy.shirodemo.service.LoginService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: songyalong
 * @Description:
 * @Date: Created in ${time}${date}
 * @Modified By:
 */
public class MyShiroRealm extends AuthorizingRealm {

    static Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    @Autowired
    private LoginService loginService;

    /**
     * 权限认证（为当前登录的Subject授予角色和权限）
     *
     * 该方法的调用时机为需授权资源被访问时，并且每次访问需授权资源都会执行该方法中的逻辑，这表明本例中并未启用AuthorizationCache，
     * 如果连续访问同一个URL（比如刷新），该方法不会被重复调用，Shiro有一个时间间隔（也就是cache时间，在ehcache-shiro.xml中配置），
     * 超过这个时间间隔再刷新页面，该方法会被执行
     *
     * doGetAuthorizationInfo()是权限控制，
     * 当访问到页面的时候，使用了相应的注解或者shiro标签才会执行此方法否则不会执行，
     * 所以如果只是简单的身份认证没有权限的控制的话，那么这个方法可以不进行实现，直接返回null即可
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("授权操作");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        System.out.println(principalCollection.getPrimaryPrincipal().toString());
//        UserInfo userInfo = (UserInfo) principalCollection.getPrimaryPrincipal();
//        logger.info(userInfo.toString());
        UserInfo userInfo = new UserInfo();
        List<Role> roleList = loginService.getRoleByUserId(userInfo);
        Set<String> roles  = new HashSet<>();
        for(Role role : roleList){
            roles.add(role.getRoleName());
        }
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(roles);
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        Object principal = authenticationToken.getPrincipal();
        if(null == principal){
            return null;
        }
        String username = authenticationToken.getPrincipal().toString();
        String credentials = authenticationToken.getCredentials().toString();

        logger.info("username={}, credentials={}", username, credentials);
        System.out.println("username="+username);
        System.out.println("credentials="+credentials);
        UserInfo userInfo = loginService.findByUsername(username);
        if(null == userInfo){
            return null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, userInfo.getPassword(), this.getName());
        return simpleAuthenticationInfo;
    }

}
