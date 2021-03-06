package com.nobitastudio.oss.fragment.old;

import android.view.View;
import android.widget.EditText;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.fragment.old.ForgetPasswordTwoFragment;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description 用户输入需要修改密码的手机号
 */
public class ForgetPasswordOneFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.user_mobile_edittext)
    EditText mUserMobileEditText;

    @OnClick({R.id.next_step_button})
    void onClick(View v) {
        startFragment(new ForgetPasswordTwoFragment());
    }

    @Override
    protected String getTopBarTitle() {
        return "请输入手机号";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_forget_passwor_one;
    }

    @Override
    protected void initLastCustom() {

    }
}
