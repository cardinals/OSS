package com.nobitastudio.oss.model.enumeration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/10 11:46
 * @description 订单状态
 */
public enum OrderState {
    HAVE_PAY,
    WAITING_PAY,
    CANCEL_PAY,
    AUTO_CANCEL_PAY;

    public static List<String> getChineseMeans() {
        List<String> chineseMean = new ArrayList<>();
        for (OrderState s : OrderState.values()) {
            if (s.equals(OrderState.HAVE_PAY)) {
                chineseMean.add("已支付");
            } else if (s.equals(OrderState.WAITING_PAY)) {
                chineseMean.add("待支付");
            } else if (s.equals(OrderState.CANCEL_PAY)) {
                chineseMean.add("已取消");
            } else if (s.equals(OrderState.AUTO_CANCEL_PAY)) {
                chineseMean.add("自动取消");
            }
        }
        return chineseMean;
    }

    public static String getChineseMean(OrderState state) {
        switch (state) {
            case HAVE_PAY:
                return "已支付";
            case CANCEL_PAY:
                return "已取消";
            case WAITING_PAY:
                return "等待支付";
            case AUTO_CANCEL_PAY:
                return "自动取消";
            default:
                return "未知状态";
        }
    }

    // 根据位置获取对应枚举值
    public static OrderState getFromPosition(int pos) {
        for (OrderState s : OrderState.values()) {
            if (pos == s.ordinal()) {
                return s;
            }
        }
        return null;
    }
}
