package com.nobitastudio.oss.fragment.login;

import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.container.NormalContainer;
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
    TextInputLayout mMobileEditText;
    @BindView(R.id.copyright_textview)
    TextView mCopyrightTextView;
    @BindView(R.id.attention_textview)
    TextView mAttentionTextView;

    @OnClick({R.id.next_step_button})
    void onClick(View v) {
        String code = mMobileEditText.getEditText().getText().toString().trim();  // 手机号或者诊疗卡卡号
        if (mNormalContainerHelper.getInputMobileFragment().equals(NormalContainer.InputMobileFor.BIND_MEDICAL_CARD)) {
            if (code.length() != 18) {
                showInfoTipDialog("请输入正确诊疗卡号");
            } else {
//                mNormalContainerHelper.setWaitBindMedicalCardNo(code);
//                mNormalContainerHelper.setInputMobile(mobile); // 通过网络获取medicalCard的信息
                startFragment(new VerificationCodeFragment());
            }
        } else {
            if (code.length() == 0) {
                showInfoTipDialog("请输入手机号");
            } else if (!RegexUtils.isMobileExact(code)) {
                showInfoTipDialog("请输入正确手机号");
            } else {
                mNormalContainerHelper.setInputMobile(code);
                startFragment(new VerificationCodeFragment());
            }
        }

    }

    private void initAttentionContent() {
        String mAttentionContent = "";
        switch (mNormalContainerHelper.getInputMobileFragment()) {
            case REGISTER:
                mAttentionContent = "请输入用于登录的手机号";
                break;
            case MODIFY_PASSWORD:
                mAttentionContent = "请输入用于登录的手机号";
                break;
            case CREATE_MEDICAL_CARD:
                mAttentionContent = "请输入诊疗卡持有者的手机号";
                break;
            case BIND_MEDICAL_CARD:
                mAttentionContent = "请输入待绑定诊疗卡卡号";
                break;
            default:
                mAttentionContent = "请输入手机号";
                break;
        }
        mAttentionTextView.setText(mAttentionContent);
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
        initAttentionContent();
        initCopyRight(mCopyrightTextView);
    }

}
