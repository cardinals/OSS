package com.nobitastudio.oss.fragment.home;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.bj.trpayjar.listener.PayResultListener;
import com.base.bj.trpayjar.utils.TrPay;
import com.blankj.utilcode.util.ToastUtils;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.fragment.home.RegisterSuccessFragment;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.nobitastudio.oss.util.PayUtil;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class WaitingPayRegisterFragment extends StandardWithTobBarLayoutFragment {

    public class AppPayResultListener implements PayResultListener {

        @Override
        public void onPayFinish(Context context, String s, int i, String s1, int i1, Long aLong, String s2) {

        }
    }

    @BindView(R.id.department_textview)
    TextView mDepartmentTextView;
    @BindView(R.id.doctor_name_textview)
    TextView mDoctorNameTextView;
    @BindView(R.id.diagnosis_date_textview)
    TextView mDiagnosisDateTextView;
    @BindView(R.id.diagnosis_time_textview)
    TextView mDiagnosisTimeTextView;
    @BindView(R.id.diagnosis_name_textview)
    TextView mDiagnosisNameTextView;
    @BindView(R.id.diagnosis_no_textview)
    TextView mDiagnosisNoTextView;
    @BindView(R.id.diagnosis_room_textview)
    TextView mDiagnosisRoomTextView;
    @BindView(R.id.register_cost_textview)
    TextView mDiagnosisCostTextView;

    @BindView(R.id.hosipital_info_solid_imageview)
    ImageView mHospitalInfoSolidImageView;
    @BindView(R.id.register_detail_solid_imageview)
    ImageView mRegisterDetailSolidImageView;

    @OnClick({R.id.cancel_register_button, R.id.pay_now_roundbutton})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_register_button:
                showMessageNegativeDialog("温馨提示", "挂号单取消后不可恢复，并且取消超过五次后将冻结该诊疗卡"
                        , "取消挂号单", (dialog, index) -> {
                            ToastUtils.showShort("您已成功取消本次预约挂号");
                            dialog.dismiss();
                        },
                        "再想想", (dialog, index) -> dialog.dismiss());
                break;
            case R.id.pay_now_roundbutton:
                showSimpleBottomSheetGrid(Arrays.asList(R.mipmap.ic_ali_pay, R.mipmap.wechat, R.mipmap.union_pay, R.mipmap.qq_pay),
                        Arrays.asList("支付宝", "微信", "云闪付", "QQ钱包"),
                        Arrays.asList(0, 1, 2, 3),
                        (dialog, itemView) -> {
                            dialog.dismiss();
                            callPay((Integer) itemView.getTag());
                            //startFragmentAndDestroyCurrent(new RegisterSuccessFragment());
                        }
                );
                break;
            default:
                break;
        }
    }

    private void callPay(Integer tag) {
        switch (tag) {
            case 0:
                PayUtil.callPay(PayUtil.PayChanel.ALI, getActivity(), "tradeName", "outTradeNo", 10l,
                        "11", "www.baidu.com", "11",
                        // 支付成功
                        (context, outTradeNo, resultString, payType, amount, tradeName) -> {
                            showSuccessTipDialog("支付成功");
                        },
                        // 支付失败
                        (context, outTradeNo, resultString, payType, amount, tradeName) -> {
                            showInfoTipDialog("支付失败");
                        });
                break;
            case 1:
                PayUtil.callPay(PayUtil.PayChanel.WX, getActivity(), "tradeName", "outTradeNo", 10l,
                        "11", "www.baidu.com", "11",
                        (context, outTradeNo, resultString, payType, amount, tradeName) -> {
                            // 支付成功
                            showSuccessTipDialog("支付成功");
                        },
                        (context, outTradeNo, resultString, payType, amount, tradeName) -> {
                            showInfoTipDialog("支付失败");
                        });
                break;
            case 2:
                Toasty.info(getContext(), "程序员小哥哥正在加紧开发中~").show();
                break;
            case 3:
                Toasty.info(getContext(), "程序员小哥哥正在加紧开发中~").show();
                break;
            default:
        }
    }

    @Override
    protected String getTopBarTitle() {
        return "预约挂号";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_waiting_pay_register;
    }

    @Override
    protected void initLastCustom() {
        initSolidImage(mHospitalInfoSolidImageView, mRegisterDetailSolidImageView);
    }

}
