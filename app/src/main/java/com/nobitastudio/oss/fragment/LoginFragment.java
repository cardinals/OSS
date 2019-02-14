package com.nobitastudio.oss.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.model.common.ServiceResult;
import com.nobitastudio.oss.model.dto.LoginResult;
import com.nobitastudio.oss.model.entity.User;
import com.nobitastudio.oss.util.DateUtil;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.user_mobile_editText)
    EditText userMobileEditText;
    @BindView(R.id.user_password_editText)
    EditText userPasswordEditText;
    @BindView(R.id.forget_password_textView)
    TextView forgetPasswordTextView;
    @BindView(R.id.enroll_textView)
    TextView enrollTextView;
    @BindView(R.id.login_button)
    Button loginButton;

    @OnClick({R.id.login_button, R.id.forget_password_textView, R.id.enroll_textView})
    void onclick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                userClickLogin();
                break;
            case R.id.forget_password_textView:
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
                mQmuiTipDialog.hide();
                showErrorTipDialog(MOBILE_OR_PASSWORD_ERROR);
                mEmptyView.postDelayed(() -> {
                    mQmuiTipDialog.hide();
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

    /**
     * 初始化版权信息
     */
    private void initCopyRight() {
        mCopyrightTextView.setText(String.format(getResources().getString(R.string.about_copyright), DateUtil.getCurrentYear()));
    }

    @Override
    protected void init() {
        initTopBar();
        initRefreshLayout();
        initData();
    }

    /**
     * 初始化 topbar
     */
    protected void initTopBar() {
        // 切换其他情况的按钮
        mTopBar.setTitle("登录");
        mTopBar.addRightImageButton(R.mipmap.icon_topbar_about, R.id.topbar_right_about_button)
                .setOnClickListener(view -> startFragment(new AboutFragment()));
    }

    @Override
    protected void initRefreshLayout() {
        mPullRefreshLayout.setEnabled(false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected View.OnClickListener getEmptyViewRetryButtonListener() {
        return null;
    }

    @Override
    protected View onCreateView() {
        FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_login, null);
        ButterKnife.bind(this,frameLayout);
        init();
        return frameLayout;
    }
}
