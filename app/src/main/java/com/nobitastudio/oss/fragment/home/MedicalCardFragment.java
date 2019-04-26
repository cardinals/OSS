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
import com.nobitastudio.oss.fragment.mine.ElectronicCaseFragment;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.nobitastudio.oss.model.dto.ElectronicCaseDTO;
import com.nobitastudio.oss.model.dto.ReflectStrategy;
import com.nobitastudio.oss.model.entity.MedicalCard;
import com.nobitastudio.oss.util.OkHttpUtil;
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
                startFragment(new InputMobileFragment());
                break;
        }
    }

    private void initBasic() {
        mBindMedicalCards = mNormalContainerHelper.getBindMedicalCards();
        if (mBindMedicalCards == null || mBindMedicalCards.size() == 0) {
            refresh(false);
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
            switch (mNormalContainerHelper.getEnterMedicalCardFor()) {
                case NORMAL:
                    mNormalContainerHelper.setSelectedMedicalCard(mBindMedicalCards.get(pos));
                    startFragment(new MedicalCardDetailFragment());
                    break;
                case DRUG_DETAIL:
                case ELECTRONIC_CASE:
                    showAutoDialogNumber("请输入诊疗卡管理密码", getContext().getString(R.string.warm_prompt_electronic_case),
                            "取消", (dialog, index) -> dialog.dismiss(),
                            "确定", (dialog, index, content) -> {
                                dialog.dismiss();
                                showNetworkLoadingTipDialog("正在验证");
                                // 验证诊疗卡管理密码
                                getAsyn(Arrays.asList("electronic-case", mBindMedicalCards.get(pos).getId(), content, "findAll"), null,
                                        new ReflectStrategy<>(new TypeReference<List<ElectronicCaseDTO>>() {
                                        }), new OkHttpUtil.SuccessHandler<List<ElectronicCaseDTO>>() {
                                            @Override
                                            public void handle(List<ElectronicCaseDTO> electronicCaseDTOS) {
                                                mNormalContainerHelper.setSelectedMedicalCard(mBindMedicalCards.get(pos));
                                                mNormalContainerHelper.setElectronicCases(electronicCaseDTOS);
                                                closeTipDialog();
                                                startFragment(new ElectronicCaseFragment());
                                            }
                                        }
                                );
                            }, 6);
                    break;
                case EXPRESS:
                    showAutoDialogNumber("请输入诊疗卡管理密码", getContext().getString(R.string.warm_prompt_electronic_case),
                            "取消", (dialog, index) -> dialog.dismiss(),
                            "确定", (dialog, index, content) -> {
                                dialog.dismiss();
                                showNetworkLoadingTipDialog("正在验证");
                                // 验证诊疗卡管理密码
                                getAsyn(Arrays.asList("electronic-case", mBindMedicalCards.get(pos).getId(), content, "findAll"), null,
                                        new ReflectStrategy<>(new TypeReference<List<ElectronicCaseDTO>>() {
                                        }), new OkHttpUtil.SuccessHandler<List<ElectronicCaseDTO>>() {
                                            @Override
                                            public void handle(List<ElectronicCaseDTO> electronicCaseDTOS) {
                                                mNormalContainerHelper.setSelectedMedicalCard(mBindMedicalCards.get(pos));
                                                mNormalContainerHelper.setElectronicCases(electronicCaseDTOS);
                                                closeTipDialog();
                                                startFragment(new ExpressFragment());
                                            }
                                        }
                                );
                            }, 6);
                    break;
            }
        });
        mMedicalCardRecyclerView.setAdapter(mMedicalCardItemAdapter);
    }

    @Override
    protected void refresh(boolean isCancelPull) {
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
                            switch (mNormalContainerHelper.getEnterMedicalCardFor()) {
                                case ELECTRONIC_CASE:
                                    showInfoTipDialog("请选择需查看病例详情的诊疗卡", 2000l);
                                    break;
                                case DRUG_DETAIL:
                                    showInfoTipDialog("请选择您需查看药品详情的诊疗卡", 2000l);
                                    break;
                                case EXPRESS:
                                    showInfoTipDialog("请选择您邮寄报告的诊疗卡");
                                    break;
                                case NORMAL:
                                default:
                                    closeTipDialog();
                                    break;
                            }
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
                refresh(false);
            }
        });
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
