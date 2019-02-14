package com.nobitastudio.oss.model.entity;

import com.nobitastudio.oss.model.enumeration.RoleName;

import java.io.Serializable;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2018/12/12 13:29
 * @description 角色实例
 */
public class Role implements Serializable {

    private static final long serialVersionUID = -2791309956740231708L;

    private Integer id;

    private RoleName name;

    private String description;
}
