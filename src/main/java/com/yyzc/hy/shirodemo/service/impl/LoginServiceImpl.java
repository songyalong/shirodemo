package com.yyzc.hy.shirodemo.service.impl;

import com.yyzc.hy.shirodemo.dao.RoleMapper;
import com.yyzc.hy.shirodemo.dao.UserInfoMapper;
import com.yyzc.hy.shirodemo.entity.Role;
import com.yyzc.hy.shirodemo.entity.UserInfo;
import com.yyzc.hy.shirodemo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: songyalong
 * @Description:
 * @Date: Created in ${time}${date}
 * @Modified By:
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserInfo findByUsername(String username) {
        UserInfo userInfo = userInfoMapper.findByUsername(username);
        return userInfo;
    }

    @Override
    public List<Role> getRoleByUserId(UserInfo userInfo) {
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(56);
        ids.add(97);
        ids.add(124);
        List<Role> roleList = roleMapper.getRoleByIds(ids);
        return roleList;
    }


}
