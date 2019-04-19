package com.nobitastudio.oss.model.common.error;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/19 14:17
 * @description 服务端返回的错误码
 */
public class ErrorCode {

    // 错误码
    public static Map<String, String> errorCodeContainer;
    // key
    public static final String UNDEFINED_ERROR = "UNDEFINED_ERROR";  // 未定义错误
    public static final String UNKNOWN_ERROR = "UNKNOWN_ERROR";  // 400 500 404等错误 未知错误
    public static final String NOT_FIND_USER_BY_MOBILE = "NOT_FIND_USER_BY_MOBILE"; // 用户未注册
    public static final String MOBILE_OR_PASSWORD_ERROR = "MOBILE_OR_PASSWORD_ERROR"; // 账号密码错误
    public static final String HAS_COLLECT_DOCTOR = "HAS_COLLECT_DOCTOR"; // 以收藏医生
    public static final String CAPTCHA_ERROR = "CAPTCHA_ERROR"; // 验证码错误
    public static final String CAPTCHA_EXPIRE = "CAPTCHA_EXPIRE"; // 验证码已过时
    public static final String VISIT_NO_LEFT = "VISIT_NO_LEFT"; // 挂号失败,该号源已全部挂完
    public static final String NOT_FIND_ORDER = "NOT_FIND_ORDER"; // 未找到指定订单
    public static final String HAS_CANCEL_REGISTER = "HAS_CANCEL_REGISTER"; // 订单已经处于取消装填
    public static final String SMS_CODE_SEND_FAIL = "SMS_CODE_SEND_FAIL"; // 验证码发送失败
    public static final String MOBILE_HAS_REGISTER = "MOBILE_HAS_REGISTER"; // 该手机号已经注册


    public static String get(String errorCode) {
        if (errorCodeContainer == null) {
            initErrorCode();
        }
        return errorCodeContainer.getOrDefault(errorCode, errorCodeContainer.get(UNDEFINED_ERROR));
    }

    private static synchronized void initErrorCode() {
        errorCodeContainer = new HashMap<>();
        errorCodeContainer.put(UNDEFINED_ERROR, "未定义错误");
        errorCodeContainer.put(UNKNOWN_ERROR, "发生未知错误,请联系系统管理员");
        errorCodeContainer.put(NOT_FIND_USER_BY_MOBILE, "该手机号尚未注册");
        errorCodeContainer.put(MOBILE_OR_PASSWORD_ERROR, "账号或密码错误");
        errorCodeContainer.put(HAS_COLLECT_DOCTOR,"您以收藏该医生,请勿重复收藏");
        errorCodeContainer.put(CAPTCHA_ERROR, "验证码错误");
        errorCodeContainer.put(CAPTCHA_EXPIRE,"验证码已过时");
        errorCodeContainer.put(VISIT_NO_LEFT, "挂号失败,该号源已全部挂完");
        errorCodeContainer.put(NOT_FIND_ORDER, "未找到指定订单");
        errorCodeContainer.put(HAS_CANCEL_REGISTER, "该挂号单已处于取消预约状态,请勿重复取消");
        errorCodeContainer.put(SMS_CODE_SEND_FAIL,"验证码发送失败,请重试");
        errorCodeContainer.put(MOBILE_HAS_REGISTER,"该手机号已经注册");
    }

}
