package com.nobitastudio.oss.model.entity;

import java.io.Serializable;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/25 20:23
 * @description
 */
public class User implements Serializable {

    public User() {
    }

    public User(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }

    private String mobile;

    private String password;
}
