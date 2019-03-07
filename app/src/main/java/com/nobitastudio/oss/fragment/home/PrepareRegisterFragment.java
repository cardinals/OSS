package com.nobitastudio.oss.fragment.home;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.fragment.mine.WaitingPayRegisterFragment;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;

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
public class PrepareRegisterFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.choose_medical_card_textview)
    TextView mChooseMedicalCardTextView;
    @BindView(R.id.medical_card_ownername_linearlayout)
    LinearLayout mMedicalCardOwnerNameLinearLayout;
    @BindView(R.id.verification_code_edittext)
    EditText mVerificationCodeEditText;
    @BindView(R.id.register_basic_msg_imageview)
    ImageView mRegisterBasicMsgSolidImageView;

    @OnClick({R.id.choose_medical_card_textview, R.id.choose_medical_card_imageview,
            R.id.confirm_register_msg})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_medical_card_textview:
            case R.id.choose_medical_card_imageview:
                isBindMedicalCard();
                break;
            case R.id.confirm_register_msg:
                showMessagePositiveDialog("温馨提示", "请在30分钟内支付，逾期作废\n逾期超5次将会冻结就诊卡",
                        "取消", (dialog, index) -> dialog.dismiss(),
                        "我知道了", (dialog, index) -> {
                            dialog.dismiss();
                            if (isChooseMedicalCard()) {
                                // 选择了诊疗卡
                                if (isInputVerificationCode()) {
                                    // 输入了验证码
                                    if (isVerificationCodeRight()) {
                                        // 验证码是正确的,进入准备支付的activity
                                        startFragment(new WaitingPayRegisterFragment());
                                    }
                                }
                            }
                        });
                break;
            default:
                showErrorTipDialog("不支持的点击事件");
                break;
        }
    }

    /**
     * 判断用户是否存在有绑定的诊疗卡
     *
     * @return
     */
    private Boolean isBindMedicalCard() {
        if (Boolean.TRUE) {
            // 已有绑定的诊疗卡
            List<String> items = Arrays.asList("陈雄", "楚楚", "冯周", "陈雄陈雄", "陈雄陈雄陈雄");
            showListPopView(mChooseMedicalCardTextView, items, 120, 160,
                    (parent, view, position, id) -> {
                        mChooseMedicalCardTextView.setText(items.get(position));
                        popViewDismiss();
                    }, () -> {
                        ToastUtils.showShort("dismiss");
                    });
        } else {
            // 未绑定诊疗卡
            showMessagePositiveDialog("温馨提示", "您尚未绑定任何诊疗卡,请完成诊疗卡绑定后再进行挂号操作。",
                    "取消", (dialog, index) -> {
                        ToastUtils.showShort("用户已经取消");
                        dialog.dismiss();
                    }, "立即绑定/创建", (dialog, index) -> {
                        ToastUtils.showShort("goto medicalcard ic_activity");
                        dialog.dismiss();
                    });
        }
        return Boolean.FALSE;
    }

    /**
     * 用户是否选择了诊疗卡
     *
     * @return
     */
    private Boolean isChooseMedicalCard() {
        if (Boolean.TRUE) {
            // 未选择诊疗卡
            showInfoTipDialog("请选择就诊人");
        }
        return Boolean.TRUE;
        //return ContainerUtil.getMedicalCard() == null ? Boolean.FALSE : Boolean.TRUE;
    }

    /**
     * 判断用户是否输入了验证码
     *
     * @return
     */
    private Boolean isInputVerificationCode() {
        if (Boolean.TRUE) {
            // 未输入验证码
            showInfoTipDialog("请输入验证码");
        }
        return Boolean.TRUE;
        //return mVerificationCodeEditText.getText().toString().trim().isEmpty();
    }

    private Boolean isVerificationCodeRight() {
        if (Boolean.TRUE) {
            // 如果不是正确的
            showErrorTipDialog("验证码错误,请重新输入");
        }
        return Boolean.TRUE;
    }

    @Override
    protected void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> popBackStack());
        mTopBar.setTitle("预约挂号");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_prepare_register;
    }

    @Override
    protected void initLastCustom() {
        initSolidImage(mRegisterBasicMsgSolidImageView);
    }

}
