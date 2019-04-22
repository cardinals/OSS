package com.nobitastudio.oss.model.enumeration;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/10 12:00
 * @description
 */
public enum PaymentChannel {
    WECHAT_PAY,
    ALI_PAY,
    UNION_PAY,
    QQ_WALLET,
    HOSPITAL_MONEY,
    HOSPITAL_MEDICAL_CAR,
    UNDEFINED;

    public static String getChineseMean(PaymentChannel paymentChannel) {
        if (paymentChannel == null) {
            return "未支付";
        }
        switch (paymentChannel) {
            case ALI_PAY:
                return "支付宝";
            case QQ_WALLET:
                return "QQ钱包";
            case UNDEFINED:
                return "未定义";
            case UNION_PAY:
                return "银联支付";
            case WECHAT_PAY:
                return "微信支付";
            case HOSPITAL_MONEY:
                return "现金支付";
            case HOSPITAL_MEDICAL_CAR:
                return "诊疗卡支付";
        }
        return "";
    }
}
