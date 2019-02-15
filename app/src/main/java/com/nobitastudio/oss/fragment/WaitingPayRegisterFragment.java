package com.nobitastudio.oss.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.nobitastudio.oss.R;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.cancel_register_button)
    QMUIRoundButton mCancelRegisterRoundButton;
    @BindView(R.id.pay_now_roundbutton)
    QMUIRoundButton mPayNowRoundButton;

    @BindView(R.id.hosipital_info_solid_imageview)
    ImageView mHospitalInfoSolidImageView;
    @BindView(R.id.register_detail_solid_imageview)
    ImageView mRegisterDetailSolidImageView;

    @OnClick({R.id.cancel_register_button, R.id.pay_now_roundbutton})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_register_button:
                showMessageNegativeDialog(getContext(), "提示", "挂号单取消后不可恢复，并且取消超过五次后将冻结该诊疗卡"
                        , "取消挂号单", (dialog, index) -> {
                            ToastUtils.showShort("您已成功取消本次预约挂号");
                            dialog.dismiss();
                        },
                        "再想想", (dialog, index) -> dialog.dismiss());
                break;
            case R.id.pay_now_roundbutton:
                showSimpleBottomSheetGrid(getContext(), Arrays.asList(R.mipmap.ali_pay, R.mipmap.wechat, R.mipmap.union_pay, R.mipmap.qq_pay),
                        Arrays.asList("支付宝", "微信", "云闪付", "QQ钱包"),
                        Arrays.asList(1, 2, 3, 4),
                        (dialog, itemView) -> {
                            Integer tag = (Integer) itemView.getTag();
                            dialog.dismiss();
                            startFragmentAndDestroyCurrent(new RegisterSuccessFragment());
                        }
                );
                break;
            default:
                break;
        }
    }

    @Override
    protected void init() {
        initSolidImage(mHospitalInfoSolidImageView, mRegisterDetailSolidImageView);
        initTopBar();
        initRefreshLayout();
        initData();
    }

    @Override
    protected void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> popBackStack());
        mTopBar.setTitle("预约挂号");
    }

    @Override
    protected void initRefreshLayout() {
        mPullRefreshLayout.setEnabled(false);
    }

    @Override
    protected void initData() {
        mEmptyView.hide();
    }

    @Override
    protected View.OnClickListener getEmptyViewRetryButtonListener() {
        return null;
    }

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_waiting_pay_register, null);
        ButterKnife.bind(this, root);
        init();
        return root;
    }
}
