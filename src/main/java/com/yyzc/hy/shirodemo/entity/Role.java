package com.yyzc.hy.shirodemo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: songyalong
 * @Description:
 * @Date: Created in ${time}${date}
 * @Modified By:
 */
@Getter
@Setter
@ToString
public class Role {
    private Integer id;
    private String roleName;
    private Integer order;
    private Integer flag;
    private Integer key;
}
