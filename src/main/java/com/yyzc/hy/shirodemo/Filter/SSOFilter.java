package com.yyzc.hy.shirodemo.Filter;

import com.yyzc.hy.shirodemo.constant.AuthConst;
import com.yyzc.hy.shirodemo.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
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

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        // 判断是否已登录, 已登录
        if(null != session.getAttribute(AuthConst.IS_LOGIN)){
            filterChain.doFilter(servletRequest, servletResponse);
            return ;
        }

        //回调身份验证中心，验证token或者cookie
        String token = request.getParameter("token");
        Object myCookie = session.getAttribute("myCookie");
        Cookie[] cookies = request.getCookies();
        if(StringUtils.isNotBlank(token)){
            Map<String, String> data = new HashMap<>();
            data.put("token", "token");
            boolean post = HttpUtil.post(AuthConst.AUTH_SERVER_URL, data);
            if(post){
                session.setAttribute(AuthConst.IS_LOGIN, true);
                Cookie cookie = new Cookie("token", token);
                cookie.setPath("/");
                response.addCookie(cookie);
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }else if(null != myCookie && null != cookies && cookies.length > 0){
            // cookie方式判断
            for(Cookie cookie : cookies){
                if(cookie.getName().toLowerCase().equals(myCookie.toString())){
                    break;
                }
            }
        }else{

            //重定向到认证中心
            response.sendRedirect(AuthConst.AUTH_SERVER_URL+"?service="+AuthConst.AUTH_CLIENT_URL);
        }

    }
}
