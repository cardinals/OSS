package com.nobitastudio.oss.model.enumeration;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/10 12:06
 * @description 业务领域(订单中各类子项的类型) 对应于订单名称,订单id等生成
 */
public enum ItemType {
    // 挂号订单
    REGISTER,
    // 邮寄订单
    EXPRESS,
    //药品订单
    DRUG,
    // 检查订单
    CHECK,
    // 手术订单
    OPERATION,
    // 住院订单
    HOSPITALIZE,
    // 诊疗卡类型
    MEDICAL_CARD;

    // 根据位置获取对应枚举值
    public static ItemType getFromPosition(int pos) {
        for (ItemType s : ItemType.values()) {
            if (pos == s.ordinal()) {
                return s;
            }
        }
        return null;
    }

}
