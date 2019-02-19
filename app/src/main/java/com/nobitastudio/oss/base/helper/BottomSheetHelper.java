package com.nobitastudio.oss.base.helper;

import android.content.Context;

import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/02/18 18:03
 * @description
 */
public class BottomSheetHelper {

    protected void showSimpleBottomSheetList(Context context, List<String> items, QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener itemClickListener) {
        QMUIBottomSheet.BottomListSheetBuilder builder = new QMUIBottomSheet.BottomListSheetBuilder(context);
        items.forEach(builder::addItem);
        builder.setOnSheetItemClickListener(itemClickListener).build().show();
    }

    protected void showSimpleBottomSheetGrid(Context context, List<Integer> mipmaps, List<String> titles, List<Integer> tags, QMUIBottomSheet.BottomGridSheetBuilder.OnSheetItemClickListener itemClickListener) {
        QMUIBottomSheet.BottomGridSheetBuilder builder = new QMUIBottomSheet.BottomGridSheetBuilder(context);
        for (int i = 0; i < mipmaps.size(); i++) {
            if (i < 4) {  // 控制每行的数量为4个
                // 第一行
                builder.addItem(mipmaps.get(i), titles.get(i), tags.get(i), QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE);
            } else {
                // 第二行
                builder.addItem(mipmaps.get(i), titles.get(i), tags.get(i), QMUIBottomSheet.BottomGridSheetBuilder.SECOND_LINE);
            }
        }
        builder.setOnSheetItemClickListener(itemClickListener).build().show();
    }
}
