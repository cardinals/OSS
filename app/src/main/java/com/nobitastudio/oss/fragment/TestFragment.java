package com.nobitastudio.oss.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.model.entity.Department;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class TestFragment extends StandardWithTobBarLayoutFragment {

    static class TestRecyclerViewAdapter extends BaseRecyclerViewAdapter<Department> {

        public TestRecyclerViewAdapter(Context ctx, List<Department> list) {
            super(ctx, list);
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.recyclerview_item_test;
        }

        @Override
        public void bindData(RecyclerViewHolder holder, int position, Department item) {
            // do nothing
        }

        @Override
        public int getItemCount() {
            return 20;
        }
    }

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @Override
    protected void initTopBar() {
        mTopBar.setTitle("科室列表");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    protected void initLastCustom() {
        mRecyclerView.setAdapter(new TestRecyclerViewAdapter(getActivity(), Collections.EMPTY_LIST));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mEmptyView.hide();
    }
}
