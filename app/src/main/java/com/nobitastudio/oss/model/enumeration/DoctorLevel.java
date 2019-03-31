package com.nobitastudio.oss.model.enumeration;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 13:43
 * @description
 */
public enum DoctorLevel {
    //一级专家
    ONE,
    //二级专家
    TWO,
    //三级专家
    THREE,
    //"教授
    PROFESSOR,
    //副教授
    ASSOCIATE_PROFESSOR,
    //主治医师
    ATTENDING_PHYSICIAN;

    public static String translateToString(DoctorLevel doctorLevel) {
        switch (doctorLevel) {
            case ONE:
                return "一级专家";
            case TWO:
                return "二级专家";
            case THREE:
                return "三级专家";
            case PROFESSOR:
                return "教授";
            case ASSOCIATE_PROFESSOR:
                return "副教授";
            case ATTENDING_PHYSICIAN:
                return "主治医师";
            default:
                return "医生";
        }
    }

}
