package com.nobitastudio.oss.fragment.test;

import android.view.View;
import android.widget.EditText;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.fragment.old.CreateMedicalCardTwoFragment;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.nobitastudio.oss.model.common.ServiceResult;
import com.nobitastudio.oss.model.dto.ReflectStrategy;
import com.nobitastudio.oss.model.entity.Bind;
import com.nobitastudio.oss.model.entity.User;
import com.nobitastudio.oss.util.OkHttpUtil;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Response;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description 用户输入需要修改密码的手机号
 */
public class Test4Fragment extends StandardWithTobBarLayoutFragment {

    @OnClick({R.id.next_step_button, R.id.next_step2_button})
    void onClick(View v) {
        if (v.getId() == R.id.next_step_button) {
            getAsyn(Arrays.asList("user", "1"), null,
                    new ReflectStrategy<>(User.class),
                    (user -> Toasty.success(getContext(), user.getName()).show()),
                    serviceResult -> Toasty.error(getContext(), "失败").show()
            );
        }
        if (v.getId() == R.id.next_step2_button) {
            putAsyn(Arrays.asList("user", "enroll"), null, new User("18284556422", "1", "韩玉婷", "1231241"),
                    new ReflectStrategy<>(User.class),
                    (user) -> {
                        Toasty.success(getContext(), user.getId().toString()).show();
                    },
                    (serviceResult) -> Toasty.error(getContext(), serviceResult.getError().toString()).show());
        }

    }

    @Override
    protected String getTopBarTitle() {
        return "绑定手机号";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test4;
    }

    @Override
    protected void initLastCustom() {

    }
}
