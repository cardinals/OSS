package com.nobitastudio.oss.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/03 11:26
 * @description 挂号的号源
 */
public class Visit implements Serializable {

    private static final long serialVersionUID = -8300723782462137747L;

    public Visit() {
        this(1,1,1.0,LocalDateTime.now(),1,1,1);
    }

    public Visit(Integer id, Integer doctorId, Double cost, LocalDateTime diagnosisTime, Integer amount, Integer leftAmount, Integer diagnosisRoomId) {
        this.id = id;
        this.doctorId = doctorId;
        this.cost = cost;
        this.diagnosisTime = diagnosisTime;
        this.amount = amount;
        this.leftAmount = leftAmount;
        this.diagnosisRoomId = diagnosisRoomId;
    }

    private Integer id;

    private Integer doctorId;

    private Double cost;

    private LocalDateTime diagnosisTime;

    private Integer amount;

    private Integer leftAmount;

    private Integer diagnosisRoomId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public LocalDateTime getDiagnosisTime() {
        return diagnosisTime;
    }

    public void setDiagnosisTime(LocalDateTime diagnosisTime) {
        this.diagnosisTime = diagnosisTime;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getLeftAmount() {
        return leftAmount;
    }

    public void setLeftAmount(Integer leftAmount) {
        this.leftAmount = leftAmount;
    }

    public Integer getDiagnosisRoomId() {
        return diagnosisRoomId;
    }

    public void setDiagnosisRoomId(Integer diagnosisRoomId) {
        this.diagnosisRoomId = diagnosisRoomId;
    }
}
