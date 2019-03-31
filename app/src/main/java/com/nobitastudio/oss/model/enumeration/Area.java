package com.nobitastudio.oss.model.enumeration;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 13:11
 * @description
 */
public enum Area {
    A,
    B,
    C,
    D;

    public static String convertToChinese(Area area) {
        switch (area) {
            case A:
                return "A区";
            case B:
                return "B区";
            case C:
                return "C区";
            case D:
                return "D区";
            default:
                return "A区";
        }
    }
}
