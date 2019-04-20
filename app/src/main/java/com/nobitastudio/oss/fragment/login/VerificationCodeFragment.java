package com.nobitastudio.oss.fragment.login;

import android.view.View;
import android.widget.TextView;

import com.mukesh.OtpView;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.fragment.home.CreateMedicalCardFragment;
import com.nobitastudio.oss.fragment.old.ForgetPasswordTwoFragment;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.nobitastudio.oss.model.common.ServiceResult;
import com.nobitastudio.oss.model.dto.ConfirmValidateCodeDTO;
import com.nobitastudio.oss.model.dto.ReflectStrategy;
import com.nobitastudio.oss.model.dto.RequestValidateCodeDTO;
import com.nobitastudio.oss.model.dto.StandardInfo;
import com.nobitastudio.oss.model.enumeration.SmsMessageType;
import com.nobitastudio.oss.util.DateUtil;
import com.nobitastudio.oss.util.OkHttpUtil;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description 验证码fragment  根据情况跳转至更改密码界面，注册界面.创建诊疗卡界面
 */
public class VerificationCodeFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.send_to_mobile_textview)
    TextView mSendToMobileTextView;
    @BindView(R.id.otp_view)
    OtpView mOtpView;
    @BindView(R.id.copyright_textview)
    TextView mCopyrightTextView;
    @BindView(R.id.resend_textview)
    TextView mResendTextView;

    Timer mLeftTimeControllerTimer;
    Integer mLeftTime;

    @OnClick({R.id.resend_textview})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.resend_textview:
                if (mLeftTime.equals(0)) {
                    showSimpleBottomSheetList(Arrays.asList("重新获取验证码", "接听语音验证码"),
                            (dialog, itemView, position, tag) -> {
                                switch (position) {
                                    case 0:
                                        reSendSmsToMobile();
                                        break;
                                    case 1:
                                        showInfoTipDialog("程序员小哥哥由于996正在ICU,敬请期待");
                                        break;
                                }
                                dialog.dismiss();
                            });
                } else {
                    // 2分钟才能发送一次
                    showInfoTipDialog("还有" + mLeftTime + "秒您才可重新发送验证码");
                }
                break;
        }
    }

    // 重新发送验证码到指定账户
    private void reSendSmsToMobile() {
        mLeftTime = 120; // 默认120秒
        showNetworkLoadingTipDialog("正在发送");
        mSendToMobileTextView.setText("正在发送验证码");
        RequestValidateCodeDTO requestValidateCodeDTO = new RequestValidateCodeDTO();
        requestValidateCodeDTO.setMobile(mNormalContainerHelper.getInputMobile());
        switch (mNormalContainerHelper.getInputMobileFragment()) {
            case REGISTER:
                requestValidateCodeDTO.setSmsMessageType(SmsMessageType.ENROLL);
                break;
            case CREATE_MEDICAL_CARD:
                requestValidateCodeDTO.setSmsMessageType(SmsMessageType.CREATE_MEDICAL_CARD);
                break;
            case MODIFY_PASSWORD:
                requestValidateCodeDTO.setSmsMessageType(SmsMessageType.UPDATE_PASSWORD);
                break;
            case BIND_MEDICAL_CARD:
                requestValidateCodeDTO.setSmsMessageType(SmsMessageType.BIND_MEDICAL_CARD);
                break;
        }
        putAsyn(Arrays.asList("sms-validate", "request-code"), null, requestValidateCodeDTO, new ReflectStrategy<>(StandardInfo.class)
                , new OkHttpUtil.SuccessHandler<StandardInfo>() {
                    @Override
                    public void handle(StandardInfo s) {
                        showSuccessTipDialog("发送成功");
                        mSendToMobileTextView.setText(generateSendToMobile());
                        mLeftTimeControllerTimer = new Timer();
                        mLeftTimeControllerTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(() -> {
                                    if (mLeftTime < 1) {
                                        mResendTextView.setText("重新获取验证码/收不到验证码点这里");
                                        mLeftTimeControllerTimer.cancel();
                                    } else {
                                        mResendTextView.setText(mLeftTime + "秒后重新获取验证码/收不到验证码点这里");
                                    }
                                });
                                mLeftTime--;
                            }
                        }, 0, 1000l);
                    }
                }, new OkHttpUtil.FailHandler<StandardInfo>() {
                    @Override
                    public void handle(ServiceResult<StandardInfo> serviceResult) {
                        showInfoTipDialog("发送失败,请重试");
                        mLeftTime = 0;
                        mResendTextView.setText("重新获取验证码/收不到验证码点这里");
                    }
                });
    }

    private String generateSendToMobile() {
        return "验证码已发送至 +86 " + mNormalContainerHelper.getInputMobile().substring(0,3) + "****" + mNormalContainerHelper.getInputMobile().substring(7,11);
    }

    private void initOptView() {
        mOtpView.setOtpCompletionListener((value) -> {
            showNetworkLoadingTipDialog("正在验证");
            ConfirmValidateCodeDTO confirmValidateCodeDTO = new ConfirmValidateCodeDTO(mNormalContainerHelper.getInputMobile(), value);
            putAsyn(Arrays.asList("sms-validate", "confirm-code"), null, confirmValidateCodeDTO, new ReflectStrategy<>(StandardInfo.class)
                    , new OkHttpUtil.SuccessHandler<StandardInfo>() {
                        @Override
                        public void handle(StandardInfo s) {
                            // 根据情况跳转至相应的fragment
                            closeTipDialog();
                            switch (mNormalContainerHelper.getInputMobileFragment()) {
                                case MODIFY_PASSWORD:
                                case REGISTER:
                                    startFragmentAndDestroyCurrent(new ForgetPasswordFragment());
                                    break;
                                case CREATE_MEDICAL_CARD:
                                    startFragmentAndDestroyCurrent(new CreateMedicalCardFragment());
                                    break;
                                case BIND_MEDICAL_CARD:
                                    bindMedicalCard();
                                    break;
                            }
                        }
                    });
        });
    }

    // 调用bind接口 绑定操作
    private void bindMedicalCard() {


    }

    @Override
    protected String getTopBarTitle() {
        return "验证";
    }

    @Override
    protected Boolean topBarIsTransparent() {
        return Boolean.TRUE;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_verification_code;
    }

    @Override
    protected void initLastCustom() {
        reSendSmsToMobile();  // 进入时即发送给指定用户
        initOptView();
        initCopyRight(mCopyrightTextView);
    }
}
