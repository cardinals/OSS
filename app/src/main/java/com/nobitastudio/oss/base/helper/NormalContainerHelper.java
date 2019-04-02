package com.nobitastudio.oss.base.helper;

import android.app.Activity;

import com.nobitastudio.oss.container.NormalContainer;
import com.nobitastudio.oss.model.entity.Department;
import com.nobitastudio.oss.model.entity.Doctor;
import com.nobitastudio.oss.model.entity.HealthArticle;
import com.nobitastudio.oss.model.entity.MedicalCard;
import com.nobitastudio.oss.model.entity.RegistrationRecord;
import com.nobitastudio.oss.model.entity.SettingAttr;
import com.nobitastudio.oss.model.entity.User;
import com.nobitastudio.oss.model.entity.Visit;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/04/02 14:57
 * @description 直接获取容器中的数据
 */
public class NormalContainerHelper {

    private NormalContainerHelper () {}

    private static NormalContainerHelper mNormalContainerHelper;

    public static synchronized NormalContainerHelper getInstance() {
        if (mNormalContainerHelper == null) {
            mNormalContainerHelper = new NormalContainerHelper();
        }
        return mNormalContainerHelper;
    }

    // get 方法
    public User getUser() {
        return NormalContainer.get(NormalContainer.USER);
    }

    public List<Visit> getVisits() {
        return NormalContainer.get(NormalContainer.VISITS);
    }

    public List<Doctor> getCollectDoctors() {
        return NormalContainer.get(NormalContainer.COLLECT_DOCTORS);
    }

    public List<MedicalCard> getBindMedicalCards() {
        return NormalContainer.get(NormalContainer.BIND_MEDICAL_CARD);
    }

    public List<SettingAttr> getSettingAttrs() {
        return NormalContainer.get(NormalContainer.SETTING_ATTR);
    }

    public Integer getDiagnosisNo() {
        return NormalContainer.get(NormalContainer.DIAGNOSIS_NO);
    }

    public RegistrationRecord getRegistrationRecord() {
        return NormalContainer.get(NormalContainer.REGISTRATION_RECORD);
    }

    public Activity getSelectedActivity() {
        return NormalContainer.get(NormalContainer.SELECTED_ACTIVITY);
    }

    public HealthArticle getSelectedHealthArticle() {
        return NormalContainer.get(NormalContainer.SELECTED_HEALTH_ARTICLE);
    }

    public HealthArticle getSelectedHospitalActivity() {
        return NormalContainer.get(NormalContainer.SELECTED_HOSPITAL_ACTIVITY);
    }

    public HealthArticle getSelectedHeadline() {
        return NormalContainer.get(NormalContainer.SELECTED_HEADLINE);
    }

    public HealthArticle getSelectedDoctorLecture() {
        return NormalContainer.get(NormalContainer.SELECTED_DOCTOR_LECTURE);
    }

    public Department getSelectedDepartment() {
        return NormalContainer.get(NormalContainer.SELECTED_DEPARTMENT);
    }

    public Doctor getSelectedDoctor() {
        return NormalContainer.get(NormalContainer.SELECTED_DOCTOR);
    }

    public Visit getSelectedVisit() {
        return NormalContainer.get(NormalContainer.SELECTED_VISIT);
    }

    public MedicalCard getSelectedMedicalCard() {
        return NormalContainer.get(NormalContainer.SELECTED_MEDICAL_CARD);
    }

    // set
    public NormalContainerHelper setUser(User user) {
        NormalContainer.put(NormalContainer.USER, user);
        return this;
    }

    public NormalContainerHelper setVisits(List<Visit> visits) {
        NormalContainer.put(NormalContainer.VISITS, visits);
        return this;
    }

    public NormalContainerHelper setCollectDoctors(List<Doctor> doctors) {
        NormalContainer.put(NormalContainer.COLLECT_DOCTORS, doctors);
        return this;
    }

    public NormalContainerHelper setBindMedicalCards(List<MedicalCard> medicalCards) {
        NormalContainer.put(NormalContainer.BIND_MEDICAL_CARD, medicalCards);
        return this;
    }

    public NormalContainerHelper setSettingAttrs(List<SettingAttr> settingAttrs) {
        NormalContainer.put(NormalContainer.SETTING_ATTR, settingAttrs);
        return this;
    }

    public NormalContainerHelper setDiagnosisNo(Integer diagnosisNo) {
        NormalContainer.put(NormalContainer.DIAGNOSIS_NO, diagnosisNo);
        return this;
    }

    public NormalContainerHelper settRegistrationRecord(RegistrationRecord registrationRecord) {
        NormalContainer.put(NormalContainer.REGISTRATION_RECORD, registrationRecord);
        return this;
    }

    public NormalContainerHelper setSelectedActivity(Activity selectedActivity) {
        NormalContainer.put(NormalContainer.SELECTED_ACTIVITY, selectedActivity);
        return this;
    }

    public NormalContainerHelper setSelectedHealthArticle(HealthArticle selectedHealthArticle) {
        NormalContainer.put(NormalContainer.SELECTED_HEALTH_ARTICLE, selectedHealthArticle);
        return this;
    }

    public NormalContainerHelper setSelectedHospitalActivity(HealthArticle selectedHospitalActivity) {
        NormalContainer.put(NormalContainer.SELECTED_HOSPITAL_ACTIVITY, selectedHospitalActivity);
        return this;
    }

    public NormalContainerHelper setSelectedHeadline(HealthArticle selectedHeadline) {
        NormalContainer.put(NormalContainer.SELECTED_HEADLINE, selectedHeadline);
        return this;
    }

    public NormalContainerHelper setSelectedDoctorLecture(HealthArticle selectedDoctorLecture) {
        NormalContainer.put(NormalContainer.SELECTED_DOCTOR_LECTURE, selectedDoctorLecture);
        return this;
    }

    public NormalContainerHelper setSelectedDepartment(Department department) {
        NormalContainer.put(NormalContainer.SELECTED_DEPARTMENT, department);
        return this;
    }

    public NormalContainerHelper setSelectedDoctor(Doctor doctor) {
        NormalContainer.put(NormalContainer.SELECTED_DOCTOR, doctor);
        return this;
    }

    public NormalContainerHelper setSelectedVisit(Visit visit) {
        NormalContainer.put(NormalContainer.SELECTED_VISIT, visit);
        return this;
    }

    public NormalContainerHelper setSelectedMedicalCard(MedicalCard medicalCard) {
        NormalContainer.put(NormalContainer.SELECTED_MEDICAL_CARD, medicalCard);
        return this;
    }

}
