package com.nobitastudio.oss.container;

import android.app.Activity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/02/28 11:27
 * @description 运行时容器
 */
public class NormalContainer implements Serializable {

    // 容器
    public static Map<String, Object> container = new HashMap<>();

    //  key
    public static final String SELECTED_ACTIVITY = "SELECTED_ACTIVITY"; // 当前的activity
    public static final String USER = "USER"; // 登录的用户
    public static final String SELECTED_HOSPITAL_ACTIVITY = "SELECTED_HOSPITAL_ACTIVITY";

    public static void put(String key, Object value) {
        container.put(key, value);
    }

    // 移除 keyvalue
    public static void remove(String key) {
        container.remove(key);
    }

    public static boolean isExist(String key) {
        return container.containsKey(key);
    }

    public static <T> T get(String key) {
        return (T) container.get(key);
    }

}
