package com.nobitastudio.oss.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.nobitastudio.oss.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestFragment extends StandardWithTobBarLayoutFragment {

    @OnClick({R.id.login_button})
    void onClick(View v) {
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initTopBar() {

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
        FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_test, null);
        ButterKnife.bind(this,frameLayout);
        return frameLayout;
    }
}
