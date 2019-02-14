package com.nobitastudio.oss.controller;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.nobitastudio.oss.R;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.QMUIWindowInsetLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    private void initTopBar() {
        mTopBar.setBackgroundDividerEnabled(false);
        mTopBar.setTitle(getResources().getString(R.string.mine));
        mTopBar.addRightImageButton(R.mipmap.setting, R.id.topbar_right_setting_button).setOnClickListener(view -> ToastUtils.showShort("打开设置"));
    }

    protected void init() {
        initTopBar();
        initRefreshLayout();
        initGroupListView();
        initData();
    }

    private void initGroupListView() {

        // 我的订单
        QMUICommonListItemView registerRecordItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.register_record),
                "挂号记录",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUICommonListItemView electronicCaseHistory = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.electronic_case_history),
                "电子病历",
                "需要密码方可查看",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUICommonListItemView finishDiagnosisItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.finish_diagnosis),
                "已就诊",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUICommonListItemView myConsultingItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.consulting),
                "我的咨询",
                "可查看咨询的历史对话信息",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );


        QMUICommonListItemView electronicPrescriptionItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.tcm_selected),
                "电子处方",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUIGroupListView.newSection(getContext())
                .setTitle("我的订单")
                .setLeftIconSize(QMUIDisplayHelper.dp2px(getContext(), 28), ViewGroup.LayoutParams.WRAP_CONTENT)
                .addItemView(registerRecordItem, null)
                .addItemView(electronicCaseHistory, null)
                .addItemView(finishDiagnosisItem, null)
                .addItemView(myConsultingItem, null)
                .addItemView(electronicPrescriptionItem, null)
                .addTo(mGroupListView);

        //  常用工具
        QMUICommonListItemView myCollectionItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.my_collection),
                "我的收藏",
                "收藏的医生与资讯",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUICommonListItemView myMedicalCardsItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.medical_card),
                "我的诊疗卡",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUICommonListItemView parkPayItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.car),
                "停车缴费",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUICommonListItemView helpCenterItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.info),
                "帮助中心",
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
                .setTitle("常用工具")
                .setLeftIconSize(QMUIDisplayHelper.dp2px(getContext(), 28), ViewGroup.LayoutParams.WRAP_CONTENT)
                .addItemView(myCollectionItem, null)
                .addItemView(myMedicalCardsItem, null)
                .addItemView(parkPayItem, null)
                .addItemView(helpCenterItem, null)
                .addItemView(shareItem, v -> showSimpleBottomSheetGrid(
                        getContext(),
                        Arrays.asList(R.mipmap.wechat, R.mipmap.wechat_zone, R.mipmap.weibo, R.mipmap.qq, R.mipmap.qq_zone, R.mipmap.msg_chat),
                        Arrays.asList("微信", "朋友圈", "微博", "QQ", "QQ空间", "短信"),
                        Arrays.asList(1, 2, 3, 4, 5, 6),
                        null))
                .addTo(mGroupListView);
    }

    protected void initRefreshLayout() {
        mPullRefreshLayout.setEnabled(false);
    }

    protected void initData() {
        mEmptyView.hide();
    }

    protected void showSimpleBottomSheetGrid(Context context, List<Integer> mipmaps, List<String> titles, List<Integer> tags, QMUIBottomSheet.BottomGridSheetBuilder.OnSheetItemClickListener itemClickListener) {
        QMUIBottomSheet.BottomGridSheetBuilder builder = new QMUIBottomSheet.BottomGridSheetBuilder(context);
        for (int i = 0; i < mipmaps.size(); i++) {
            if (i < 4) {  // 控制每行的数量为4个
                // 第一行
                builder.addItem(mipmaps.get(i), titles.get(i), tags.get(i), QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE);
            } else {
                // 第二行
                builder.addItem(mipmaps.get(i), titles.get(i), tags.get(i), QMUIBottomSheet.BottomGridSheetBuilder.SECOND_LINE);
            }
        }
        builder.setOnSheetItemClickListener(itemClickListener).build().show();
    }

    public MineController(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.pager_mine, this);
        ButterKnife.bind(this);
        init();
    }
}
