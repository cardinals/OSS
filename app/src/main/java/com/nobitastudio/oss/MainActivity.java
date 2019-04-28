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

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.base.bj.trpayjar.utils.TrPay;
import com.nobitastudio.oss.base.activity.BaseFragmentActivity;
import com.nobitastudio.oss.base.fragment.BaseFragment;
import com.nobitastudio.oss.base.helper.TipDialogHelper;
import com.nobitastudio.oss.base.lab.fragment.QDWebViewFixFragment;
import com.nobitastudio.oss.container.ConstantContainer;
import com.nobitastudio.oss.container.NormalContainer;
import com.nobitastudio.oss.fragment.home.CreateMedicalCardFragment;
import com.nobitastudio.oss.fragment.home.DepartmentFragment;
import com.nobitastudio.oss.fragment.home.HomeFragment;
import com.nobitastudio.oss.fragment.login.ForgetPasswordFragment;
import com.nobitastudio.oss.fragment.login.InputMobileFragment;
import com.nobitastudio.oss.fragment.login.LoginFragment;
import com.nobitastudio.oss.fragment.mine.ElectronicCaseFragment;
import com.nobitastudio.oss.fragment.test.Test4Fragment;
import com.nobitastudio.oss.fragment.test.TestFragment;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

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
            startUp();
//            initPermission();  // 取消进入时必须申请权限
        }
    }

    // 开始启动，开启framgent的回退栈，
    private void startUp() {
        initPay();

        BaseFragment fragment = getFirstFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(getContextViewId(), fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }

    // 初始化应用权限
    private void initPermission() {
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.MODIFY_AUDIO_SETTINGS)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.MODIFY_AUDIO_SETTINGS);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.INTERNET);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_NETWORK_STATE);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CHANGE_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.CHANGE_WIFI_STATE);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_WIFI_STATE);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WAKE_LOCK)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WAKE_LOCK);
        }
        if (permissionList.size() != 0) {
            ActivityCompat.requestPermissions(MainActivity.this, permissionList.toArray(new String[0]), ConstantContainer.REQUEST_CODE);
        } else {
            startUp();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        NormalContainer.put(NormalContainer.SELECTED_ACTIVITY, this);
    }

    // 初始化支付功能
    private void initPay() {
        TrPay.getInstance(this).initPaySdk(ConstantContainer.TR_PAY_APP_KEY, ConstantContainer.TR_PAY_CHANEL);
    }

    /**
     * 检测登录及其异常状态
     *
     * @return
     */
    protected BaseFragment getFirstFragment() {
//        return new Test2Fragment();
//        return new LoginOldFragment();
//        return new SettingFragment();
//        return new HomeFragment();
//        return new QDWebViewFixFragment();
        return new LoginFragment();
//        return new UserInfoFragment();
//        return new ForgetPasswordOneFragment();
//        return new MedicalCardFragment();
//        return new CreateMedicalCardThreeOldFragment();
//        return new TestFragment();
//        return new VerificationCodeFragment();
//        return new MyCollectFragment();
//        return new BootFragment();
//        return new HealthArticleFragment();
//        return new LoginFragment();
//        return new ElectronicCaseDetailFragment();
//        return new FeedbackFragment();
    }


}
