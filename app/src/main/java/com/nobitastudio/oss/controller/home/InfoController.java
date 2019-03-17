package com.nobitastudio.oss.controller.home;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.helper.QMUILinearLayoutHelper;
import com.nobitastudio.oss.base.inter.ControllerClickHandler;
import com.nobitastudio.oss.fragment.home.ExpressFragment;
import com.nobitastudio.oss.fragment.test.TestFragment;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.QMUIWindowInsetLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/28 14:03
 * @description
 */
public class InfoController extends QMUIWindowInsetLayout {

    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.pull_to_refresh)
    QMUIPullRefreshLayout mPullRefreshLayout;
    @BindView(R.id.emptyView)
    QMUIEmptyView mEmptyView;
    @BindView(R.id.QMUILinearLayout)
    QMUILinearLayout mQMUILinearLayout;
    @BindView(R.id.groupListView)
    QMUIGroupListView mGroupListView;

    ControllerClickHandler mHandler;
    Context mContext;
    QMUILinearLayoutHelper mQMUILinearLayoutHelper;

    private void initQMUILinearLayout() {
        mQMUILinearLayoutHelper = new QMUILinearLayoutHelper(mContext);
        mQMUILinearLayoutHelper.init(mQMUILinearLayout);
    }

    protected void initTopBar() {
        mTopBar.setBackgroundDividerEnabled(false);
        mTopBar.setTitle("消息");
    }

    protected void initRefreshLayout() {
        mPullRefreshLayout.setEnabled(false);
    }

    private void initGroupListView() {

        // 提醒类消息
        QMUICommonListItemView mEatDrugRemindItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_drug_big),
                "吃药提醒",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUICommonListItemView mCheckRemindItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_check_big),
                "检查提醒",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUICommonListItemView mOperationRemindItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_operation_big),
                "手术提醒",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUIGroupListView.newSection(getContext())
                .setTitle("提醒消息")
                .setLeftIconSize(QMUIDisplayHelper.dp2px(getContext(), 28), ViewGroup.LayoutParams.WRAP_CONTENT)
                .addItemView(mEatDrugRemindItem, getOnclickListener())
                .addItemView(mCheckRemindItem, getOnclickListener())
                .addItemView(mOperationRemindItem, getOnclickListener())
                .addTo(mGroupListView);

        //  其他消息
        QMUICommonListItemView mExpressItemView = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_express_big),
                "邮寄报告",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUICommonListItemView mWeatherItemView = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_weather),
                "天气信息",
                "可查看未来一周的天气信息",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUICommonListItemView mPreferenItemView = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_preferen),
                "优惠活动",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUICommonListItemView mOtherItemView = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_message),
                "其他消息",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUIGroupListView.newSection(getContext())
                .setTitle("常用消息")
                .setLeftIconSize(QMUIDisplayHelper.dp2px(getContext(), 28), ViewGroup.LayoutParams.WRAP_CONTENT)
                .addItemView(mExpressItemView, getOnclickListener())
                .addItemView(mPreferenItemView, getOnclickListener())
                .addItemView(mWeatherItemView, getOnclickListener())
                .addItemView(mOtherItemView, getOnclickListener())
                .addTo(mGroupListView);
    }

    private View.OnClickListener getOnclickListener() {
        return v -> {
            QMUICommonListItemView itemView = (QMUICommonListItemView) v;
            CharSequence itemViewText = ((QMUICommonListItemView) v).getText();
            if (itemViewText.equals("邮寄报告")) {
                mHandler.startFragment(new ExpressFragment());
            } else {
                mHandler.startFragment(new TestFragment());
            }
        };
    }

    protected void init() {
        initTopBar();
        initQMUILinearLayout();
        initRefreshLayout();
        initGroupListView();
    }

    public InfoController(Context context, ControllerClickHandler mHandler) {
        super(context);
        this.mHandler = mHandler;
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.controller_info, this);
        ButterKnife.bind(this);
        init();
    }

}
