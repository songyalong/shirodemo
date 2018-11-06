package com.yyzc.hy.shirodemo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: songyalong
 * @Description:
 * @Date: Created in ${time}${date}
 * @Modified By:
 */
@Getter
@Setter
@ToString
public class UserInfo implements Serializable{
    private String name;
    private Integer id;
    private String password;
}
