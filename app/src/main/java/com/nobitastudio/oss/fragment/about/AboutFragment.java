package com.nobitastudio.oss.fragment.about;

import android.widget.TextView;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.qmuiteam.qmui.util.QMUIPackageHelper;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import butterknife.BindView;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class AboutFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.version_textView)
    TextView versionTextView;
    @BindView(R.id.about_list_groupList)
    QMUIGroupListView aboutListQMUIGroupListView;
    @BindView(R.id.copyright_textView)
    TextView mCopyrightTextView;

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

    @Override
    public TransitionConfig onFetchTransitionConfig() {
        return SCALE_TRANSITION_CONFIG;
    }

    @Override
    protected String getTopBarTitle() {
        return "关于";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_about;
    }

    @Override
    protected void initLastCustom() {
        initVersion();
        initGroupList();
        initCopyRight(mCopyrightTextView);
    }




}
