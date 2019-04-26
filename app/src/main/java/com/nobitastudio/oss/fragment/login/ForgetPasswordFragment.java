package com.nobitastudio.oss.fragment.login;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.fragment.home.HomeFragment;
import com.nobitastudio.oss.fragment.mine.UserInfoFragment;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.nobitastudio.oss.model.common.ServiceResult;
import com.nobitastudio.oss.model.dto.ReflectStrategy;
import com.nobitastudio.oss.model.entity.User;
import com.nobitastudio.oss.util.OkHttpUtil;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description 用户输入新密码 以及 确认密码 进入的时候可能是注册也可能是修改密码
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
        // clearAllFragment()
        String newPassword = mNewPasswordEditText.getText().toString().trim();
        String confirmPassword = mConfirmPasswordEditText.getText().toString().trim();
        if (newPassword.length() == 0 || confirmPassword.length() == 0) {
            showInfoTipDialog("请输入密码");
        } else if (newPassword.length() < 6 || newPassword.length() > 16 || confirmPassword.length() < 6 || confirmPassword.length() > 16) {
            showInfoTipDialog("密码格式错误,请重新输入");
        } else if (!newPassword.equals(confirmPassword)) {
            showInfoTipDialog("两次密码不匹配");
        } else {
            User user = new User();
            user.setMobile(mNormalContainerHelper.getInputMobile());
            user.setPassword(newPassword);
            switch (mNormalContainerHelper.getInputMobileFragment()) {
                case REGISTER:
                    showNetworkLoadingTipDialog("正在注册");
                    postAsyn(Arrays.asList("user", "enroll"), null, user, new ReflectStrategy<>(User.class),
                            new OkHttpUtil.SuccessHandler<User>() {
                                @Override
                                public void handle(User user) {
                                    // 注册成功  进行登录操作
                                    showSuccessTipDialog("注册成功,请登录");
                                    mNormalContainerHelper.clearAllButActivity(getBaseFragmentActivity()); // 先清除所有的内存已有数据
//                                        mNormalContainerHelper.setUser(user);
//                                        startFragmentAndDestroyCurrent(new HomeFragment());  返回到登录fragment
                                    popBackStack(LoginFragment.class);
                                }
                            });
                    break;
                case MODIFY_PASSWORD:
                    showNetworkLoadingTipDialog("正在修改密码");
                    putAsyn(Arrays.asList("user", "pw-by-sms"), null, user, new ReflectStrategy<>(User.class),
                            new OkHttpUtil.SuccessHandler<User>() {
                                @Override
                                public void handle(User user) {
                                    // 修改成功
                                    showSuccessTipDialog("密码修改成功");
                                    mNormalContainerHelper.clearAllButActivity(getBaseFragmentActivity()); // 先清除所有的内存已有数据
//                                    mNormalContainerHelper.setUser(user);
////                                    startFragmentAndDestroyCurrent(new HomeFragment());  返回到登录fragment重新执行登录操作
                                    if (mNormalContainerHelper.getUser() != null) {
                                        // 从登陆界面过来
                                        popBackStack(LoginFragment.class);
                                    } else {
                                        // 从用户设置界面过来
                                        popBackStack(UserInfoFragment.class);
                                    }
                                }
                            });
                    break;
            }
        }
    }

    @Override
    protected Boolean topBarIsTransparent() {
        return Boolean.TRUE;
    }

    @Override
    protected String getTopBarTitle() {
        return "设置密码";
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
