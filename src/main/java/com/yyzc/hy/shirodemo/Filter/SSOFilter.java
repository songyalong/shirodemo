package com.yyzc.hy.shirodemo.Filter;

import com.yyzc.hy.shirodemo.constant.AuthConst;
import com.yyzc.hy.shirodemo.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: songyalong
 * @Description: 单点登录过滤器
 * @Date: Created in ${time}${date}
 * @Modified By:
 */
public class SSOFilter implements Filter {
    AuthConst authConst ;

    public SSOFilter(AuthConst authConst){
        this.authConst = authConst;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        // 判断是否已经登录, 已登录
        if(null != session.getAttribute(AuthConst.IS_LOGIN)){
            filterChain.doFilter(servletRequest, servletResponse);
            return ;
        }

        //回调身份验证中心，验证token
        String token = request.getParameter("token");
        if(StringUtils.isNotBlank(token)){
            authConst.getAuthServerUrl()
        }



    }
}
