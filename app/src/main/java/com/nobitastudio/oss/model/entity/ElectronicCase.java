package com.nobitastudio.oss.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nobitastudio.oss.model.enumeration.ElectronicCaseType;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/04/24 16:43
 * @description 电子病历对应的实体
 */
@Data
@Entity
@Table(name = "electronic_case")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicCase {

    // 绑定物理id,每一次就诊的病历详情
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 订单id
    @Column(name = "order_id")
    private String orderId;

    // 本次就诊对应的挂号单id
    @Column(name = "registration_record_id")
    private String registrationRecordId;

    // 用户到医院报到的时间
    @Column(name = "diagnosis_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime diagnosisTime;

    // 当为住院类型的时候,表示出院时间也可以表示就诊结束时间
    @Column(name = "recover_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime recoverTime;

    // 诊断描述
    @Column(name = "diagnosis_des")
    private String diagnosisDes;

    // 诊断医嘱
    @Column(name = "diagnosis_advise")
    private String diagnosisAdvise;

    // 用药医嘱
    @Column(name = "use_drug_advise")
    private String useDrugAdvise;

    // 检查医嘱
    @Column(name = "check_advise")
    private String checkAdvise;

    // 其他医嘱
    @Column(name = "other_advise")
    private String otherAdvise;

    // 电子病历类型:门诊，住院，急诊
    @Column(name = "case_type")
    @Enumerated(EnumType.STRING)
    private ElectronicCaseType caseType;
}
