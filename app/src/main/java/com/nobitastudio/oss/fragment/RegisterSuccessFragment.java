package com.nobitastudio.oss.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.nobitastudio.oss.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class RegisterSuccessFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.hosipital_info_solid_imageview)
    ImageView mHospitalInfoSolidImageView;
    @BindView(R.id.register_detail_solid_imageview)
    ImageView mRegisterDetailSolidImageView;
    @BindView(R.id.warm_prompt_solid_imageview)
    ImageView mWarmPromptSolidImageView;
    @BindView(R.id.cancel_register_button)
    Button mCancelRegisterRoundButton;

    @OnClick({R.id.cancel_register_button})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_register_button:
                showMessageNegativeDialog("提示", "挂号单取消后不可恢复，并且取消超过五次后将冻结该诊疗卡，挂号费将按照原支付方式进行退回。"
                        , "取消挂号单", (dialog, index) -> {
                            ToastUtils.showShort("您已成功取消本次预约挂号");
                            dialog.dismiss();
                        },
                        "再想想", (dialog, index) -> dialog.dismiss());
                break;
            default:
                showErrorTipDialog("未处理的点击事件");
                break;
        }
    }

    @Override
    protected void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> popBackStack());
        mTopBar.setTitle("挂号成功");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register_success;
    }

    @Override
    protected void initLastCustom() {
        initSolidImage(mHospitalInfoSolidImageView, mRegisterDetailSolidImageView, mWarmPromptSolidImageView);
    }

}