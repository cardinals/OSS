package com.nobitastudio.oss.fragment.home;

import android.view.View;
import android.widget.TextView;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;

import java.util.Arrays;

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
    @BindView(R.id.medical_card_owner_address_textview)
    TextView mMedicalCardOwnerAddressTextView;
    @BindView(R.id.copyright_textview)
    TextView mCopyrightTextView;


    @OnClick({R.id.medical_card_owner_name_linearlayout, R.id.medical_card_owner_sex_linearlayout,
            R.id.medical_card_owner_idcard_linearlayout, R.id.medical_card_owner_address_linearlayout,
            R.id.confirm_info_button})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.medical_card_owner_name_linearlayout:
                showAutoDialog("请输入诊疗卡持有者姓名", getString(R.string.create_medical_card_attention),
                        "取消", (dialog, index) -> {
                            dialog.dismiss();
                        },
                        "确定", (dialog, index) -> {
                            dialog.dismiss();
                        });
                break;
            case R.id.medical_card_owner_sex_linearlayout:
                showListPopView(mMedicalCardOwnerSexTextView, Arrays.asList("男", "女", "保密"), 120, 160, (parent, view, position, id) -> {
                    popViewDismiss();
                }, null);
                break;
            case R.id.medical_card_owner_idcard_linearlayout:
                showAutoDialog("请输入诊疗卡持有者身份证号", getString(R.string.create_medical_card_attention),
                        "取消", (dialog, index) -> {
                            dialog.dismiss();
                        },
                        "确定", (dialog, index) -> {
                            dialog.dismiss();
                        });
                break;
            case R.id.medical_card_owner_address_linearlayout:
                showAutoDialog("请输入诊疗卡持有者联系地址", getString(R.string.create_medical_card_attention),
                        "取消", (dialog, index) -> {
                            dialog.dismiss();
                        },
                        "确定", (dialog, index) -> {
                            dialog.dismiss();
                        });
                break;
            case R.id.confirm_info_button:
                showNetworkLoadingTipDialog("正在创建电子诊疗卡", 1000l);
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
        initCopyRight(mCopyrightTextView);
    }
}
