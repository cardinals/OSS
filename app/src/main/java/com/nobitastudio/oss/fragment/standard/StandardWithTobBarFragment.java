package com.nobitastudio.oss.fragment.standard;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.fragment.standard.StandardFragment;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/30 14:41
 * @description
 */
public abstract class StandardWithTobBarFragment extends StandardFragment {

    @BindView(R.id.topbar)
    protected QMUITopBar mTopBar;




}
