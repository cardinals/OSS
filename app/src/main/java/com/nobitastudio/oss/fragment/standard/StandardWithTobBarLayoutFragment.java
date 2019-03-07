package com.nobitastudio.oss.fragment.standard;

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

}
