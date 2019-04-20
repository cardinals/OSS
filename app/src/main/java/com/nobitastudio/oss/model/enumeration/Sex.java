package com.nobitastudio.oss.model.enumeration;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 16:15
 * @description
 */
public enum Sex {
    MALE,
    FEMALE,
    UNKNOWN;

    public static String getChineseSex(Sex sex) {
        switch (sex) {
            case MALE:
                return "男";
            case FEMALE:
                return "女";
            case UNKNOWN:
            default:
                return "未知";
        }
    }

    public static Sex getFromChinese(String sex) {
        if (sex.equals("男")) {
            return Sex.MALE;
        } else if (sex.equals("女")) {
            return Sex.FEMALE;
        } else {
            return Sex.UNKNOWN;
        }
    }
}
