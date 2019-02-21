package com.nobitastudio.oss.fragment;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.nobitastudio.oss.R;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUILoadingView;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import java.util.Arrays;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.groupListView)
    QMUIGroupListView mGroupListView;

    @OnClick({R.id.logout_button})
    void onClick(View v) {
        // 清除所有fragment
        startFragment(new LoginFragment());
    }

    protected void initGroupListView() {

        // ============================ 短信通知
        QMUICommonListItemView diagnosisRemindItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.diagnosis),
                "就诊提醒",
                "就诊前两小时推送",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_SWITCH
        );

        QMUICommonListItemView eatDrugRemindItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.drug),
                "吃药提醒",
                "吃药时推送",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_SWITCH
        );

        QMUICommonListItemView checkRemindItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.drug),
                "检查提醒",
                "检查前两小时推送",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_SWITCH
        );

        QMUICommonListItemView operationRemindItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.drug),
                "手术提醒",
                "手术前一天推送",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_SWITCH
        );

        QMUICommonListItemView hospitalActivityRemindItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.drug),
                "医院活动",
                "实时推送",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM
        );
        hospitalActivityRemindItem.addAccessoryCustomView(new QMUILoadingView(getActivity()));

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof QMUICommonListItemView) {
                    CharSequence text = ((QMUICommonListItemView) v).getText();
                    Toast.makeText(getActivity(), text + " is Clicked", Toast.LENGTH_SHORT).show();
                }
            }
        };

        QMUIGroupListView.newSection(getContext())
                .setTitle("消息推送：同时打开或关闭App推送与短信通知")
                .setLeftIconSize(QMUIDisplayHelper.dp2px(getContext(), 28), ViewGroup.LayoutParams.WRAP_CONTENT)
                .addItemView(diagnosisRemindItem, onClickListener)
                .addItemView(eatDrugRemindItem, null)
                .addItemView(checkRemindItem, null)
                .addItemView(operationRemindItem, null)
                .addItemView(hospitalActivityRemindItem, null)
                .addTo(mGroupListView);

        // ============================ 常用设置 通知
        QMUICommonListItemView clearCacheItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.delete),
                "清除缓存",
                "10.1M",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUICommonListItemView updateInfoItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.input),
                "修改资料",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUICommonListItemView aboutUsItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.info),
                "关于我们",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUICommonListItemView shareItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.share),
                "分享",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUIGroupListView.newSection(getContext())
                .setTitle("常用设置")
                .setLeftIconSize(QMUIDisplayHelper.dp2px(getContext(), 28), ViewGroup.LayoutParams.WRAP_CONTENT)
                .addItemView(clearCacheItem,
                        view -> showMessagePositiveDialog("清除缓存", "确定清楚本地缓存数据吗?",
                                "取消", (dialog, index) -> dialog.dismiss(),
                                "确定", (dialog, index) -> {
                                    dialog.dismiss();
                                    // 清除本地缓存操作
                                    showNetworkLoadingTipDialog("正在清除");
                                    mEmptyView.postDelayed(() -> {
                                        closeTipDialog();
                                        showSuccessTipDialog("清除成功");
                                    }, 3000l);
                                })
                )
                .addItemView(updateInfoItem, view -> startFragment(new UserInfoFragment()))
                .addItemView(aboutUsItem, view -> startFragment(new AboutFragment()))
                .addItemView(shareItem, v -> showSimpleBottomSheetGrid(
                        getContext(),
                        Arrays.asList(R.mipmap.wechat, R.mipmap.wechat_zone, R.mipmap.weibo, R.mipmap.qq, R.mipmap.qq_zone, R.mipmap.msg_chat),
                        Arrays.asList("微信", "朋友圈", "微博", "QQ", "QQ空间", "短信"),
                        Arrays.asList(1, 2, 3, 4, 5, 6),
                        (dialog, index) -> dialog.dismiss()))
                .addTo(mGroupListView);
    }

    @Override
    public TransitionConfig onFetchTransitionConfig() {
        return SCALE_TRANSITION_CONFIG;
    }

    @Override
    protected View.OnClickListener getEmptyViewRetryButtonListener() {
        return v -> ToastUtils.showShort("点击了emptyView" + v);
    }

    @Override
    protected void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> this.popBackStack());
        mTopBar.setTitle("设置");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initLastCustom() {
        initGroupListView();
    }
}
