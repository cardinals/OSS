package com.nobitastudio.oss.fragment.old;

import android.view.View;
import android.widget.Button;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class BindMedicalCardTwoFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.send_verification_code_button)
    Button mSendVerificationCodeButton;

    @OnClick({R.id.send_verification_code_button})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_verification_code_button:
                showNetworkLoadingTipDialog("正在发送验证码");
                mTopBar.postDelayed(() -> {
                    closeTipDialog();
                    showSuccessTipDialog("发送成功");
                },1500l);
                break;
        }
    }

    @Override
    protected String getTopBarTitle() {
        return "确认绑定";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bind_medical_card_two;
    }

    @Override
    protected void initLastCustom() {
        showNetworkLoadingTipDialog("正在发送验证码");
        mTopBar.postDelayed(() -> {
            closeTipDialog();
            showSuccessTipDialog("发送成功");
        },1500l);
    }
}
