package com.nobitastudio.oss.model.entity;


import com.nobitastudio.oss.model.enumeration.DoctorLevel;

import java.io.Serializable;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 13:40
 * @description 医生实例
 */
public class Doctor implements Serializable {

    private static final long serialVersionUID = -1767052683247139655L;

    public Doctor() {
        this(1, "1", "1", "1", "1", DoctorLevel.ASSOCIATE_PROFESSOR, 1);
    }

    public Doctor(Integer id, String name, String specialty, String subMajor, String introduction, DoctorLevel level, Integer departmentId) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.subMajor = subMajor;
        this.introduction = introduction;
        this.level = level;
        this.departmentId = departmentId;
    }

    private Integer id;

    private String name;

    private String specialty;

    private String subMajor;

    private String introduction;

    private DoctorLevel level;

    private Integer departmentId;
}
