package com.nobitastudio.oss.fragment.test;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.base.fragment.BaseFragment;
import com.nobitastudio.oss.model.common.ServiceResult;
import com.nobitastudio.oss.model.entity.Department;
import com.nobitastudio.oss.model.entity.User;
import com.nobitastudio.oss.util.OkHttpUtil;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class TestFragment extends BaseFragment {

    @OnClick(R.id.confirm_modify_password)
    void onClick(View v) {

//        OkHttpUtil.get(Arrays.asList("user", "1"), null, User.class,
//                (call, e) -> {
//                    Toasty.error(getContext(), e.getMessage()).show();
//                },
//                user -> {
//                    Toasty.success(getContext(), user.getMobile()).show();
//                },
//                (serviceResult) -> {
//                    Toasty.error(getContext(), serviceResult.getErrorCode()).show();
//                },
//                (call, response) -> {
//                    Toasty.success(getContext(), response.code()).show();
//                }
//        );
    }

    @Override
    protected void init() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test;
    }


}
