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
 * @description 用户输入新密码 以及 确认密码
 */
public class ForgetPasswordThreeFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.new_password_editText)
    EditText mNewPasswordEditText;
    @BindView(R.id.confirm_password_editText)
    EditText mConfirmPasswordEditText;

    @OnClick({R.id.confirm_modify_password})
    void onClick(View v) {
        // 清除所有fragment 进入homeFragment
        // clearAllFragment();
        startFragmentAndDestroyCurrent(new HomeFragment());
    }

    @Override
    protected void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> this.popBackStack());
        mTopBar.setTitle("确认修改");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_forget_passwor_three;
    }

    @Override
    protected void initLastCustom() {

    }
}
