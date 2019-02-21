package com.nobitastudio.oss.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.nobitastudio.oss.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class TestFragment extends StandardWithTobBarLayoutFragment {

    @OnClick({R.id.login_button})
    void onClick(View v) {

    }

    @Override
    protected void initTopBar() {
        mTopBar.setTitle("test");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    protected void initLastCustom() {

    }
}
