package com.yyzc.hy.shirodemo.dao;


import com.yyzc.hy.shirodemo.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> getRoleByIds(@Param("ids") List<Integer> ids);
}