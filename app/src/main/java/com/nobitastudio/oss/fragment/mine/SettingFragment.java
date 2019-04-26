package com.nobitastudio.oss.fragment.mine;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.fragment.about.AboutFragment;
import com.nobitastudio.oss.fragment.login.LoginFragment;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.nobitastudio.oss.widget.QMUIGroupListView;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUILoadingView;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class SettingFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.groupListView)
    QMUIGroupListView mGroupListView;

    QMUICommonListItemView diagnosisRemindItem;
    QMUICommonListItemView eatDrugRemindItem;
    QMUICommonListItemView checkRemindItem;
    QMUICommonListItemView operationRemindItem;
    QMUICommonListItemView hospitalActivityRemindItem;
    QMUICommonListItemView clearCacheItem;
    QMUICommonListItemView updateInfoItem;
    QMUICommonListItemView aboutUsItem;
    QMUICommonListItemView shareItem;

    @OnClick({R.id.logout_button})
    void onClick(View v) {
        // 清除所有fragment
        showInfoTipDialog("正在开发中");
    }

    protected void initGroupListView() {

        // ============================ 短信通知
        diagnosisRemindItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_remind),
                "就诊提醒",
                "已关闭",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_SWITCH
        );

        eatDrugRemindItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_drug_big),
                "吃药提醒",
                "吃药时推送",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_SWITCH
        );

        checkRemindItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_check_big),
                "检查提醒",
                "检查前两小时推送",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_SWITCH
        );

        operationRemindItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_operation_big),
                "手术提醒",
                "手术前一天推送",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_SWITCH
        );

        hospitalActivityRemindItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_preferen),
                "优惠活动",
                "实时推送",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM
        );
        hospitalActivityRemindItem.addAccessoryCustomView(new QMUILoadingView(getActivity()));

        QMUIGroupListView.newSection(getContext())
                .setTitle("消息推送：同时打开或关闭App推送与短信通知")
                .setLeftIconSize(QMUIDisplayHelper.dp2px(getContext(), 28), ViewGroup.LayoutParams.WRAP_CONTENT)
                .addItemView(diagnosisRemindItem, getOnclickListener())
                .addItemView(eatDrugRemindItem, getOnclickListener())
                .addItemView(checkRemindItem, getOnclickListener())
                .addItemView(operationRemindItem, getOnclickListener())
                .addItemView(hospitalActivityRemindItem, getOnclickListener())
                .addTo(mGroupListView);

        // ============================ 常用设置 通知
        clearCacheItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_delete),
                "清除缓存",
                "10.1M",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        updateInfoItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_edit),
                "个人资料",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        aboutUsItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_info),
                "关于我们",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        shareItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_share),
                "分享",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUIGroupListView.newSection(getContext())
                .setTitle("常用设置")
                .setLeftIconSize(QMUIDisplayHelper.dp2px(getContext(), 28), ViewGroup.LayoutParams.WRAP_CONTENT)
                .addItemView(clearCacheItem, getOnclickListener())
                .addItemView(updateInfoItem, getOnclickListener())
                .addItemView(aboutUsItem, getOnclickListener())
                .addItemView(shareItem, getOnclickListener())
                .addTo(mGroupListView);
    }

    private View.OnClickListener getOnclickListener() {
        return v -> {
            QMUICommonListItemView itemView = (QMUICommonListItemView) v;
            CharSequence itemViewText = ((QMUICommonListItemView) v).getText();
            if (itemViewText.equals("就诊提醒")) {
                changeQMUICommonListItemViewState(itemView, !itemView.getSwitch().isChecked(), "正在开启");
                // 模拟
                mTopBar.postDelayed(() -> changeQMUICommonListItemViewState(itemView, !itemView.getSwitch().isChecked(), "就诊前两小时推送"), 1500);
            } else if (itemViewText.equals("吃药提醒")) {
                changeQMUICommonListItemViewState(itemView, false, "正在关闭");
                mTopBar.postDelayed(() -> changeQMUICommonListItemViewState(itemView, false, "已关闭"), 1500);
            } else if (itemViewText.equals("检查提醒")) {

            } else if (itemViewText.equals("手术提醒")) {

            } else if (itemViewText.equals("医院活动")) {

            } else if (itemViewText.equals("清除缓存")) {
                showMessagePositiveDialog("清除缓存", "确定清楚本地缓存数据吗?",
                        "取消", (dialog, index) -> dialog.dismiss(),
                        "确定", (dialog, index) -> {
                            dialog.dismiss();
                            // 清除本地缓存操作
                            showNetworkLoadingTipDialog("正在清除");
                            mTopBar.postDelayed(() -> {
                                closeTipDialog();
                                showSuccessTipDialog("清除成功");
                                clearCacheItem.setDetailText("0.0M");
                            }, 3000l);
                        });
            } else if (itemViewText.equals("个人资料")) {
                startFragment(new UserInfoFragment());
            } else if (itemViewText.equals("关于我们")) {
                startFragment(new AboutFragment());
            } else if (itemViewText.equals("分享")) {
                showSimpleBottomSheetGrid(
                        Arrays.asList(R.mipmap.wechat, R.mipmap.wechat_zone, R.mipmap.weibo, R.mipmap.qq, R.mipmap.qq_zone, R.mipmap.msg_chat),
                        Arrays.asList("微信", "朋友圈", "微博", "QQ", "QQ空间", "短信"),
                        Arrays.asList(1, 2, 3, 4, 5, 6),
                        (dialog, index) -> {
                            dialog.dismiss();
                            showInfoTipDialog("正在开发中");
                        });
            }
        };
    }

    // 改变gourplistitem的状态
    private void changeQMUICommonListItemViewState(QMUICommonListItemView itemView, boolean isChecked, String msg) {
        itemView.setDetailText(msg);
        if (itemView.getAccessoryType() == QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM) {
            // loading -> switch
            itemView.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_SWITCH);
            itemView.getSwitch().setChecked(isChecked);
        } else if (itemView.getAccessoryType() == QMUICommonListItemView.ACCESSORY_TYPE_SWITCH) {
            // switch -> loading
            itemView.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
            itemView.addAccessoryCustomView(new QMUILoadingView(getActivity()));
        }
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
    protected String getTopBarTitle() {
        return "设置";
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
