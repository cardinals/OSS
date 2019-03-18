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

package com.nobitastudio.oss.base.lab.fragment;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.container.Constant;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.webview.QMUIWebView;
import com.qmuiteam.qmui.widget.webview.QMUIWebViewContainer;

// 提供浏览器支持
public class QDWebViewFixFragment extends QDWebExplorerFragment {

    public QDWebViewFixFragment() {
        String url = Constant.OSS_SERVER_LOCAL + "/swagger-ui.html";
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_URL, url);
        bundle.putString(EXTRA_TITLE, "浏览器fragment");
        setArguments(bundle);
    }

    @Override
    protected boolean needDispatchSafeAreaInset() {
        return true;
    }


    @Override
    protected void configWebView(QMUIWebViewContainer webViewContainer, QMUIWebView webView) {
        webView.setCallback(new QMUIWebView.Callback() {
            @Override
            public void onSureNotSupportChangeCssEnv() {
                new QMUIDialog.MessageDialogBuilder(getContext())
                        .setMessage("Do not support to change css env")
                        .addAction(new QMUIDialogAction(getContext(), R.string.ok, new QMUIDialogAction.ActionListener() {

                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        }))
                        .show();
            }
        });
    }

    @Override
    protected WebChromeClient getWebViewChromeClient() {
        return new ExplorerWebViewChromeClient(this) {
            @Override
            public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
                super.onShowCustomView(view, callback);
                mTopBarLayout.setBackgroundAlpha(0);
            }

            @Override
            public void onHideCustomView() {
                super.onHideCustomView();
            }
        };
    }

    @Override
    protected void onScrollWebContent(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        mTopBarLayout.computeAndSetBackgroundAlpha(scrollY, 0, QMUIDisplayHelper.dp2px(getContext(), 20));
    }
}
