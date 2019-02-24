package com.nobitastudio.oss.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.nobitastudio.oss.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description 用户输入验证码 再验证成功后可以视为用户通过验证码登录成功,应当收到 accessToken.
 * 再通过accessToken 在 ForgetPasswordThreeFragment 中调用修改密码的接口.
 */
public class ForgetPasswordTwoFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.verification_code_edittext)
    EditText mVerificationCodeEditText;

    @OnClick({R.id.next_step_button})
    void onClick(View v) {
        startFragmentAndDestroyCurrent(new ForgetPasswordThreeFragment());
    }

    @Override
    protected void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> this.popBackStack());
        mTopBar.setTitle("请输入验证码");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_forget_passwor_two;
    }

    @Override
    protected void initLastCustom() {

    }
}
