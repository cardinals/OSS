package com.nobitastudio.oss.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/31 13:17
 * @description
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResult implements Serializable {

    // 登录的用户
    private User user;

    // 收藏的诊疗卡
    private List<MedicalCard> medicalCards;

    // 设置属性：是否推送等待
    private List<SettingAttr> settingAttrs;

    // 用户消息
    private List<Info> infos;

    // 收藏的医生
    private List<Doctor> doctors;
}
