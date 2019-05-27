package com.nobitastudio.oss.fragment.home;

import android.preference.MultiSelectListPreference;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.nobitastudio.oss.model.entity.Bind;
import com.nobitastudio.oss.model.entity.MedicalCard;
import com.nobitastudio.oss.model.enumeration.Sex;
import com.nobitastudio.oss.util.DateUtil;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class MedicalCardDetailFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.hosipital_info_solid_imageview)
    ImageView mHospitalInfoSolidImageView;
    @BindView(R.id.medical_card_detail_solid_imageview)
    ImageView mMedicalCardDetailSolidImageView;
    @BindView(R.id.warm_prompt_solid_imageview)
    ImageView mWarmPromptSolidImageView;
    @BindView(R.id.medical_card_no_textview)
    TextView mMedicalCardNoTextView;
    @BindView(R.id.owner_name_textview)
    TextView mOwnerNameTextView;
    @BindView(R.id.owner_sex_textview)
    TextView mOwnerSexTextView;
    @BindView(R.id.owner_idcard_textview)
    TextView mOwnerIdCardTextView;
    @BindView(R.id.medical_card_create_time_textview)
    TextView mMedicalCardCreateTimeTextView;
    @BindView(R.id.attention_textview)
    TextView mTextView;

    MedicalCard mSelectedMedicalCard;

    @OnClick({R.id.unbind_medical_card_button})
    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.unbind_medical_card_button:
                showMessageNegativeDialog("温馨提示", "确定要取消绑定吗",
                        "取消绑定", (dialog, pos) -> {
                            dialog.dismiss();
                            putAsyn(Arrays.asList("bind", "unbind"), null,
                                    new Bind(mNormalContainerHelper.getUser().getId(), mNormalContainerHelper.getSelectedMedicalCard().getId()));
                            popBackStack();
                        },
                        "再想想", (dialog, pos) -> dialog.dismiss());
            default:
                break;
        }
    }

    private void initMedicalCardDetail() {
        // 初始化 textView
        mMedicalCardNoTextView.setText(mSelectedMedicalCard.getId());
        mOwnerNameTextView.setText(mSelectedMedicalCard.getOwnerName());
        mOwnerSexTextView.setText(Sex.getChineseSex(mSelectedMedicalCard.getOwnerSex()));
        mOwnerIdCardTextView.setText(mSelectedMedicalCard.getOwnerIdCard());
        mMedicalCardCreateTimeTextView.setText(DateUtil.convertToStandardDateTime(mSelectedMedicalCard.getCreateTime()));
    }

    @Override
    protected void initMiddleCustom() {
        mSelectedMedicalCard = mNormalContainerHelper.getSelectedMedicalCard(); // 需要用于产生tobbar title.在initTopbar之前调用
    }

    @Override
    protected String getTopBarTitle() {
        return mSelectedMedicalCard.getOwnerName();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_medical_card_detail;
    }

    @Override
    protected void initLastCustom() {
        initSolidImage(mHospitalInfoSolidImageView, mMedicalCardDetailSolidImageView, mWarmPromptSolidImageView);
        initMedicalCardDetail();

    }
}
