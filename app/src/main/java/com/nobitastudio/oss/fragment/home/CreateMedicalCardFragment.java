package com.nobitastudio.oss.fragment.home;

import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.nobitastudio.oss.model.dto.CreateMedicalCardDTO;
import com.nobitastudio.oss.model.dto.ReflectStrategy;
import com.nobitastudio.oss.model.entity.MedicalCard;
import com.nobitastudio.oss.model.enumeration.Sex;
import com.nobitastudio.oss.util.OkHttpUtil;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description 用户输入新密码 以及 确认密码
 */
public class CreateMedicalCardFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.medical_card_owner_name_textview)
    TextView mMedicalCardOwnerNameTextView;
    @BindView(R.id.medical_card_owner_sex_textview)
    TextView mMedicalCardOwnerSexTextView;
    @BindView(R.id.medical_card_owner_idcard_textview)
    TextView mMedicalCardOwnerIdCardTextView;
    @BindView(R.id.medical_card_password_textview)
    TextView mMedicalCardPasswordTexView;
    @BindView(R.id.medical_card_owner_address_textview)
    TextView mMedicalCardOwnerAddressTextView;
    @BindView(R.id.copyright_textview)
    TextView mCopyrightTextView;

    MedicalCard mMedicalCard; // 待创建的medicalCard;

    @OnClick({R.id.medical_card_owner_name_linearlayout, R.id.medical_card_owner_sex_linearlayout,
            R.id.medical_card_owner_idcard_linearlayout, R.id.medical_card_owner_address_linearlayout,
            R.id.medical_card_password_linearlayout, R.id.confirm_info_button})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.medical_card_owner_name_linearlayout:
                showAutoDialog("请输入诊疗卡持有者姓名", getString(R.string.create_medical_card_attention),
                        "取消", (dialog, index) -> {
                            dialog.dismiss();
                        },
                        "确定", (dialog, index, content) -> {
                            dialog.dismiss();
                            mMedicalCard.setOwnerName(content);
                            mMedicalCardOwnerNameTextView.setText(content);
                        });
                break;
            case R.id.medical_card_owner_sex_linearlayout:
                List<String> sexes = Arrays.asList("男", "女", "保密");
                showListPopView(mMedicalCardOwnerSexTextView, sexes, 120, 160, (parent, view, position, id) -> {
                    popViewDismiss();
                    mMedicalCard.setOwnerSex(Sex.getFromChinese(sexes.get(position)));
                    mMedicalCardOwnerSexTextView.setText(sexes.get(position));
                }, null);
                break;
            case R.id.medical_card_owner_idcard_linearlayout:
                showAutoDialogIdCard("请输入诊疗卡持有者身份证号", getString(R.string.create_medical_card_attention),
                        "取消", (dialog, index) -> {
                            dialog.dismiss();
                        },
                        "确定", (dialog, index, content) -> {
                            dialog.dismiss();
                            if (RegexUtils.isIDCard18Exact(content)) {
                                mMedicalCard.setOwnerIdCard(content);
                                mMedicalCardOwnerIdCardTextView.setText(content);
                            } else {
                                showInfoTipDialog("请输入正确的身份证号");
                            }
                        });
                break;
            case R.id.medical_card_password_linearlayout:
                showAutoDialogNumber("该密码用于保护病历等隐私信息", getContext().getString(R.string.warm_prompt_electronic_case),
                        "取消", (dialog, index) -> dialog.dismiss(),
                        "确定", (dialog, index, content) -> {
                            dialog.dismiss();
                            mMedicalCard.setPassword(content);
                            mMedicalCardPasswordTexView.setText(content);
                        },6);
                break;
            case R.id.medical_card_owner_address_linearlayout:
                showAutoDialog("请输入诊疗卡持有者联系地址", getString(R.string.create_medical_card_attention),
                        "取消", (dialog, index) -> {
                            dialog.dismiss();
                        },
                        "确定", (dialog, index, content) -> {
                            dialog.dismiss();
                            mMedicalCard.setOwnerAddress(content);
                            mMedicalCardOwnerAddressTextView.setText(content);
                        });
                break;
            case R.id.confirm_info_button:
                // 检查是否存在没有输入的信息
                if (mMedicalCard.createInfoIsComplete()) {
                    showMessageNegativeDialog("提示", "该诊疗卡创建后将自动绑定至该账户",
                            "取消创建", (dialog, index) -> dialog.dismiss(),
                            "立刻创建并绑定", (dialog, index) -> {
                                dialog.dismiss();
                                showNetworkLoadingTipDialog("正在创建电子诊疗卡", 1000l);
                                postAsyn(Arrays.asList("medical-card", "create-and-bind"), null,
                                        new CreateMedicalCardDTO(mNormalContainerHelper.getUser().getId(), mMedicalCard), new ReflectStrategy<>(MedicalCard.class),
                                        new OkHttpUtil.SuccessHandler<MedicalCard>() {
                                            @Override
                                            public void handle(MedicalCard medicalCard) {
                                                showSuccessTipDialog("创建成功");
                                                mNormalContainerHelper.getBindMedicalCards().add(medicalCard);
                                                popBackStack(MedicalCardFragment.class);
                                            }
                                        });
                            });
                } else {
                    showInfoTipDialog("请输入所有选项");
                }
                break;
        }
    }

    @Override
    protected Boolean topBarIsTransparent() {
        return Boolean.TRUE;
    }

    @Override
    protected String getTopBarTitle() {
        return "确认办理";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_create_medical_card;
    }

    @Override
    protected void initLastCustom() {
        mMedicalCard = new MedicalCard();
        mMedicalCard.setOwnerMobile(mNormalContainerHelper.getInputMobile());
        initCopyRight(mCopyrightTextView);
    }
}
