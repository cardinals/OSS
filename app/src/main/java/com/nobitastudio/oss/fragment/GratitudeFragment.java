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

public class GratitudeFragment extends StandardWithTobBarLayoutFragment {


    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.copyright_textView)
    TextView mCopyrightTextView;

    @Override
    protected void init() {
        initTopBar();
        initRefreshLayout();
        initCopyRight();
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
    protected void initRefreshLayout() {
        mPullRefreshLayout.setEnabled(false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected View.OnClickListener getEmptyViewRetryButtonListener() {
        return null;
    }

    /**
     * 初始化版权信息
     */
    private void initCopyRight() {
        mCopyrightTextView.setText(String.format(getResources().getString(R.string.about_copyright), DateUtil.getCurrentYear()));
    }



    @Override
    protected View onCreateView() {
        FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_gratitude, null);
        ButterKnife.bind(this,frameLayout);
        init();
        return frameLayout;
    }
}
