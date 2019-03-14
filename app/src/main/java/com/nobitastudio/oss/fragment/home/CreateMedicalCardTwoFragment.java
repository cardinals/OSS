package com.nobitastudio.oss.fragment.home;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.fragment.home.CreateMedicalCardThreeFragment;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description 用户输入验证码 再验证成功后可以视为用户通过验证码登录成功,应当收到 accessToken.
 * 再通过accessToken 在 ForgetPasswordThreeFragment 中调用修改密码的接口.
 */
public class CreateMedicalCardTwoFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.verification_code_edittext)
    EditText mVerificationCodeEditText;
    @BindView(R.id.send_verification_code_button)
    Button mSendVerificationCodeButton;

    @OnClick({R.id.next_step_button, R.id.send_verification_code_button})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_verification_code_button:
                showNetworkLoadingTipDialog("正在发送验证码");
                mTopBar.postDelayed(() -> {
                    closeTipDialog();
                    showSuccessTipDialog("发送成功");
                }, 1500l);
                break;
            case R.id.next_step_button:
                startFragmentAndDestroyCurrent(new CreateMedicalCardThreeFragment());
                break;
        }

    }

    @Override
    protected String getTopBarTitle() {
        return "请输入验证码";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_forget_passwor_two;
    }

    @Override
    protected void initLastCustom() {
        // send sms verificationCode to user
        showNetworkLoadingTipDialog("正在发送验证码");
        mTopBar.postDelayed(() -> {
            closeTipDialog();
            showSuccessTipDialog("发送成功");
        }, 1500l);
    }
}
