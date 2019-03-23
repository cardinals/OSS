/*
 * Tencent is pleased to support the open source community by making QMUI_Android available.
 *
 * Copyright (C) 2017-2018 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the MIT License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nobitastudio.oss;

import android.os.Bundle;

import com.base.bj.trpayjar.utils.TrPay;
import com.nobitastudio.oss.base.activity.BaseFragmentActivity;
import com.nobitastudio.oss.base.fragment.BaseFragment;
import com.nobitastudio.oss.container.NormalContainer;
import com.nobitastudio.oss.fragment.home.CreateMedicalCardFragment;
import com.nobitastudio.oss.fragment.home.HomeFragment;
import com.nobitastudio.oss.fragment.login.ForgetPasswordFragment;
import com.nobitastudio.oss.fragment.login.InputMobileFragment;
import com.nobitastudio.oss.fragment.login.LoginFragment;
import com.nobitastudio.oss.fragment.test.Test4Fragment;
import com.nobitastudio.oss.fragment.test.TestFragment;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class MainActivity extends BaseFragmentActivity {

    @Override
    protected int getContextViewId() {
        return R.id.main_container;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            initPay();
            BaseFragment fragment = getFirstFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(getContextViewId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        NormalContainer.put(NormalContainer.SELECTED_ACTIVITY,this);
    }

    // 初始化支付功能
    private void initPay() {
        TrPay.getInstance(this).initPaySdk("appkey", "mychannel");
    }

    /**
     * 检测登录及其异常状态
     * @return
     */
    protected BaseFragment getFirstFragment() {
//        return new Test2Fragment();
//        return new LoginOldFragment();
//        return new SettingFragment();
//        return new HomeFragment();
//        return new UserInfoFragment();
//        return new ForgetPasswordOneFragment();
//        return new MedicalCardFragment();
//        return new CreateMedicalCardThreeOldFragment();
//        return new TestFragment();
//        return new VerificationCodeFragment();
//        return new MyCollectFragment();
//        return new BootFragment();
//        return new HealthArticleFragment();
        return new LoginFragment();
//        return new ElectronicCaseDetailFragment();
//        return new FeedbackFragment();
    }


}
