package com.nobitastudio.oss.model.enumeration;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/12 12:35
 * @description 短信内容认证
 */
public enum SmsMessageType {
    ENROLL,
    RETRIEVE_PASSWORD,
    UPDATE_PASSWORD,
    CREATE_MEDICAL_CARD,
    BIND_MEDICAL_CARD,
    REGISTER_SUCCESS_HAVE_PAY,
    CANCEL_REGISTER,
    DIAGNOSIS_REMIND,
    CHECK_REMIND,
    EAT_DRUG_REMIND,
    REGISTER_SUCCESS_WAITING_PAY,
}
