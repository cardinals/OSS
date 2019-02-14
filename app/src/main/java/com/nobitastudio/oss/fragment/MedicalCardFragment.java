package com.nobitastudio.oss.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.model.entity.MedicalCard;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MedicalCardFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.recyclerview)
    RecyclerView mMedicalCardRecyclerView;

    @OnClick({R.id.create_medical_card_button, R.id.bind_medical_card_button})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_medical_card_button:
                break;
            case R.id.bind_medical_card_button:
                break;
        }
    }

    @Override
    protected void init() {
        initTopBar();
        initRefreshLayout();
        initData();
    }

    @Override
    protected void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> popBackStack());
        mTopBar.setTitle("挂号成功");
    }

    @Override
    protected void initRefreshLayout() {
        mPullRefreshLayout.setEnabled(false);
    }

    @Override
    protected void initData() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        mMedicalCardRecyclerView.setLayoutManager(gridLayoutManager);
        mMedicalCardRecyclerView.setAdapter(new MedicalCardItemAdapter(getBaseFragmentActivity(), null));
    }

    @Override
    protected View.OnClickListener getEmptyViewRetryButtonListener() {
        return null;
    }

    static class MedicalCardItemAdapter extends BaseRecyclerAdapter<MedicalCard> {

        public MedicalCardItemAdapter(Context ctx, List<MedicalCard> list) {
            super(ctx, list);
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.recycleview_item_medical_card;
        }

        @Override
        public void bindData(RecyclerViewHolder holder, int position, MedicalCard item) {
            holder.getTextView(R.id.owner_name_textview).setText("持卡人" + position);
            holder.getTextView(R.id.medical_card_no_textview).setText("卡号" + position);
        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    @Override
    protected View onCreateView() {
        FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_medicalcard, null);
        ButterKnife.bind(this, frameLayout);
        init();
        return frameLayout;
    }
}
