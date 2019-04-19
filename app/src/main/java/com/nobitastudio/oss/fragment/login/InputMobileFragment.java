package com.nobitastudio.oss.fragment.login;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description 用户输入需要修改密码的手机号
 */
public class InputMobileFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.mobile_edittext)
    EditText mMobileEditText;
    @BindView(R.id.copyright_textview)
    TextView mCopyrightTextView;

    @OnClick({R.id.next_step_button})
    void onClick(View v) {
        String mobile = mMobileEditText.getText().toString().trim();
        if (mobile.length() == 0) {
            showInfoTipDialog("请输入手机号");
        } else if (!RegexUtils.isMobileExact(mobile)) {
            showInfoTipDialog("请输入正确手机号");
        } else {
            mNormalContainerHelper.setInputMobile(mobile);
            startFragment(new VerificationCodeFragment());
        }
    }

    private void initMobileEditTextHint() {
        String mHint = "";
        switch (mNormalContainerHelper.getInputMobileFragment()) {
            case REGISTER:
                mHint = "请输入手机号";
                break;
            case MODIFY_PASSWORD:
                mHint = "请输入绑定的手机号";
                break;
            case CREATE_MEDICAL_CARD:
                mHint = "请输入诊疗卡持有者的手机号";
                break;
            default:
                mHint = "请输入手机号";
                break;
        }
        mMobileEditText.setHint(mHint);
    }

    @Override
    protected Boolean topBarIsTransparent() {
        return Boolean.TRUE;
    }

    @Override
    protected String getTopBarTitle() {
        return "输入手机号";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_input_mobile;
    }

    @Override
    protected void initLastCustom() {
        initMobileEditTextHint();
        initCopyRight(mCopyrightTextView);
    }

}
