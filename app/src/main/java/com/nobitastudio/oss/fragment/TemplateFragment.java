package com.nobitastudio.oss.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.nobitastudio.oss.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class TemplateFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.solidImage)
    ImageView solidImage;

    @Override
    protected void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> this.popBackStack());
        mTopBar.setTitle("title");
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
