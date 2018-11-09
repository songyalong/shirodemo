package com.yyzc.hy.shirodemo.constant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @Author: songyalong
 * @Description: 用户身份验证常量
 * @Date: Created in ${time}${date}
 * @Modified By:
 */
@Component
@Getter
public class AuthConst {
    public static final String IS_LOGIN="isLogin";
    public static final String LOGIN_URL = "loginUrl";
    public static final String TOKEN = "token";
    public static String clientUrl;

    @Value("${cas.server.url}")
    public String authServerUrl;

    @Value("${cas.project.url}")
    public String perClientUrl;

}
