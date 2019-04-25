package com.nobitastudio.oss.base.helper;

import android.app.Activity;

import com.nobitastudio.oss.container.NormalContainer;
import com.nobitastudio.oss.model.dto.ElectronicCaseDTO;
import com.nobitastudio.oss.model.entity.Department;
import com.nobitastudio.oss.model.entity.Doctor;
import com.nobitastudio.oss.model.entity.HealthArticle;
import com.nobitastudio.oss.model.entity.MedicalCard;
import com.nobitastudio.oss.model.entity.OSSOrder;
import com.nobitastudio.oss.model.entity.RegistrationRecord;
import com.nobitastudio.oss.model.entity.SettingAttr;
import com.nobitastudio.oss.model.entity.User;
import com.nobitastudio.oss.model.entity.Visit;
import com.nobitastudio.oss.model.vo.DoctorAndDepartment;
import com.qmuiteam.qmui.arch.QMUIFragmentActivity;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/04/02 14:57
 * @description 直接获取容器中的数据
 */
public class NormalContainerHelper {

    private NormalContainerHelper() {
    }

    private static NormalContainerHelper mNormalContainerHelper;

    public static synchronized NormalContainerHelper getInstance() {
        if (mNormalContainerHelper == null) {
            mNormalContainerHelper = new NormalContainerHelper();
        }
        return mNormalContainerHelper;
    }

    // 清除所有键值对
    public void clearAll() {
        NormalContainer.container.clear();
    }

    // 清除所有键值对除了 当前所处在的activity
    public void clearAllButActivity(QMUIFragmentActivity mainActivity) {
        NormalContainer.container.clear();
        NormalContainer.put(NormalContainer.SELECTED_ACTIVITY, mainActivity);
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

    public List<DoctorAndDepartment> getCollectDoctorAndDepartments() {
        return NormalContainer.get(NormalContainer.COLLECT_DOCTOR_AND_DEPARTMENTS);
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

    public Integer getLeftTime() {
        return NormalContainer.get(NormalContainer.LEFT_TIME_PAY);
    }

    public OSSOrder getOrder() {
        return NormalContainer.get(NormalContainer.OSS_ORDER);
    }

    public NormalContainer.InputMobileFor getInputMobileFragment() {
        return NormalContainer.get(NormalContainer.INPUT_MOBILE_FRAGMENT);
    }

    public String getInputMobile() {
        return NormalContainer.get(NormalContainer.INPUT_MOBILE);
    }

    public String getWaitBindMedicalCardNo() {
        return NormalContainer.get(NormalContainer.WAIT_BIND_MEDICAL_CARD_NO);
    }

    public String getMedicalCardPassword() {
        return NormalContainer.get(NormalContainer.MEDICAL_CARD_PASSWORD);
    }

    public Integer getDiagnosisTypePos() {
        return NormalContainer.get(NormalContainer.SELECTED_DIAGNOSIS_TYPE_POS);
    }

    public NormalContainer.EnterMedicalCardFor getEnterMedicalCardFor() {
        return NormalContainer.get(NormalContainer.ENTER_MEDICAL_CARD_FOR);
    }

    public List<ElectronicCaseDTO> getElectronicCases() {
        return NormalContainer.get(NormalContainer.ELECTRONIC_CASES);
    }

    public ElectronicCaseDTO getSelectedElectronicCase() {
        return NormalContainer.get(NormalContainer.SELECTED_ELECTRONIC_CASE);
    }

    public Integer getElectronicCaseTypePos() {
        return NormalContainer.get(NormalContainer.SELECTED_ELECTRONIC_CASE_TYPE_POS);
    }

    // ===========================================set
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

    public NormalContainerHelper setCollectDoctorAndDepartments(List<DoctorAndDepartment> doctorAndDepartments) {
        NormalContainer.put(NormalContainer.COLLECT_DOCTOR_AND_DEPARTMENTS, doctorAndDepartments);
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

    public NormalContainerHelper setRegistrationRecord(RegistrationRecord registrationRecord) {
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

    public NormalContainerHelper setLeftTime(Integer leftTime) {
        NormalContainer.put(NormalContainer.LEFT_TIME_PAY, leftTime);
        return this;
    }

    public NormalContainerHelper setOrder(OSSOrder ossOrder) {
        NormalContainer.put(NormalContainer.OSS_ORDER, ossOrder);
        return this;
    }

    public NormalContainerHelper setInputMobileFragment(NormalContainer.InputMobileFor inputMobileFor) {
        NormalContainer.put(NormalContainer.INPUT_MOBILE_FRAGMENT, inputMobileFor);
        return this;
    }

    public NormalContainerHelper setInputMobile(String mobile) {
        NormalContainer.put(NormalContainer.INPUT_MOBILE, mobile);
        return this;
    }

    public NormalContainerHelper setWaitBindMedicalCardNo(String waitBindMedicalCardNo) {
        NormalContainer.put(NormalContainer.WAIT_BIND_MEDICAL_CARD_NO, waitBindMedicalCardNo);
        return this;
    }

    public NormalContainerHelper setMedicalCardPassword(String medicalCardPassword) {
        NormalContainer.put(NormalContainer.MEDICAL_CARD_PASSWORD, medicalCardPassword);
        return this;
    }

    public NormalContainerHelper setDiagnosisTypePos(Integer pos) {
        NormalContainer.put(NormalContainer.SELECTED_DIAGNOSIS_TYPE_POS, pos);
        return this;
    }

    public NormalContainerHelper setEnterMedicalCardFor(NormalContainer.EnterMedicalCardFor enterMedicalCardFor) {
        NormalContainer.put(NormalContainer.ENTER_MEDICAL_CARD_FOR, enterMedicalCardFor);
        return this;
    }

    public NormalContainerHelper setElectronicCases(List<ElectronicCaseDTO> electronicCaseDTOS) {
        NormalContainer.put(NormalContainer.ELECTRONIC_CASES, electronicCaseDTOS);
        return this;
    }

    public NormalContainerHelper setSelectedElectronicCase(ElectronicCaseDTO electronicCaseDTO) {
        NormalContainer.put(NormalContainer.SELECTED_ELECTRONIC_CASE, electronicCaseDTO);
        return this;
    }

    public NormalContainerHelper setElectronicCaseTypePos(Integer pos) {
        NormalContainer.put(NormalContainer.SELECTED_ELECTRONIC_CASE_TYPE_POS, pos);
        return this;
    }
}
