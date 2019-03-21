package com.nobitastudio.oss.fragment.login;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.RegexUtils;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.fragment.BaseFragment;
import com.nobitastudio.oss.base.helper.BottomSheetHelper;
import com.nobitastudio.oss.base.helper.TipDialogHelper;
import com.nobitastudio.oss.fragment.about.AboutFragment;
import com.nobitastudio.oss.fragment.home.HomeFragment;
import com.nobitastudio.oss.model.common.ServiceResult;
import com.nobitastudio.oss.model.dto.LoginResult;
import com.nobitastudio.oss.model.entity.User;
import com.nobitastudio.oss.util.DateUtil;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class LoginFragment extends BaseFragment {

    static final String MOBILE_OR_PASSWORD_ERROR = "账号或密码错误";

    @BindView(R.id.copyright_textview)
    TextView mCopyrightTextView;
    @BindView(R.id.user_mobile_edittext)
    EditText userMobileEditText;
    @BindView(R.id.user_password_editText)
    EditText userPasswordEditText;

    TipDialogHelper mTipDialogHelper;
    BottomSheetHelper mBottomSheetHelper;

    @OnClick({R.id.login_button, R.id.chioces_button})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                userClickLogin();
                break;
            case R.id.chioces_button:
                mBottomSheetHelper.showSimpleBottomSheetGrid(Arrays.asList(R.mipmap.ic_enroll, R.mipmap.ic_lock, R.mipmap.ic_app_icon),
                        Arrays.asList("注册", "找回密码", "关于我们"),
                        Arrays.asList(1, 2, 3),
                        (dialog, itemView) -> {
                            if (itemView.getTag().equals(1)) {
                                startFragment(new InputMobileFragment());
                            } else if (itemView.getTag().equals(2)) {
                                startFragment(new InputMobileFragment());
                            } else if (itemView.getTag().equals(3)) {
                                startFragment(new AboutFragment());
                            }
                            dialog.dismiss();
                        });
                break;
        }
    }

    private void userClickLogin() {
        String mobile = userMobileEditText.getText().toString();
        String password = userPasswordEditText.getText().toString();
        if (!RegexUtils.isMobileExact(mobile.trim())) {
            mTipDialogHelper.showInfoTipDialog("请输入正确手机号", mCopyrightTextView);
        } else if (password.trim().length() < 6 || password.trim().length() > 16) {
            mTipDialogHelper.showInfoTipDialog("密码格式错误,请重新输入", mCopyrightTextView);
        } else {
            mTipDialogHelper.showNetworkLoadingTipDialog("正在验证");
            //give the user information to the server,get necessary information,if right go to mainactivity,otherwise,ack user to reinput
            if (userLogin(new User(mobile, password))) {
                // 登陆成功
                mTipDialogHelper.closeTipDialog();
                startFragmentAndDestroyCurrent(new HomeFragment());
            } else {
                // 登录失败
                mTipDialogHelper.closeTipDialog();
                mTipDialogHelper.showErrorTipDialog(MOBILE_OR_PASSWORD_ERROR, mCopyrightTextView);
                mCopyrightTextView.postDelayed(() -> {
                    mTipDialogHelper.closeTipDialog();
                }, 1500);
            }
        }
    }

    private Boolean userLogin(User user) {

        return Boolean.FALSE;
    }

    @Override
    protected boolean canDragBack() {
        return false;
    }

    protected void initLastCustom() {
        mTipDialogHelper = new TipDialogHelper(getContext());
        mBottomSheetHelper = new BottomSheetHelper(getContext());
        mCopyrightTextView.setText(String.format(getResources().getString(R.string.about_copyright), DateUtil.getCurrentYear()));
        // 通过glide 加载gif 到背景中
    }

    @Override
    public TransitionConfig onFetchTransitionConfig() {
        return SCALE_TRANSITION_CONFIG;
    }

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_login, null);
        ButterKnife.bind(this, root);
        initLastCustom();
        return root;
    }

}
