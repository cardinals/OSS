package com.nobitastudio.oss.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.nobitastudio.oss.R;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @BindView(R.id.hosipital_info_solid_imageview)
    ImageView mHospitalInfoSolidImageView;
    @BindView(R.id.register_detail_solid_imageview)
    ImageView mRegisterDetailSolidImageView;

    @OnClick({R.id.cancel_register_button, R.id.pay_now_roundbutton})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_register_button:
                showMessageNegativeDialog("提示", "挂号单取消后不可恢复，并且取消超过五次后将冻结该诊疗卡"
                        , "取消挂号单", (dialog, index) -> {
                            ToastUtils.showShort("您已成功取消本次预约挂号");
                            dialog.dismiss();
                        },
                        "再想想", (dialog, index) -> dialog.dismiss());
                break;
            case R.id.pay_now_roundbutton:
                showSimpleBottomSheetGrid(Arrays.asList(R.mipmap.ic_ali_pay, R.mipmap.wechat, R.mipmap.union_pay, R.mipmap.qq_pay),
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
    protected void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> popBackStack());
        mTopBar.setTitle("预约挂号");
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
