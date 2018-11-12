package com.yyzc.hy.shirodemo.constant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author: songyalong
 * @Description: 用户身份验证常量
 * @Date: Created in ${time}${date}
 * @Modified By:
 */
public class AuthConst {
    public static final String IS_LOGIN="isLogin";
    public static final String LOGIN_URL = "loginUrl";
    public static final String TOKEN = "token";

    public static final String AUTH_SERVER_URL = "http://127.0.0.1:9000/cas";
    public static final String AUTH_CLIENT_URL = "http://127.0.0.1:9001";



}
