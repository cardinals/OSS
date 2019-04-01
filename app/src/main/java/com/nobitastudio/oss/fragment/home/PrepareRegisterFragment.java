package com.nobitastudio.oss.fragment.home;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.container.NormalContainer;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.nobitastudio.oss.model.entity.Department;
import com.nobitastudio.oss.model.entity.Doctor;
import com.nobitastudio.oss.model.entity.MedicalCard;
import com.nobitastudio.oss.model.entity.Visit;
import com.nobitastudio.oss.util.DateUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class PrepareRegisterFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.department_textview)
    TextView mDepartmentTextView;
    @BindView(R.id.doctor_name_textview)
    TextView mDoctorNameTextView;
    @BindView(R.id.diagnosis_date_textview)
    TextView mDiagnosisDateTextView;
    @BindView(R.id.diagnosis_time_textview)
    TextView mDiagnosisTimeTextView;
    @BindView(R.id.diagnosis_room_textview)
    TextView mDiagnosisRoomTextView;  // 诊室位置  暂时填充为科室的位置
    @BindView(R.id.diagnosis_cost_textview)
    TextView mDiagnosisCostTextView;
    @BindView(R.id.choose_medical_card_textview)
    TextView mChooseMedicalCardTextView;
    @BindView(R.id.medical_card_ownername_linearlayout)
    LinearLayout mMedicalCardOwnerNameLinearLayout;
    @BindView(R.id.verification_code_edittext)
    EditText mVerificationCodeEditText;
    @BindView(R.id.register_basic_msg_imageview)
    ImageView mRegisterBasicMsgSolidImageView;

    List<MedicalCard> mBindMedicalCards;

    @OnClick({R.id.choose_medical_card_textview, R.id.choose_medical_card_imageview,
            R.id.confirm_register_msg})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_medical_card_textview:
            case R.id.choose_medical_card_imageview:
                isBindMedicalCard();
                break;
            case R.id.confirm_register_msg:
                showMessagePositiveDialog("温馨提示", "请在30分钟内支付，逾期作废\n逾期超5次将会冻结就诊卡",
                        "取消", (dialog, index) -> dialog.dismiss(),
                        "我知道了", (dialog, index) -> {
                            dialog.dismiss();
                            if (isChooseMedicalCard()) {
                                // 选择了诊疗卡
                                if (isInputVerificationCode()) {
                                    // 输入了验证码
                                    if (isVerificationCodeRight()) {
                                        // 验证码是正确的,进入准备支付
                                        startFragment(new WaitingPayRegisterFragment());
                                    }
                                }
                            }
                        });
                break;
            default:
                showErrorTipDialog("不支持的点击事件");
                break;
        }
    }

    // 正常的初始化操作
    private void intBasic() {
        mBindMedicalCards = NormalContainer.get(NormalContainer.BIND_MEDICAL_CARD);

        mDepartmentTextView.setText(NormalContainer.get(NormalContainer.SELECTED_DEPARTMENT, Department.class).getName());
        mDoctorNameTextView.setText(NormalContainer.get(NormalContainer.SELECTED_DOCTOR, Doctor.class).getName());
        mDiagnosisDateTextView.setText(DateUtil.convertToStandardDate(NormalContainer.get(NormalContainer.SELECTED_VISIT, Visit.class).getDiagnosisTime()));
        mDiagnosisTimeTextView.setText(DateUtil.convertToStandardTime(NormalContainer.get(NormalContainer.SELECTED_VISIT, Visit.class).getDiagnosisTime()));
        mDiagnosisRoomTextView.setText(NormalContainer.get(NormalContainer.SELECTED_DEPARTMENT, Department.class).getAddress());
        mDiagnosisCostTextView.setText(NormalContainer.get(NormalContainer.SELECTED_VISIT, Visit.class).getCost().toString() + " 元");
    }

    /**
     * 判断用户是否存在有绑定的诊疗卡
     *
     * @return
     */
    private Boolean isBindMedicalCard() {
        if (mBindMedicalCards == null || mBindMedicalCards.isEmpty()) {
            // 未绑定诊疗卡
            showMessagePositiveDialog("温馨提示", "您尚未绑定任何诊疗卡,请完成诊疗卡绑定后再进行挂号操作。",
                    "取消", (dialog, index) -> {
                        dialog.dismiss();
                    }, "立即绑定/创建", (dialog, index) -> {
                        dialog.dismiss();
                        startFragment(new MedicalCardFragment());
                    });
        } else {
            // 已有绑定的诊疗卡
            List<String> items = mBindMedicalCards.stream().map(MedicalCard::getOwnerName).collect(Collectors.toList());
            showListPopView(mChooseMedicalCardTextView, items, 120, 160,
                    (parent, view, position, id) -> {
                        mChooseMedicalCardTextView.setText(items.get(position));
                        NormalContainer.put(NormalContainer.SELECTED_MEDICAL_CARD,mBindMedicalCards.get(position));
                        popViewDismiss();
                    },
                    null);
        }
        return Boolean.FALSE;
    }

    /**
     * 用户是否选择了诊疗卡
     *
     * @return
     */
    private Boolean isChooseMedicalCard() {
        if (Boolean.TRUE) {
            // 未选择诊疗卡
            showInfoTipDialog("请选择就诊人");
        }
        return Boolean.TRUE;
        //return ContainerUtil.getMedicalCard() == null ? Boolean.FALSE : Boolean.TRUE;
    }

    /**
     * 判断用户是否输入了验证码
     *
     * @return
     */
    private Boolean isInputVerificationCode() {
        if (Boolean.TRUE) {
            // 未输入验证码
            showInfoTipDialog("请输入验证码");
        }
        return Boolean.TRUE;
        //return mVerificationCodeEditText.getText().toString().trim().isEmpty();
    }

    private Boolean isVerificationCodeRight() {
        if (Boolean.TRUE) {
            // 如果不是正确的
            showErrorTipDialog("验证码错误,请重新输入");
        }
        return Boolean.TRUE;
    }

    @Override
    protected String getTopBarTitle() {
        return "预约挂号";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_prepare_register;
    }

    @Override
    protected void initLastCustom() {
        intBasic();
        initSolidImage(mRegisterBasicMsgSolidImageView);
    }

}
