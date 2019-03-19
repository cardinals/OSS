package com.nobitastudio.oss.fragment.standard;

import android.view.View;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.fragment.standard.StandardFragment;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import butterknife.BindView;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/30 14:41
 * @description
 */
public abstract class StandardWithTobBarLayoutFragment extends StandardFragment {

    @BindView(R.id.topbar)
    protected QMUITopBarLayout mTopBar;

    @Override
    protected void initTopBar() {
        if (isSpecialTopBar()) {
            // 特殊topbar 特殊处理
            handleSpecialTopBar();
        } else {
            // 正常
            if (canDragBack()) {
                mTopBar.addLeftBackImageButton().setOnClickListener(view -> popBackStack());
            }
            mTopBar.setBackgroundDividerEnabled(topBarHavDivider());
            mTopBar.setTitle(getTopBarTitle());
            if (topBarIsTransparent()) {
                mTopBar.setBackgroundColor(getResources().getColor(R.color.qmui_config_color_transparent, null));
            }
            initTopBarLast();
        }
    }

    @Override
    protected View getTopBar() {
        return mTopBar;
    }
}
