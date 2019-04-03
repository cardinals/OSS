package com.nobitastudio.oss.util;

import android.app.Activity;
import android.content.Context;

import com.base.bj.trpayjar.domain.TrPayResult;
import com.base.bj.trpayjar.listener.PayResultListener;
import com.base.bj.trpayjar.utils.TrPay;
import com.nobitastudio.oss.fragment.home.WaitingPayRegisterFragment;

import es.dmoral.toasty.Toasty;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/14 20:33
 * @description 支付工具
 */
public class PayUtil {

    // 支付方式  支付宝 微信 银联(云闪付) QQ钱包  后面两个暂时不支持
    public enum PayChanel {
        WECHAT_PAY,
        ALI_PAY,
        UNION_PAY,
        QQ_WALLET,
//        HOSPITAL_MONEY,
//        HOSPITAL_MEDICAL_CAR
    }

    private static TrPay getTrPayInstance(Activity activity) {
        return TrPay.getInstance(activity);
    }

    // 支付成功处理
    public interface paySuccessHandle {
        void handle(Context context, String outTradeNo, String resultString, int payType, Long amount, String tradeName);
    }

    // 支付失败处理
    public interface payFailHandle {
        void handle(Context context, String outTradeNo, String resultString, int payType, Long amount, String tradeName);
    }

    /**
     * 调用支付
     *
     * @param payChanel  支付渠道
     * @param activity   上下文
     * @param tradename  商品名称
     * @param outtradeno 商户系统订单号(商户系统内唯一)
     * @param amount     商品价格（单位：分。如 1.5 元传 150）
     * @param backparams 商户系统回调参数
     * @param notifyurl  商户系统回调地址
     * @param userid     商户系统用户 ID(如：trpay@52yszd.com，商户系统内唯一)
     */
    public static void callPay(PayChanel payChanel, Activity activity, String tradename, String outtradeno, Long amount,
                               String backparams, String notifyurl, String userid,
                               paySuccessHandle paySuccessHandle, payFailHandle payFailHandle) {
        switch (payChanel) {
            case ALI_PAY:
                getTrPayInstance(activity).callAlipay(tradename, outtradeno, amount, backparams, notifyurl, userid,
                        (context, outTradeNo, resultCode, resultString, payType, amount12, tradeName) -> {
                            if (resultCode == TrPayResult.RESULT_CODE_SUCC.getId()) {
                                paySuccessHandle.handle(context, outTradeNo, resultString, payType, amount12, tradeName);
                            } else if (resultCode == TrPayResult.RESULT_CODE_FAIL.getId()) {
                                payFailHandle.handle(context, outTradeNo, resultString, payType, amount12, tradeName);
                            }
                        });
                break;
            case WECHAT_PAY:
                getTrPayInstance(activity).callWxPay(tradename, outtradeno, amount, backparams, notifyurl, userid,
                        (context, outTradeNo, resultCode, resultString, payType, amount1, tradeName) -> {
                            if (resultCode == TrPayResult.RESULT_CODE_SUCC.getId()) {
                                paySuccessHandle.handle(context, outTradeNo, resultString, payType, amount1, tradeName);
                            } else if (resultCode == TrPayResult.RESULT_CODE_FAIL.getId()) {
                                payFailHandle.handle(context, outTradeNo, resultString, payType, amount1, tradeName);
                            }
                        });
                break;
            case UNION_PAY:
                Toasty.info(activity.getApplicationContext(), "程序员小哥哥正在加紧开发中~").show();
                break;
            case QQ_WALLET:
                Toasty.info(activity.getApplicationContext(), "程序员小哥哥正在加紧开发中~").show();
                break;
            default:
                break;
        }
    }


}
