package com.nobitastudio.oss.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.util.DateUtil;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class GratitudeFragment extends StandardWithTobBarLayoutFragment {


    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.copyright_textView)
    TextView mCopyrightTextView;

    /**
     * 初始化版权信息
     */
    private void initCopyRight() {
        mCopyrightTextView.setText(String.format(getResources().getString(R.string.about_copyright), DateUtil.getCurrentYear()));
    }

    /**
     * 初始化 topbar
     */
    protected void initTopBar() {
        // 切换其他情况的按钮
        mTopBar.setTitle("致谢");
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> popBackStack());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gratitude;
    }

    @Override
    protected void initLastCustom() {
        initCopyRight();
    }

    @Override
    protected void initRefreshLayout() {
        mPullRefreshLayout.setEnabled(false);
    }

}
