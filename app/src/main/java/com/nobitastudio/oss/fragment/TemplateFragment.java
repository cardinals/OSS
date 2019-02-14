package com.nobitastudio.oss.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.nobitastudio.oss.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TemplateFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.solidImage)
    ImageView solidImage;

    @Override
    protected View onCreateView() {
        FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_standard, null);
        ButterKnife.bind(this,frameLayout);
        return frameLayout;
    }

    @Override
    protected void init() {
        initSolidImage(solidImage);
        initTopBar();
        initRefreshLayout();
        initData();
    }

    @Override
    protected void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> this.popBackStack());
        mTopBar.setTitle("title");
    }

    @Override
    protected void initRefreshLayout() {
        mPullRefreshLayout.setEnabled(false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected View.OnClickListener getEmptyViewRetryButtonListener() {
        return v -> ToastUtils.showShort("点击了emptyView" + v);
    }
}
