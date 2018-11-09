package com.yyzc.hy.shirodemo.realm;

import io.buji.pac4j.realm.Pac4jRealm;
import io.buji.pac4j.token.Pac4jToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @Author: songyalong
 * @Description:
 * @Date: Created in ${time}${date}
 * @Modified By:
 */
public class CasRealm extends Pac4jRealm {

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        final Pac4jToken pac4jToken = (Pac4jToken) authenticationToken;

        return super.doGetAuthenticationInfo(authenticationToken);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return super.doGetAuthorizationInfo(principals);
    }
}
