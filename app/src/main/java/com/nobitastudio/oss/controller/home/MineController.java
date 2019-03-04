package com.nobitastudio.oss.controller.home;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.helper.BottomSheetHelper;
import com.nobitastudio.oss.base.inter.ControllerClickHandler;
import com.nobitastudio.oss.fragment.AboutFragment;
import com.nobitastudio.oss.fragment.MedicalCardFragment;
import com.nobitastudio.oss.fragment.MyCollectFragment;
import com.nobitastudio.oss.fragment.OrderFragment;
import com.nobitastudio.oss.fragment.RegisterRecordFragment;
import com.nobitastudio.oss.fragment.SettingFragment;
import com.nobitastudio.oss.fragment.UserInfoFragment;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.QMUIWindowInsetLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/28 14:03
 * @description
 */
public class MineController extends QMUIWindowInsetLayout {

    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.pull_to_refresh)
    QMUIPullRefreshLayout mPullRefreshLayout;
    @BindView(R.id.emptyView)
    QMUIEmptyView mEmptyView;
    @BindView(R.id.groupListView)
    QMUIGroupListView mGroupListView;
    @BindView(R.id.QMUILinearLayout)
    QMUILinearLayout mQMUILinearLayout;

    ControllerClickHandler mHandler;

    @OnClick({R.id.wait_diagnosis_linearlayout, R.id.order_linearlayout, R.id.my_collection_linearlayout})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.wait_diagnosis_linearlayout:
                break;
            case R.id.order_linearlayout:
                mHandler.startFragment(new OrderFragment());
                break;
            case R.id.my_collection_linearlayout:
                mHandler.startFragment(new MyCollectFragment());
                break;
        }
    }

    private void initTopBar() {
        mTopBar.setBackgroundDividerEnabled(false);
        mTopBar.setTitle(getResources().getString(R.string.mine));
        mTopBar.addRightImageButton(R.mipmap.ic_setting, R.id.topbar_right_setting_button)
                .setOnClickListener(view -> mHandler.startFragment(new SettingFragment()));
    }

    protected void init() {
        initTopBar();
        initQMUILinearLayout();
        initRefreshLayout();
        initGroupListView();
        initLastCustom();
    }

    private void initQMUILinearLayout() {
        float mShadowAlpha = 1.0f;
        int mShadowElevationDp = 10;
        int mRadius = 15;
        mQMUILinearLayout.setRadiusAndShadow(QMUIDisplayHelper.dp2px(getContext(), mRadius),
                QMUIDisplayHelper.dp2px(getContext(), mShadowElevationDp), mShadowAlpha);
    }

    private void initGroupListView() {

        // 常用工具
        QMUICommonListItemView registerRecordItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_history),
                "挂号记录",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUICommonListItemView electronicCaseHistory = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_electronic_case),
                "电子病历",
                "需要密码方可查看",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUICommonListItemView electronicPrescriptionItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_tcm),
                "电子处方",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUICommonListItemView finishDiagnosisItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_finish_diagnosis),
                "已就诊",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUIGroupListView.newSection(getContext())
                .setTitle("常用工具")
                .setLeftIconSize(QMUIDisplayHelper.dp2px(getContext(), 28), ViewGroup.LayoutParams.WRAP_CONTENT)
                .addItemView(registerRecordItem, getOnclickListener())
                .addItemView(electronicCaseHistory, getOnclickListener())
                .addItemView(electronicPrescriptionItem, getOnclickListener())
                .addItemView(finishDiagnosisItem, getOnclickListener())
                .addTo(mGroupListView);

        //  普通工具
        QMUICommonListItemView myConsultingItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_consulting),
                "我的咨询",
                "可查看咨询的历史对话信息",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUICommonListItemView myMedicalCardsItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_medical_card),
                "我的诊疗卡",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUIGroupListView.newSection(getContext())
                .setTitle("普通工具")
                .setLeftIconSize(QMUIDisplayHelper.dp2px(getContext(), 28), ViewGroup.LayoutParams.WRAP_CONTENT)
                .addItemView(myConsultingItem, getOnclickListener())
                .addItemView(myMedicalCardsItem, getOnclickListener())
                .addTo(mGroupListView);
    }

    private View.OnClickListener getOnclickListener() {
        return v -> {
            QMUICommonListItemView itemView = (QMUICommonListItemView) v;
            CharSequence itemViewText = ((QMUICommonListItemView) v).getText();
            if (itemViewText.equals("挂号记录")) {
                mHandler.startFragment(new RegisterRecordFragment());
            } else if (itemView.equals("电子病历")) {
            } else if (itemView.equals("电子处方")) {
            } else if (itemView.equals("已就诊")) {
            } else if (itemView.equals("我的咨询")) {
            } else if (itemView.equals("我的诊疗卡")) {
                mHandler.startFragment(new MedicalCardFragment());
            }
        };
    }

    protected void initRefreshLayout() {
        mPullRefreshLayout.setEnabled(false);
    }

    protected void initLastCustom() {
        mEmptyView.hide();
    }

    public MineController(Context context, ControllerClickHandler mHandler) {
        super(context);
        this.mHandler = mHandler;
        LayoutInflater.from(context).inflate(R.layout.controller_mine, this);
        ButterKnife.bind(this);
        init();
    }
}
