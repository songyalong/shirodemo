package com.yyzc.hy.shirodemo.service;

import com.yyzc.hy.shirodemo.entity.Role;
import com.yyzc.hy.shirodemo.entity.UserInfo;

import java.util.List;

/**
 * @Author: songyalong
 * @Description:
 * @Date: Created in ${time}${date}
 * @Modified By:
 */
public interface LoginService {
    UserInfo findByUsername(String username);
    List<Role> getRoleByUserId(UserInfo userInfo);
}
