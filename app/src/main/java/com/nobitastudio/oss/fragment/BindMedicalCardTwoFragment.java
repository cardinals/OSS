package com.nobitastudio.oss.fragment;

import android.widget.ImageView;

import com.nobitastudio.oss.R;

import butterknife.BindView;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class BindMedicalCardTwoFragment extends StandardWithTobBarLayoutFragment {

    @Override
    protected void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> this.popBackStack());
        mTopBar.setTitle("确认绑定");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bind_medical_card_two;
    }

    @Override
    protected void initLastCustom() {
    }
}
