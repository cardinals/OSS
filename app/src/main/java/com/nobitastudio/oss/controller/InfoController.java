package com.nobitastudio.oss.controller;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.blankj.utilcode.util.ToastUtils;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.base.inter.ControllerClickHandler;
import com.nobitastudio.oss.decorator.GridDividerItemDecoration;
import com.nobitastudio.oss.model.vo.ItemDescription;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.QMUIWindowInsetLayout;
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
public class InfoController extends QMUIWindowInsetLayout {

    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.pull_to_refresh)
    QMUIPullRefreshLayout mPullRefreshLayout;
    @BindView(R.id.emptyView)
    QMUIEmptyView mEmptyView;

    ControllerClickHandler mHandler;

    protected void init() {
        initTopBar();
        initRefreshLayout();
        initData();
    }

    protected void initTopBar() {
        mTopBar.setTitle("消息");
    }

    protected void initRefreshLayout() {
        mPullRefreshLayout.setEnabled(false);
    }

    protected void initData() {
        mEmptyView.hide();
        ItemRecyclerViewAdapter mItemAdapter = new ItemRecyclerViewAdapter(getContext(),
                Arrays.asList(new ItemDescription("就诊提醒", R.mipmap.diagnosis), new ItemDescription("吃药提醒", R.mipmap.drug),
                        new ItemDescription("检查提醒", R.mipmap.ic_check), new ItemDescription("手术提醒", R.mipmap.operation),
                        new ItemDescription("缴费信息", R.mipmap.pay_big), new ItemDescription("公告栏", R.mipmap.ic_announcement),
                        new ItemDescription("天气预报", R.mipmap.weather), new ItemDescription("更早消息", R.mipmap.refresh)
                ));
        mItemAdapter.setOnItemClickListener((v, pos) -> ToastUtils.showShort(pos));
        mRecyclerView.setAdapter(mItemAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRecyclerView.addItemDecoration(new GridDividerItemDecoration(getContext(), 3));
    }

    static class ItemRecyclerViewAdapter extends BaseRecyclerAdapter<ItemDescription> {

        public ItemRecyclerViewAdapter(Context ctx, List<ItemDescription> data) {
            super(ctx, data);
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.recycleview_item_info;
        }

        @Override
        public void bindData(RecyclerViewHolder holder, int position, ItemDescription item) {
            holder.getTextView(R.id.item_name).setText(item.getName());
            if (item.getIconRes() != 0) {
                holder.getImageView(R.id.item_icon).setImageResource(item.getIconRes());
            }
        }
    }

    public InfoController(Context context, ControllerClickHandler mHandler) {
        super(context);
        this.mHandler = mHandler;
        LayoutInflater.from(context).inflate(R.layout.pager_info, this);
        ButterKnife.bind(this);
        init();
    }

}
