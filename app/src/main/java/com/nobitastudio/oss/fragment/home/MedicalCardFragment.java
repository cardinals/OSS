package com.nobitastudio.oss.fragment.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.TypeReference;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.adapter.recyclerview.MedicalCardItemAdapter;
import com.nobitastudio.oss.container.NormalContainer;
import com.nobitastudio.oss.fragment.login.InputMobileFragment;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.nobitastudio.oss.model.common.ServiceResult;
import com.nobitastudio.oss.model.dto.ReflectStrategy;
import com.nobitastudio.oss.model.entity.MedicalCard;
import com.nobitastudio.oss.util.OkHttpUtil;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import java.util.Arrays;
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

    @BindView(R.id.recyclerview)
    RecyclerView mMedicalCardRecyclerView;

    List<MedicalCard> mBindMedicalCards;
    MedicalCardItemAdapter mMedicalCardItemAdapter;

    @OnClick({R.id.create_medical_card_button, R.id.bind_medical_card_button})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_medical_card_button:
                mNormalContainerHelper.setInputMobileFragment(NormalContainer.InputMobileFor.CREATE_MEDICAL_CARD);
                startFragment(new InputMobileFragment());
                break;
            case R.id.bind_medical_card_button:
                mNormalContainerHelper.setInputMobileFragment(NormalContainer.InputMobileFor.BIND_MEDICAL_CARD);
                startFragment(new BindMedicalCardOneFragment());
                break;
        }
    }

    private void initBasic() {
        mBindMedicalCards = mNormalContainerHelper.getBindMedicalCards();
        if (mBindMedicalCards == null || mBindMedicalCards.size() == 0) {
            getBindMedicalCards();
        }
        initRecyclerView();
    }

    protected void initRecyclerView() {
        mMedicalCardRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mMedicalCardItemAdapter = new MedicalCardItemAdapter(getContext(), mBindMedicalCards);
        mMedicalCardItemAdapter.setOnItemClickListener((view, pos) -> {
            mNormalContainerHelper.setSelectedMedicalCard(mBindMedicalCards.get(pos));
            startFragment(new MedicalCardDetailFragment());
        });
        mMedicalCardRecyclerView.setAdapter(mMedicalCardItemAdapter);
    }

    // 绑定的诊疗卡
    private void getBindMedicalCards() {
        showNetworkLoadingTipDialog("正在查询绑定的诊疗卡");
        getAsyn(Arrays.asList("medical-card", mNormalContainerHelper.getUser().getId().toString(), "medical-cards"), null,
                new ReflectStrategy<>(new TypeReference<List<MedicalCard>>() {
                }), new OkHttpUtil.SuccessHandler<List<MedicalCard>>() {
                    @Override
                    public void handle(List<MedicalCard> medicalCards) {
                        mNormalContainerHelper.setBindMedicalCards(medicalCards);
                        mBindMedicalCards.clear();
                        mBindMedicalCards.addAll(medicalCards);
                        mMedicalCardItemAdapter.notifyDataSetChanged();
                        if (mNormalContainerHelper.getBindMedicalCards().size() == 0) {
                            showInfoTipDialog("您尚未绑定任何诊疗卡");
                        } else {
                            closeTipDialog();
                        }
                    }
                });
    }

    @Override
    public TransitionConfig onFetchTransitionConfig() {
        return SCALE_TRANSITION_CONFIG;
    }

    @Override
    protected void initRefreshLayout() {
        mPullRefreshLayout.setEnabled(true);
        mPullRefreshLayout.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override
            public void onMoveTarget(int offset) {

            }

            @Override
            public void onMoveRefreshView(int offset) {

            }

            @Override
            public void onRefresh() {
                mPullRefreshLayout.finishRefresh();
                getBindMedicalCards();
            }
        });
    }

    @Override
    protected void onResumeAction() {
        mMedicalCardItemAdapter.notifyDataSetChanged(); // 从创建、解绑、绑定诊疗卡 回退时调用
    }

    @Override
    protected String getTopBarTitle() {
        return "我的诊疗卡";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_medical_card;
    }

    @Override
    protected void initLastCustom() {
        initBasic();
    }
}
