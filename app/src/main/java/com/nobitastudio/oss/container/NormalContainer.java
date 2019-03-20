package com.nobitastudio.oss.container;

import android.app.Activity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/02/28 11:27
 * @description 正常容器
 */
public class NormalContainer implements Serializable {

    public static Map<String,Object> container = new HashMap<>();

    public static final String SELECTED_ACTIVITY = "SELECTED_ACTIVITY";

}
