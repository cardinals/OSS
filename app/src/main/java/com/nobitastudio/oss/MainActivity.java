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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.nobitastudio.oss.activity.BaseFragmentActivity;
import com.nobitastudio.oss.fragment.BaseFragment;
import com.nobitastudio.oss.fragment.HomeFragment;
import com.nobitastudio.oss.fragment.LoginFragment;
import com.nobitastudio.oss.fragment.TestFragment;

public class MainActivity extends BaseFragmentActivity {

    @Override
    protected int getContextViewId() {
        return R.id.qmuidemo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            BaseFragment fragment = getFirstFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(getContextViewId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
        }
    }

    /**
     * 检测登录及其异常状态
     * @return
     */
    protected BaseFragment getFirstFragment() {
//        return new TestFragment();
//        return new LoginFragment();
        return new HomeFragment();
    }

}
