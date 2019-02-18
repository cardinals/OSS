package com.nobitastudio.oss.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.nobitastudio.oss.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class Test3Fragment extends StandardWithTobBarLayoutFragment {

    @OnClick({R.id.login_button})
    void onClick(View v) {
        
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initTopBar() {
        mTopBar.setTitle("3");
    }

    @Override
    protected void initRefreshLayout() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected View.OnClickListener getEmptyViewRetryButtonListener() {
        return null;
    }

    @Override
    protected View onCreateView() {
        FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_test3, null);
        ButterKnife.bind(this,frameLayout);
        initTopBar();
        return frameLayout;
    }
}
