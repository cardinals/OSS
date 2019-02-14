package com.nobitastudio.oss.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.view.MotionEvent;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class JudgeNestedScrollView extends NestedScrollView {

    private boolean isNeedScroll = true;

    public JudgeNestedScrollView(@NonNull Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                return isNeedScroll;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /*
    改方法用来处理NestedScrollView是否拦截滑动事件
     */
    public void setNeedScroll(boolean isNeedScroll) {
        this.isNeedScroll = isNeedScroll;
    }
}
