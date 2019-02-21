package com.nobitastudio.oss.fragment;

import android.widget.ImageView;

import com.nobitastudio.oss.R;

import butterknife.BindView;

public class UserInfoFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.solidImage)
    ImageView solidImage;

    @Override
    protected void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> this.popBackStack());
        mTopBar.setTitle("个人资料");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_standard;
    }

    @Override
    protected void initLastCustom() {
        initSolidImage(solidImage);
    }
}
