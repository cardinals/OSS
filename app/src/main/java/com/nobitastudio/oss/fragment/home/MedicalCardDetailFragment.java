package com.nobitastudio.oss.fragment.home;

import android.view.View;
import android.widget.ImageView;

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
public class MedicalCardDetailFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.hosipital_info_solid_imageview)
    ImageView mHospitalInfoSolidImageView;
    @BindView(R.id.medical_card_detail_solid_imageview)
    ImageView mMedicalCardDetailSolidImageView;
    @BindView(R.id.warm_prompt_solid_imageview)
    ImageView mWarmPromptSolidImageView;

    @OnClick({R.id.unbind_medical_card_button})
    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.unbind_medical_card_button:
                showMessageNegativeDialog("温馨提示", "1.\n2.\n3.",
                        "取消绑定", (dialog, pos) -> dialog.dismiss(),
                        "再想想", (dialog, pos) -> dialog.dismiss());
            default:
                break;
        }
    }

    private void initMedicalCardDetail() {
        // 初始化 textView
    }

    @Override
    protected void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> this.popBackStack());
        mTopBar.setTitle("诊疗卡持有者姓名");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_medical_card_detail;
    }

    @Override
    protected void initLastCustom() {
        initSolidImage(mHospitalInfoSolidImageView, mMedicalCardDetailSolidImageView, mWarmPromptSolidImageView);
        initMedicalCardDetail();

    }
}
