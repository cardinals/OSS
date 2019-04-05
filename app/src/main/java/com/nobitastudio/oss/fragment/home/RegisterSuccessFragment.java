package com.nobitastudio.oss.fragment.home;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.nobitastudio.oss.model.dto.ConfirmOrCancelRegisterDTO;
import com.nobitastudio.oss.model.dto.ReflectStrategy;
import com.nobitastudio.oss.util.OkHttpUtil;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import java.util.Arrays;

import butterknife.BindView;
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
                showMessageNegativeDialog("温馨提示", "挂号单取消后不可恢复，并且取消超过五次后将冻结该诊疗卡，挂号费将按照原支付方式进行退回。"
                        , "取消挂号单", (dialog, index) -> {
                            dialog.dismiss();
                            cancelRegistration();
                        },
                        "再想想", (dialog, index) -> dialog.dismiss());
                break;
            default:
                showErrorTipDialog("未处理的点击事件");
                break;
        }
    }

    // 取消挂号单
    private void cancelRegistration() {
        showNetworkLoadingTipDialog("正在为您取消本次预约");
        getAsyn(Arrays.asList("registration-record", mNormalContainerHelper.getRegistrationRecord().getId(), "cancel"), null,
                new ReflectStrategy<>(ConfirmOrCancelRegisterDTO.class),
                new OkHttpUtil.SuccessHandler<ConfirmOrCancelRegisterDTO>() {
                    @Override
                    public void handle(ConfirmOrCancelRegisterDTO confirmOrCancelRegisterDTO) {
                        mNormalContainerHelper.settRegistrationRecord(confirmOrCancelRegisterDTO.getRegistrationRecord());
                        mNormalContainerHelper.setOrder(confirmOrCancelRegisterDTO.getOssOrder());
                        showSuccessTipDialog("取消预约成功");
                        popBackStack();
                    }
                });
    }

    @Override
    protected String getTopBarTitle() {
        return "挂号成功";
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
