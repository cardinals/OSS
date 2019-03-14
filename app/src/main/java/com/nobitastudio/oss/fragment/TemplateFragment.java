package com.nobitastudio.oss.fragment;

import android.widget.ImageView;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;

import butterknife.BindView;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class TemplateFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.solidImage)
    ImageView solidImage;

    @Override
    protected String getTopBarTitle() {
        return "模板";
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
