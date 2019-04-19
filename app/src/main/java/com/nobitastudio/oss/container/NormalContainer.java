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

    // 进入 InputMobileFragment的意图
    public enum InputMobileFor {
        REGISTER, //
        MODIFY_PASSWORD, // 修改密码
        CREATE_MEDICAL_CARD; // 创建诊疗卡
    }

    // 容器
    public static Map<String, Object> container = new HashMap<>();

    //  key
    // 非选择类型
    public static final String USER = "USER"; // 登录的用户
    public static final String VISITS = "VISITS";// 需要展示的号源
    public static final String COLLECT_DOCTORS = "COLLECT_DOCTORS"; // 收藏的医生
    public static final String BIND_MEDICAL_CARD = "BIND_MEDICAL_CARD"; // 绑定的诊疗阿卡
    public static final String SETTING_ATTR = "SETTING_ATTR"; // 用户设置
    public static final String DIAGNOSIS_NO = "DIAGNOSIS_NO"; // 当年前挂号单所得到的诊号
    public static final String REGISTRATION_RECORD = "REGISTRATION_RECORD"; // 最新挂号成功的记录
    public static final String LEFT_TIME_PAY = "LEFT_TIME_PAY"; // 剩余支付的时间 以秒计时
    public static final String OSS_ORDER = "OSS_ORDER"; // 从服务器端获取的订单
    public static final String INPUT_MOBILE_FRAGMENT = "INPUT_MOBILE_FRAGMENT";    // 进入InputMobileFragment的意图
    public static final String INPUT_MOBILE = "INPUT_MOBILE"; // 用户输入的手机号（并非用于登录）

    // 选择类型
    public static final String SELECTED_ACTIVITY = "SELECTED_ACTIVITY"; // 当前的activity
    public static final String SELECTED_HEALTH_ARTICLE = "SELECTED_HEALTH_ARTICLE"; // 选中的健康资讯.下面三种情况先暂时不使用
    public static final String SELECTED_HOSPITAL_ACTIVITY = "SELECTED_HOSPITAL_ACTIVITY"; // 选中的医院活动
    public static final String SELECTED_HEADLINE = "SELECTED_HEADLINE"; // 选中的健康头条
    public static final String SELECTED_DOCTOR_LECTURE = "SELECTED_DOCTOR_LECTURE"; // 选中的名医讲堂
    public static final String SELECTED_DEPARTMENT = "SELECTED_DEPARTMENT"; // 选中的科室
    public static final String SELECTED_DOCTOR = "SELECTED_DOCTOR"; // 选中的科室
    public static final String SELECTED_VISIT = "SELECTED_VISIT"; // 选中的号源
    public static final String SELECTED_MEDICAL_CARD = "SELECTED_MEDICAL_CARD"; // 选中的诊疗卡

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

    public static <T> T get(String key,Class<T> tClass) {
        return (T) container.get(key);
    }

}
