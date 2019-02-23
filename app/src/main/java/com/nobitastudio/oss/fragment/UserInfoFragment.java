package com.nobitastudio.oss.fragment;

import android.widget.Button;
import android.widget.ImageView;

import com.nobitastudio.oss.R;

import butterknife.BindView;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class UserInfoFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.save_info_button)
    Button button;

    @Override
    protected void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> this.popBackStack());
        mTopBar.setTitle("个人资料");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_info;
    }

    @Override
    protected void initLastCustom() {


    }
}
