package com.nobitastudio.oss.fragment.login;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.container.NormalContainer;
import com.nobitastudio.oss.fragment.about.AboutFragment;
import com.nobitastudio.oss.fragment.home.HomeFragment;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.nobitastudio.oss.model.common.ServiceResult;
import com.nobitastudio.oss.model.common.error.ErrorCode;
import com.nobitastudio.oss.model.dto.ReflectStrategy;
import com.nobitastudio.oss.model.entity.User;
import com.nobitastudio.oss.util.OkHttpUtil;
import com.qmuiteam.qmui.util.QMUIPackageHelper;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class LoginFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.copyright_textview)
    TextView mCopyrightTextView;
    @BindView(R.id.user_mobile_edittext)
    EditText userMobileEditText;
    @BindView(R.id.user_password_editText)
    EditText userPasswordEditText;
    @BindView(R.id.version_textview)
    TextView versionTextView;

    @OnClick({R.id.login_button, R.id.chioces_button,
            R.id.qq_login_imageview, R.id.wechat_login_imageview, R.id.weibo_login_imageview})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                userClickLogin();
                break;
            case R.id.chioces_button:
                showSimpleBottomSheetGrid(Arrays.asList(R.mipmap.ic_enroll, R.mipmap.ic_lock, R.mipmap.ic_app_icon),
                        Arrays.asList("注册", "找回密码", "关于我们"),
                        Arrays.asList(0, 1, 2),
                        (dialog, itemView) -> {
                            if (itemView.getTag().equals(0)) {
                                mNormalContainerHelper.setInputMobileFragment(NormalContainer.InputMobileFor.REGISTER);
                                startFragment(new InputMobileFragment());
                            } else if (itemView.getTag().equals(1)) {
                                mNormalContainerHelper.setInputMobileFragment(NormalContainer.InputMobileFor.MODIFY_PASSWORD);
                                startFragment(new InputMobileFragment());
                            } else if (itemView.getTag().equals(2)) {
                                startFragment(new AboutFragment());
                            }
                            dialog.dismiss();
                        });
                break;
            case R.id.qq_login_imageview:
            case R.id.wechat_login_imageview:
            case R.id.weibo_login_imageview:
                showInfoTipDialog("敬请期待");
                break;
        }
    }

    private void userClickLogin() {
        String mobile = userMobileEditText.getText().toString().trim();
        String password = userPasswordEditText.getText().toString().trim();
        if (mobile.length() == 0) {
            showInfoTipDialog("请输入手机号");
        } else if (password.length() == 0) {
            showInfoTipDialog("请输入密码");
        } else if (!RegexUtils.isMobileExact(mobile)) {
            showInfoTipDialog("请输入正确手机号");
        } else if (password.length() < 6 || password.length() > 16) {
            showInfoTipDialog("密码格式错误,请重新输入");
        } else {
            showNetworkLoadingTipDialog("正在验证");
            //give the user information to the server,get necessary information,if right go to mainactivity,otherwise,ack user to reinput
            userLogin(new User(mobile, password));
        }
    }

    // 将 手机号 密码发送至服务器端进行验证.返回全部user信息
    private void userLogin(User user) {
        showNetworkLoadingTipDialog("正在验证");
        postAsyn(Arrays.asList("test", "test-login"), null, user, new ReflectStrategy<>(User.class),
                userResult -> {
                    LoginFragment.this.closeTipDialog();
                    mNormalContainerHelper.clearAllButActivity(getBaseFragmentActivity());
                    mNormalContainerHelper.setUser(userResult);
                    LoginFragment.this.startFragmentAndDestroyCurrent(new HomeFragment());
                });
    }

    @Override
    protected boolean canDragBack() {
        return false;
    }

    @Override
    public TransitionConfig onFetchTransitionConfig() {
        return SCALE_TRANSITION_CONFIG;
    }

    @Override
    protected Boolean topBarIsTransparent() {
        return Boolean.TRUE;
    }

    @Override
    protected String getTopBarTitle() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initLastCustom() {
        initCopyRight(mCopyrightTextView);
        versionTextView.setText(QMUIPackageHelper.getAppVersion(getContext()));
        // 通过glide 加载gif 到背景中
    }

}
