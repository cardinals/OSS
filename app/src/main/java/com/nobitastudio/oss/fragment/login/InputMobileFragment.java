package com.nobitastudio.oss.fragment.login;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
        startFragment(new VerificationCodeFragment());
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
        initCopyRight(mCopyrightTextView);
    }
}
