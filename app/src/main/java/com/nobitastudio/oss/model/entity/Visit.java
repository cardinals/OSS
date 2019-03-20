package com.nobitastudio.oss.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/03 11:26
 * @description 挂号的号源
 */
@Data
@Entity
@Table(name = "visit")
@Getter
@Setter
public class Visit implements Serializable {

    private static final long serialVersionUID = -8300723782462137747L;

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "doctor_id")
    private Integer doctorId;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "diagnosis_time")
    private LocalDateTime diagnosisTime;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "left_amount")
    private Integer leftAmount;

    @Column(name = "diagnosis_room_id")
    private Integer diagnosisRoomId;
}
