package com.nobitastudio.oss.fragment.home;

import android.widget.ImageView;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;

import butterknife.BindView;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description 邮寄报告
 */
public class ExpressFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.solidImage)
    ImageView solidImage;

    @Override
    protected String getTopBarTitle() {
        return "title";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_standard;
    }

    @Override
    protected void initLastCustom() {
        initSolidImage(solidImage);
    }
}
