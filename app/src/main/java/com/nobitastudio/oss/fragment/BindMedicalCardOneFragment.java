package com.nobitastudio.oss.fragment;

import android.view.View;
import android.widget.ImageView;

import com.nobitastudio.oss.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class BindMedicalCardOneFragment extends StandardWithTobBarLayoutFragment {

    @OnClick({R.id.next_step_button})
    void onClick(View v) {
        // clearAllFragment();
        startFragment(new BindMedicalCardTwoFragment());
    }

    @Override
    protected void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> this.popBackStack());
        mTopBar.setTitle("输入诊疗卡卡号");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bind_medical_card_one;
    }

    @Override
    protected void initLastCustom() {

    }
}
