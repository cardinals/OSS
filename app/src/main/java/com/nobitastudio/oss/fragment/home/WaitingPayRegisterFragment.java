package com.nobitastudio.oss.fragment.home;

import android.content.Context;
import android.database.DatabaseUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.bj.trpayjar.listener.PayResultListener;
import com.base.bj.trpayjar.utils.TrPay;
import com.blankj.utilcode.util.ToastUtils;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.container.ConstantContainer;
import com.nobitastudio.oss.fragment.home.RegisterSuccessFragment;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.nobitastudio.oss.model.common.ServiceResult;
import com.nobitastudio.oss.model.common.error.ErrorCode;
import com.nobitastudio.oss.model.dto.ConfirmOrCancelRegisterDTO;
import com.nobitastudio.oss.model.dto.ReflectStrategy;
import com.nobitastudio.oss.model.entity.OSSOrder;
import com.nobitastudio.oss.model.enumeration.OrderState;
import com.nobitastudio.oss.util.DateUtil;
import com.nobitastudio.oss.util.OkHttpUtil;
import com.nobitastudio.oss.util.PayUtil;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

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
    @BindView(R.id.left_time_textview)
    TextView mLeftTimeTextView;
    @BindView(R.id.hosipital_info_solid_imageview)
    ImageView mHospitalInfoSolidImageView;
    @BindView(R.id.register_detail_solid_imageview)
    ImageView mRegisterDetailSolidImageView;
    @BindView(R.id.left_time_solid_imageview)
    ImageView mLeftTimeSolidImageView;
    @BindView(R.id.attention_imageview)
    ImageView mAttentionSolidImageView;

    Timer mLeftTimeControllerTimer;
    Integer mLeftTime;

    @OnClick({R.id.cancel_register_button, R.id.pay_now_roundbutton})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_register_button:
                showMessageNegativeDialog("温馨提示", "挂号单取消后不可恢复，并且取消超过五次后将冻结该诊疗卡"
                        , "取消挂号单", (dialog, index) -> {
                            dialog.dismiss();
                            cancelRegistration();
                        },
                        "再想想", (dialog, index) -> dialog.dismiss());
                break;
            case R.id.pay_now_roundbutton:
                if (mLeftTime > 0) {
                    showPayChannel();
                } else {
                    showInfoTipDialog("该挂号单因逾期未支付已作废");
                }
                break;
            default:
                break;
        }
    }

    // 取消挂号单
    private void cancelRegistration() {
        showNetworkLoadingTipDialog("正在为您取消本次预约");
        getAsyn(Arrays.asList("registration-record", mNormalContainerHelper.getRegistrationRecord().getId(), "cancel"), null,
                new ReflectStrategy<>(ConfirmOrCancelRegisterDTO.class),
                new OkHttpUtil.SuccessHandler<ConfirmOrCancelRegisterDTO>() {
                    @Override
                    public void handle(ConfirmOrCancelRegisterDTO confirmOrCancelRegisterDTO) {
                        mNormalContainerHelper.settRegistrationRecord(confirmOrCancelRegisterDTO.getRegistrationRecord());
                        mNormalContainerHelper.setOrder(confirmOrCancelRegisterDTO.getOssOrder());
                        showSuccessTipDialog("取消预约成功");
                        popBackStack();
                    }
                });
    }

    // 展示支付渠道
    private void showPayChannel() {
        showSimpleBottomSheetGrid(Arrays.asList(R.mipmap.ic_ali_pay, R.mipmap.wechat, R.mipmap.union_pay, R.mipmap.qq_pay),
                Arrays.asList("支付宝", "微信", "云闪付", "QQ钱包"),
                Arrays.asList(0, 1, 2, 3),
                (dialog, itemView) -> {
                    dialog.dismiss();
                    // 请求服务器查看支付时间
                    showNetworkLoadingTipDialog("正在准备支付");
                    getAsyn(Arrays.asList("order", mNormalContainerHelper.getSelectedMedicalCard().getId(), mNormalContainerHelper.getSelectedVisit().getId().toString()),
                            null, new ReflectStrategy<OSSOrder>(OSSOrder.class),
                            new OkHttpUtil.SuccessHandler<OSSOrder>() {
                                @Override
                                public void handle(OSSOrder ossOrder) {
                                    if (ossOrder.getCreateTime().plusMinutes(30).isAfter(LocalDateTime.now())) {
                                        // 在支付时间内
                                        mNormalContainerHelper.setOrder(ossOrder);
                                        closeTipDialog();
                                        callPay((Integer) itemView.getTag());
                                    } else {
                                        // 已过支付时间
                                        showInfoTipDialog("该挂号单因逾期未支付已作废");
                                    }
                                }
                            });
//                    startFragmentAndDestroyCurrent(new RegisterSuccessFragment());
                }
        );
    }

    private void callPay(Integer tag) {
        switch (tag) {
            case 0:
//                Double cost = mNormalContainerHelper.getSelectedVisit().getCost() * 100;
                Double cost = 10.0;
                PayUtil.callPay(PayUtil.PayChanel.ALI_PAY, getActivity(), "挂号费", mNormalContainerHelper.getOrder().getId(), cost.longValue(),
                        mNormalContainerHelper.getRegistrationRecord().getId(), ConstantContainer.OSS_PAY_CALLBACK_URL, mNormalContainerHelper.getUser().getId().toString(),
                        // 支付成功
                        (context, outTradeNo, resultString, payType, amount, tradeName) -> {
                            mLeftTimeControllerTimer.cancel();
                            mLeftTimeTextView.setText("已支付");
                            validateOrderStatus();
                        },
                        // 支付失败
                        (context, outTradeNo, resultString, payType, amount, tradeName) -> {
                            showInfoTipDialog("请尽快完成支付");
                        });
                break;
            case 1:
                PayUtil.callPay(PayUtil.PayChanel.WECHAT_PAY, getActivity(), "tradeName", "outTradeNo", 10L,
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

    // 验证订单是否支付
    private void validateOrderStatus() {
        showNetworkLoadingTipDialog("正在检查支付状态");
        getSync(Arrays.asList("order", "status", mNormalContainerHelper.getOrder().getId()), null, new ReflectStrategy<OSSOrder>(OSSOrder.class),
                new OkHttpUtil.SuccessHandler<OSSOrder>() {
                    @Override
                    public void handle(OSSOrder ossOrder) {
                        if (ossOrder.getState().equals(OrderState.HAVE_PAY)) {
                            mNormalContainerHelper.setOrder(ossOrder);
                            showSuccessTipDialog("支付成功");
                            startFragmentAndDestroyCurrent(new RegisterSuccessFragment());
                        } else {
                            showInfoTipDialog("当前挂号单尚未支付,若已支付请下拉刷新",2000l);
                        }
                    }
                });
        mPullRefreshLayout.finishRefresh();
    }

    // 初始化基础数据
    private void initBasic() {
        mDepartmentTextView.setText(mNormalContainerHelper.getSelectedDepartment().getName());
        mDoctorNameTextView.setText(mNormalContainerHelper.getSelectedDoctor().getName());
        mDiagnosisDateTextView.setText(DateUtil.convertToStandardDate(mNormalContainerHelper.getSelectedVisit().getDiagnosisTime()));
        mDiagnosisTimeTextView.setText(DateUtil.convertToStandardTime(mNormalContainerHelper.getSelectedVisit().getDiagnosisTime()));
        mDiagnosisNameTextView.setText(mNormalContainerHelper.getSelectedMedicalCard().getOwnerName());
        mDiagnosisNoTextView.setText(mNormalContainerHelper.getRegistrationRecord().getDiagnosisNo() + " 号");
        mDiagnosisRoomTextView.setText(mNormalContainerHelper.getSelectedDepartment().getAddress());
        mDiagnosisCostTextView.setText(mNormalContainerHelper.getSelectedVisit().getCost().toString() + " 元");
        mLeftTime = mNormalContainerHelper.getLeftTime();

        // 开启定时器.默认30分钟支付时间.不支付则自动销毁至上一个fragment
        mLeftTimeControllerTimer = new Timer();
        mLeftTimeControllerTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    mLeftTimeTextView.setText(DateUtil.formatSecondsToStandardString(mLeftTime));
                    if (mLeftTime.equals(0)) {
                        // 不可支付
                        mLeftTimeTextView.setText("已超时");
                        mLeftTimeControllerTimer.cancel();
                        showInfoTipDialog("该挂号单因逾期未支付已作废");
                    }
                    // 最后2分钟时，进行提醒
                    if (mLeftTime.equals(120)) {
                        showMessageNegativeDialog("提醒", "您还剩两分钟可支付。请尽快支付。逾期作废",
                                "立即支付", (dialog, index) -> {
                                    dialog.dismiss();
                                    showPayChannel();
                                },
                                "知道了", (dialog, index) -> dialog.dismiss());
                    }
                });
                mLeftTime--;
            }
        }, 0, 1000l);
    }

    @Override
    protected void initRefreshLayout() {
        mPullRefreshLayout.setEnabled(true);
        mPullRefreshLayout.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override
            public void onMoveTarget(int offset) {

            }

            @Override
            public void onMoveRefreshView(int offset) {

            }

            @Override
            public void onRefresh() {
                validateOrderStatus();
            }
        });
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
        initBasic();
        initSolidImage(mHospitalInfoSolidImageView, mRegisterDetailSolidImageView, mLeftTimeSolidImageView, mAttentionSolidImageView);
    }

}
