package com.nobitastudio.oss.fragment.login;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.fragment.home.HomeFragment;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description 用户输入新密码 以及 确认密码
 */
public class ForgetPasswordFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.new_password_editText)
    EditText mNewPasswordEditText;
    @BindView(R.id.confirm_password_editText)
    EditText mConfirmPasswordEditText;
    @BindView(R.id.copyright_textview)
    TextView mCopyrightTextView;

    @OnClick({R.id.confirm_modify_password})
    void onClick(View v) {
        // 清除所有fragment 进入homeFragment
        // clearAllFragment();
        startFragmentAndDestroyCurrent(new HomeFragment());
    }

    @Override
    protected Boolean topBarIsTransparent() {
        return Boolean.TRUE;
    }

    @Override
    protected String getTopBarTitle() {
        return "确认修改";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_forget_passwor;
    }

    @Override
    protected void initLastCustom() {
        initCopyRight(mCopyrightTextView);
    }
}
