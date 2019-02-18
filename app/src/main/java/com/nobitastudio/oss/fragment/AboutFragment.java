package com.nobitastudio.oss.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.util.DateUtil;
import com.qmuiteam.qmui.util.QMUIPackageHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.version_textView)
    TextView versionTextView;
    @BindView(R.id.about_list_groupList)
    QMUIGroupListView aboutListQMUIGroupListView;
    @BindView(R.id.copyright_textView)
    TextView mCopyrightTextView;

    @Override
    protected void init() {
        initTopBar();
        initRefreshLayout();
        initVersion();
        initGroupList();
        initCopyRight();
        initData();
    }

    @Override
    protected void initTopBar() {
        mTopBar.setTitle("关于");
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
     * 初始化版本号信息
     */
    private void initVersion() {
        versionTextView.setText(QMUIPackageHelper.getAppVersion(getContext()));
    }

    /**
     * 初始化可选择查看的信息
     */
    private void initGroupList() {

        QMUICommonListItemView gratitudeItem = aboutListQMUIGroupListView.createItemView(getResources().getString(R.string.about_gratitude));
        gratitudeItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView declarationItem = aboutListQMUIGroupListView.createItemView(getResources().getString(R.string.about_declaration));
        declarationItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView licenseAgreementItem = aboutListQMUIGroupListView.createItemView(getResources().getString(R.string.about_license_agreement));
        licenseAgreementItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView feedbackItem = aboutListQMUIGroupListView.createItemView(getResources().getString(R.string.about_feedback));
        feedbackItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUIGroupListView.newSection(getContext())
                .addItemView(gratitudeItem, v -> startFragment(new GratitudeFragment()))
                .addItemView(declarationItem, v -> startFragment(new DeclarationFragment()))
                .addItemView(licenseAgreementItem, v -> startFragment(new LicenseAgreementFragment()))
                .addItemView(feedbackItem, v -> startFragment(new FeedbackFragment()))
                .addTo(aboutListQMUIGroupListView);
    }

    /**ww
     * 初始化版权信息
     */
    private void initCopyRight() {
        mCopyrightTextView.setText(String.format(getResources().getString(R.string.about_copyright), DateUtil.getCurrentYear()));
    }

    @Override
    public TransitionConfig onFetchTransitionConfig() {
        return SCALE_TRANSITION_CONFIG;
    }

    @Override
    protected View onCreateView() {
        FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_about, null);
        ButterKnife.bind(this, frameLayout);
        init();
        return frameLayout;
    }

}
