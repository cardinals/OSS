package com.nobitastudio.oss.fragment;

import android.view.View;
import android.widget.EditText;

import com.nobitastudio.oss.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description 用户输入需要修改密码的手机号
 */
public class CreateMedicalCardOneFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.medical_card_owner_mobile_edittext)
    EditText mMedicalCardOwnerMobileEditText;

    @OnClick({R.id.next_step_button})
    void onClick(View v) {
        startFragment(new CreateMedicalCardTwoFragment());
    }

    @Override
    protected void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> this.popBackStack());
        mTopBar.setTitle("绑定手机号");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_create_medical_card_one;
    }

    @Override
    protected void initLastCustom() {

    }
}
