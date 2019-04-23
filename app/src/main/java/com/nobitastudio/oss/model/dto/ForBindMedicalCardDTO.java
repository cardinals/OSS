package com.nobitastudio.oss.model.dto;

import com.nobitastudio.oss.model.enumeration.SmsMessageType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/14 13:53
 * @description 用户（指定的mobile）请求短信验证码时发出的请求
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForBindMedicalCardDTO implements Serializable {

    // 请求的短信验证码类型,即文本类型
    @Enumerated(EnumType.STRING)
    private SmsMessageType smsMessageType;

    // 待接收验证码的medicalCardId
    private String medicalCardId;
}


