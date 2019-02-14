package com.nobitastudio.oss.model.vo;

import com.nobitastudio.oss.model.entity.Department;
import com.nobitastudio.oss.model.entity.Doctor;
import com.nobitastudio.oss.model.enumeration.Area;
import com.nobitastudio.oss.model.enumeration.DoctorLevel;

import java.io.Serializable;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/15 22:57
 * @description 医生以及对应的科室的封装对象
 */
public class DoctorAndDepartment implements Serializable {

    private static final long serialVersionUID = 559299959620878568L;

    /**
     * 对连表结果强转时进行调用
     */
    public DoctorAndDepartment(Integer doctorId, String doctorName, String doctorSpecialty, String doctorSubMajor, String doctorIntroduction,  // docor
                               String doctorLevel, Integer doctorDepartmentId,
                               Integer departmentId, String departmentName, String departmentAddress, Integer departmentLocation,  // department
                               Integer departmentFloor, String area, String departmentIntroduction) {
        this.doctor = new Doctor(doctorId, doctorName, doctorSpecialty, doctorSubMajor, doctorIntroduction, DoctorLevel.valueOf(doctorLevel), doctorDepartmentId);
        this.department = new Department(departmentId, departmentName, departmentAddress, departmentLocation, departmentFloor, Area.valueOf(area), departmentIntroduction);
    }

    private Doctor doctor;
    private Department department;
}
