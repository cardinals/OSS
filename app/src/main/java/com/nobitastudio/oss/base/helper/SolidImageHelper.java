package com.nobitastudio.oss.base.helper;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.nobitastudio.oss.R;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIDrawableHelper;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/02/18 18:20
 * @description
 */
public class SolidImageHelper {

    Context mContext;

    public SolidImageHelper(Context mContext) {
        this.mContext = mContext;
    }

    public void initSolidImage(ImageView... imageViews) {
        int commonWidth = mContext.getResources().getDimensionPixelSize(R.dimen.solid_image_common_width);
        int commonHeight = mContext.getResources().getDimensionPixelSize(R.dimen.solid_image_common_height);
        int commonShapeRadius = QMUIDisplayHelper.dp2px(mContext, 2);
        BitmapDrawable solidImageBitmapDrawable = QMUIDrawableHelper.createDrawableWithSize(mContext.getResources(), commonWidth, commonHeight,
                commonShapeRadius, ContextCompat.getColor(mContext, R.color.app_color_blue));
        for (ImageView imageView : imageViews) {
            imageView.setImageDrawable(solidImageBitmapDrawable);
        }
    }
}
