package com.nobitastudio.oss.fragment.test;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import butterknife.BindView;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class Test2Fragment extends StandardWithTobBarLayoutFragment {

    private float mShadowAlpha = 1.0f;
    private int mShadowElevationDp = 10;
    private int mRadius = 15;

    @BindView(R.id.QMUILinearLayout)
    QMUILinearLayout linearLayout;

    @Override
    protected void initTopBar() {
        mTopBar.setTitle("test");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test2;
    }

    @Override
    protected void initLastCustom() {
        linearLayout.setRadiusAndShadow(QMUIDisplayHelper.dp2px(getContext(), mRadius),
                QMUIDisplayHelper.dp2px(getContext(), mShadowElevationDp), mShadowAlpha);
    }
}
