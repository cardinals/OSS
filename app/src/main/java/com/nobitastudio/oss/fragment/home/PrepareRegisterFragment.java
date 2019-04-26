package com.nobitastudio.oss.fragment.home;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.container.NormalContainer;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.nobitastudio.oss.model.common.ServiceResult;
import com.nobitastudio.oss.model.common.error.ErrorCode;
import com.nobitastudio.oss.model.dto.ReflectStrategy;
import com.nobitastudio.oss.model.dto.RegisterDTO;
import com.nobitastudio.oss.model.dto.RegistrationAll;
import com.nobitastudio.oss.model.entity.MedicalCard;
import com.nobitastudio.oss.model.entity.RegistrationRecord;
import com.nobitastudio.oss.util.DateUtil;
import com.nobitastudio.oss.util.OkHttpUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

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
    @BindView(R.id.captcha_imageview)
    ImageView mCaptchaImageView;

    List<MedicalCard> mBindMedicalCards;
    boolean isRequestingForImageCaptcha;
    Call mCall; // 验证码请求;  用于用户重复请求.重复请求时取消上一个请求.

    @OnClick({R.id.choose_medical_card_textview, R.id.choose_medical_card_imageview,
            R.id.confirm_register_button, R.id.captcha_imageview})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_medical_card_textview:
            case R.id.choose_medical_card_imageview:
                isBindMedicalCard();
                break;
            case R.id.confirm_register_button:
                if (isChooseMedicalCard()) {
                    // 选择了诊疗卡
                    if (isInputVerificationCode()) {
                        // 输入了验证码.准备挂号
                        showMessagePositiveDialog("温馨提示", getString(R.string.register_attention_before),
                                "取消", (dialog, index) -> dialog.dismiss(),
                                "我知道了", (dialog, index) -> {
                                    dialog.dismiss();
                                    requestForRegister();
                                });
                    }
                }
                break;
            case R.id.captcha_imageview:
                requestForImageCaptcha();
                break;
        }
    }

    // 请求服务器进行挂号操作
    private void requestForRegister() {
        showNetworkLoadingTipDialog("正在为您预约挂号");
        postAsyn(Arrays.asList("registration-record", "register"), null,
                new RegisterDTO(mNormalContainerHelper.getUser().getId(),
                        mNormalContainerHelper.getSelectedMedicalCard().getId(),
                        mNormalContainerHelper.getSelectedVisit().getId(),
                        mVerificationCodeEditText.getText().toString().trim()),
                new ReflectStrategy<>(RegistrationAll.class),
                new OkHttpUtil.SuccessHandler<RegistrationAll>() {
                    @Override
                    public void handle(RegistrationAll registrationAll) {
                        closeTipDialog();
                        mNormalContainerHelper.setRegistrationRecord(registrationAll.getRegistrationRecord());
                        mNormalContainerHelper.setOrder(registrationAll.getOssOrder());
                        mNormalContainerHelper.setLeftTime(1800);  // 新建的挂号单.默认支付时间是30分钟
                        requestForImageCaptcha(); // 刷新验证码
                        startFragment(new WaitingPayRegisterFragment());
                    }
                }, new OkHttpUtil.FailHandler<RegistrationAll>() {
                    @Override
                    public void handle(ServiceResult<RegistrationAll> serviceResult) {
                        showInfoTipDialog(ErrorCode.get(serviceResult.getErrorCode()));
                        requestForImageCaptcha();// 刷新验证码
                    }
                });
    }

    // 请求验证码
    private void requestForImageCaptcha() {
        mCaptchaImageView.setBackground(getResources().getDrawable(R.drawable.bg_verificationcode, null)); // 重置验证码图片
        if (mCall != null && isRequestingForImageCaptcha) {
            mCall.cancel();  // 取消上一个call
        }
        isRequestingForImageCaptcha = true;
        mCall = OkHttpUtil.getImageAsyn(Arrays.asList("image-validate", mNormalContainerHelper.getUser().getId().toString()), null,
                new OkHttpUtil.ConnectFailHandler() {
                    @Override
                    public void handle(Call call, IOException e) {
                        showInfoTipDialog("验证码获取失败");
                        isRequestingForImageCaptcha = false;
                    }
                }, new OkHttpUtil.SuccessHandler<Bitmap>() {
                    @Override
                    public void handle(Bitmap bitmap) {
                        isRequestingForImageCaptcha = false;
                        runOnUiThread(() -> Glide.with(getContext()).load(bitmap).into(mCaptchaImageView));
                    }
                }, getErrorHandler());

        // 初始化验证码

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
                        mNormalContainerHelper.setEnterMedicalCardFor(NormalContainer.EnterMedicalCardFor.NORMAL);
                        startFragment(new MedicalCardFragment());
                    });
        } else {
            // 已有绑定的诊疗卡
            List<String> items = mBindMedicalCards.stream().map(MedicalCard::getOwnerName).collect(Collectors.toList());
            showListPopView(mChooseMedicalCardTextView, items, 120, 160,
                    (parent, view, position, id) -> {
                        mChooseMedicalCardTextView.setText(items.get(position));
                        mNormalContainerHelper.setSelectedMedicalCard(mBindMedicalCards.get(position));
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
        if (mChooseMedicalCardTextView.getText().equals("请选择")) {
            showInfoTipDialog("请选择就诊人");
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    /**
     * 判断用户是否输入了验证码
     *
     * @return
     */
    private Boolean isInputVerificationCode() {
        if (mVerificationCodeEditText.getText().toString().trim().isEmpty()) {
            // 未输入
            showInfoTipDialog("请输入验证码");
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    // 正常的初始化操作
    private void intBasic() {
        isRequestingForImageCaptcha = false;
        mDepartmentTextView.setText(mNormalContainerHelper.getSelectedDepartment().getName());
        mDoctorNameTextView.setText(mNormalContainerHelper.getSelectedDoctor().getName());
        mDiagnosisDateTextView.setText(DateUtil.convertToStandardDate(mNormalContainerHelper.getSelectedVisit().getDiagnosisTime()));
        mDiagnosisTimeTextView.setText(DateUtil.convertToStandardTime(mNormalContainerHelper.getSelectedVisit().getDiagnosisTime()));
        mDiagnosisRoomTextView.setText(mNormalContainerHelper.getSelectedDepartment().getAddress());
        mDiagnosisCostTextView.setText(mNormalContainerHelper.getSelectedVisit().getCost().toString() + " 元");
        mBindMedicalCards = mNormalContainerHelper.getBindMedicalCards();

        requestForImageCaptcha();
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
