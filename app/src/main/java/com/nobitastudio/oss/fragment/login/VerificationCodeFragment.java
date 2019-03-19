package com.nobitastudio.oss.fragment.login;

import android.view.View;
import android.widget.TextView;

import com.mukesh.OtpView;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.fragment.home.CreateMedicalCardFragment;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description 验证码fragment
 */
public class VerificationCodeFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.send_to_mobile_textview)
    TextView mSendToMobileTextView;
    @BindView(R.id.otp_view)
    OtpView mOtpView;

    @OnClick({R.id.resend_textview})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.resend_textview:
                showSimpleBottomSheetList(Arrays.asList("重新获取验证码", "接听语音验证码"),
                        (dialog, itemView, position, tag) -> {
                            dialog.dismiss();
                            Toasty.success(getContext(), position + tag).show();
                        });
                break;
        }
    }

    private void initOptView() {
        mOtpView.setOtpCompletionListener((value) -> {
            startFragmentAndDestroyCurrent(new CreateMedicalCardFragment());
        });
    }

    @Override
    protected String getTopBarTitle() {
        return "验证";
    }

    @Override
    protected Boolean topBarIsTransparent() {
        return Boolean.TRUE;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_verification_code;
    }

    @Override
    protected void initLastCustom() {
        initOptView();
    }
}
