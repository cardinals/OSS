package com.nobitastudio.oss.fragment.old;

import android.view.View;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.fragment.old.BindMedicalCardTwoFragment;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;

import butterknife.OnClick;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class BindMedicalCardOneFragment extends StandardWithTobBarLayoutFragment {

    @OnClick({R.id.next_step_button})
    void onClick(View v) {
        // clearAllFragment();
        startFragment(new BindMedicalCardTwoFragment());
    }

    @Override
    protected String getTopBarTitle() {
        return "输入诊疗卡卡号";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bind_medical_card_one;
    }

    @Override
    protected void initLastCustom() {

    }
}
