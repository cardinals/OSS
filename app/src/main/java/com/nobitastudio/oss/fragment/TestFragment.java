package com.nobitastudio.oss.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.nobitastudio.oss.R;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class TestFragment extends StandardWithTobBarLayoutFragment {

    private float mShadowAlpha = 1.0f;
    private int mShadowElevationDp = 10;
    private int mRadius = 15;

    @BindView(R.id.QMUILinearLayout)
    QMUILinearLayout linearLayout;
    @BindView(R.id.groupListView)
    QMUIGroupListView mQMUIGroupListView;

    @OnClick({R.id.register_remind_linearlayout,R.id.pay_info_linearlayout,R.id.hospital_announce_linearlayout})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_remind_linearlayout:
                break;
            case R.id.pay_info_linearlayout:
                break;
            case R.id.hospital_announce_linearlayout:
                break;
        }
    }


    @Override
    protected void initTopBar() {
        mTopBar.setTitle("消息");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    protected void initLastCustom() {
        linearLayout.setRadiusAndShadow(QMUIDisplayHelper.dp2px(getContext(), mRadius),
                QMUIDisplayHelper.dp2px(getContext(), mShadowElevationDp), mShadowAlpha);
    }
}
