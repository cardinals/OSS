package com.nobitastudio.oss.model.dto;

import com.nobitastudio.oss.model.entity.Department;
import com.nobitastudio.oss.model.entity.DiagnosisRoom;
import com.nobitastudio.oss.model.entity.Doctor;
import com.nobitastudio.oss.model.entity.MedicalCard;
import com.nobitastudio.oss.model.entity.OSSOrder;
import com.nobitastudio.oss.model.entity.RegistrationRecord;
import com.nobitastudio.oss.model.entity.User;
import com.nobitastudio.oss.model.entity.Visit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/04/22 19:27
 * @description 挂号单包含的全部对象
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationAll {

    // 挂号单实体
    private RegistrationRecord registrationRecord;

    //科室
    private Department department;

    // 医生
    private Doctor doctor;

    //挂号者实体
    private User user;

    // 号源实体
    private Visit visit;

    // 诊疗卡实体
    private MedicalCard medicalCard;

    // 订单实体
    private OSSOrder ossOrder;

    // 诊室
    private DiagnosisRoom diagnosisRoom;
}
