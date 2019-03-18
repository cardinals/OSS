package com.nobitastudio.oss.fragment.home;

import android.view.View;
import android.view.ViewGroup;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description 用户输入新密码 以及 确认密码
 */
public class CreateMedicalCardThreeFragment extends StandardWithTobBarLayoutFragment {

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
        return R.layout.fragment_forget_passwor_three;
    }

    @Override
    protected void initLastCustom() {

    }
}
