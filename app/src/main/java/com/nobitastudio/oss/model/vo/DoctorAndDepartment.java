package com.nobitastudio.oss.model.vo;

import com.nobitastudio.oss.model.entity.Department;
import com.nobitastudio.oss.model.entity.Doctor;
import com.nobitastudio.oss.model.entity.Visit;
import com.nobitastudio.oss.model.enumeration.Area;
import com.nobitastudio.oss.model.enumeration.DoctorLevel;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/15 22:57
 * @description 医生以及对应的科室以及号源信息 -- 从我的收藏进入时
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorAndDepartment implements Serializable {

    private static final long serialVersionUID = 559299959620878568L;

    /**
     * 对连表结果强转时进行调用
     */
    public DoctorAndDepartment(Integer doctorId, String doctorName, String doctorSpecialty, String doctorSubMajor, String doctorIntroduction,  // docor
                               String doctorLevel, String iconUrl, Integer doctorDepartmentId,
                               Integer departmentId, String departmentName, String departmentAddress, Integer departmentLocation,  // department
                               Integer departmentFloor, String area, String departmentIntroduction) {
        this.doctor = new Doctor(doctorId, doctorName, doctorSpecialty, doctorSubMajor, doctorIntroduction, DoctorLevel.valueOf(doctorLevel), iconUrl, doctorDepartmentId);
        this.department = new Department(departmentId, departmentName, departmentAddress, departmentLocation, departmentFloor, Area.valueOf(area), departmentIntroduction);
    }

    // 医生信息
    private Doctor doctor;
    // 科室信息
    private Department department;
}
