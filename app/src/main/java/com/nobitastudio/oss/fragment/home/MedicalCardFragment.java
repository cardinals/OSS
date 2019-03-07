package com.nobitastudio.oss.fragment.home;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.fragment.home.BindMedicalCardOneFragment;
import com.nobitastudio.oss.fragment.home.CreateMedicalCardOneFragment;
import com.nobitastudio.oss.fragment.home.MedicalCardDetailFragment;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.nobitastudio.oss.model.entity.MedicalCard;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class MedicalCardFragment extends StandardWithTobBarLayoutFragment {

    static class MedicalCardItemAdapter extends BaseRecyclerViewAdapter<MedicalCard> {

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

    @BindView(R.id.recyclerview)
    RecyclerView mMedicalCardRecyclerView;

    @OnClick({R.id.create_medical_card_button, R.id.bind_medical_card_button})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_medical_card_button:
                startFragment(new CreateMedicalCardOneFragment());
                break;
            case R.id.bind_medical_card_button:
                startFragment(new BindMedicalCardOneFragment());
                break;
        }
    }

    @Override
    public TransitionConfig onFetchTransitionConfig() {
        return SCALE_TRANSITION_CONFIG;
    }

    @Override
    protected void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> popBackStack());
        mTopBar.setTitle("我的诊疗卡");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_medical_card;
    }

    @Override
    protected void initLastCustom() {
        initMedicalCard();
    }

    protected void initMedicalCard() {
        mMedicalCardRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        MedicalCardItemAdapter mMedicalCardItemAdapter = new MedicalCardItemAdapter(getBaseFragmentActivity(), null);
        mMedicalCardItemAdapter.setOnItemClickListener((view,pos) -> startFragment(new MedicalCardDetailFragment()));
        mMedicalCardRecyclerView.setAdapter(mMedicalCardItemAdapter);
    }

}
