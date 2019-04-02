package com.nobitastudio.oss.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/10 12:20
 * @description 用户挂号的数据传输对象
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO implements Serializable {

    private static final long serialVersionUID = -4329830827839495650L;

    // 用户id
    private Integer userId;
    // 诊疗卡id
    private String medicalCardId;
    // 号源id
    private Integer visitId;
    // 图片验证码
    private String captcha;
}
