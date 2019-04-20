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

package com.nobitastudio.oss.base.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.nobitastudio.oss.base.manager.QDUpgradeManager;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import butterknife.ButterKnife;

/**
 * Created by cgspine on 2018/1/7.
 */

public abstract class BaseFragment extends QMUIFragment {

    protected Boolean inited = Boolean.FALSE;   // 初始化过,便不再需要初始化

    @Override
    protected int backViewInitOffset() {
        return QMUIDisplayHelper.dp2px(getContext(), 100);
    }

    @Override
    public void onResume() {
        if (!inited) {
            // 初始化操作
            init();
            inited = Boolean.TRUE;
        }
        super.onResume();
        QDUpgradeManager.getInstance(getContext()).runUpgradeTipTaskIfExist(getActivity());

    }

    // 初始化
    protected abstract void init();

    // ================ 获取布局文件
    protected abstract int getLayoutId();

    // 刷新操作.默认在首次进入时以及需要刷新时进行调用
    protected void refresh(Boolean isCancelPull) {
        // do nothing
    }

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(getLayoutId(), null);
        ButterKnife.bind(this, root);
        return root;
    }

    // 回退到fragmeng 回退栈的指定fragment
    protected void popBackStack(Class<? extends QMUIFragment> clazz) {
        getBaseFragmentActivity().popBackStack(clazz);
    }


    // 跳转至指定 fragment 并且清除回退栈中所有fragment
//    protected void startFragmentAndClearOtherFragment(QMUIFragment targetFragment) {
//        startFragment(targetFragment);
//        getBaseFragmentActivity().popBackStack(targetFragment.getClass());
//    }

}
