package com.nobitastudio.oss.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.model.common.ServiceResult;
import com.nobitastudio.oss.model.dto.LoginResult;
import com.nobitastudio.oss.model.entity.User;
import com.nobitastudio.oss.util.DateUtil;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class LoginFragment extends StandardWithTobBarLayoutFragment {

    static final int PHONE_NUM_LENGTH = 11;
    static final String PASSWORD_LENGTH_LESS = "密码长度太短";
    static final String PASSWORD_LENGTH_MORE = "密码长度过长";
    static final String ACCOUNT_IS_LESS = "请输入正确手机号";
    static final String DONT_INPUT_ACCOUNT = "请输入手机号";
    static final String DONT_INPUT_PASSWORD = "请输入密码";
    static final String MOBILE_OR_PASSWORD_ERROR = "账号或密码错误";
    static final String ACCESS_TIMEOUT = "网络连接超时";

    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.emptyView)
    QMUIEmptyView mEmptyView;
    @BindView(R.id.copyright_textView)
    TextView mCopyrightTextView;
    @BindView(R.id.user_mobile_edittext)
    EditText userMobileEditText;
    @BindView(R.id.user_password_editText)
    EditText userPasswordEditText;
    @BindView(R.id.login_button)
    Button loginButton;

    @OnClick({R.id.login_button, R.id.forget_password_textView, R.id.enroll_textView})
    void onclick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                userClickLogin();
                break;
            case R.id.forget_password_textView:
                startFragment(new ForgetPasswordOneFragment());
                break;
            case R.id.enroll_textView:
                break;
        }
    }

    private void userClickLogin() {
        String mobile = userMobileEditText.getText().toString();
        String password = userPasswordEditText.getText().toString();
        if (mobile.length() == 0) {
            showInfoTipDialog(DONT_INPUT_ACCOUNT);
        } else if (password.length() == 0) {
            showInfoTipDialog(DONT_INPUT_PASSWORD);
        } else if (mobile.length() != PHONE_NUM_LENGTH) {
            showInfoTipDialog(ACCOUNT_IS_LESS);
        } else if (password.length() < 6) {
            showInfoTipDialog(PASSWORD_LENGTH_LESS);
        } else if (password.length() > 16) {
            showInfoTipDialog(PASSWORD_LENGTH_MORE);
        } else {
            showNetworkLoadingTipDialog("正在验证");
            //give the user information to the server,get necessary information,if right go to mainactivity,otherwise,ack user to reinput
            if (userLogin(new User(mobile, password)).getState() != ServiceResult.STATE_APP_EXCEPTION) {
                // 登陆成功
                closeTipDialog();
                startFragmentAndDestroyCurrent(new HomeFragment());
            } else {
                // 登录失败
                closeTipDialog();
                showErrorTipDialog(MOBILE_OR_PASSWORD_ERROR);
                mEmptyView.postDelayed(() -> {
                    closeTipDialog();
                }, 1500);
            }
        }
    }

    /**
     * 网络请求
     *
     * @param user
     */
    private ServiceResult<LoginResult> userLogin(User user) {
        return ServiceResult.success(null);
    }

    @Override
    protected boolean canDragBack() {
        return false;
    }

    /**
     * 初始化 topbar
     */
    @Override
    protected void initTopBar() {
        // 切换其他情况的按钮
        mTopBar.setTitle("登录");
        mTopBar.addRightImageButton(R.mipmap.icon_topbar_about, R.id.topbar_right_about_button)
                .setOnClickListener(view -> startFragment(new AboutFragment()));
    }

    @Override
    public TransitionConfig onFetchTransitionConfig() {
        return SCALE_TRANSITION_CONFIG;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initLastCustom() {
        initCopyRight(mCopyrightTextView);
    }

}
