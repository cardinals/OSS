package com.nobitastudio.oss.fragment;

import android.os.Bundle;

import com.nobitastudio.oss.R;
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
    QMUITopBar mTopBar;




}
