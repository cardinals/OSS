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

    Context mContext;

    private static QMUIBottomSheet mQMUIBottomSheet;

    public BottomSheetHelper(Context mContext) {
        this.mContext = mContext;
    }

    public QMUIBottomSheet showSimpleBottomSheetList(List<String> items,
                                                     QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener itemClickListener) {
        QMUIBottomSheet.BottomListSheetBuilder builder = new QMUIBottomSheet.BottomListSheetBuilder(mContext);
        items.forEach(builder::addItem);
        mQMUIBottomSheet = builder.setOnSheetItemClickListener(itemClickListener).build();
        mQMUIBottomSheet.show();
        return mQMUIBottomSheet;
    }

    public QMUIBottomSheet showSimpleBottomSheetGrid(List<Integer> mipmaps, List<String> titles, List<Integer> tags,
                                                     QMUIBottomSheet.BottomGridSheetBuilder.OnSheetItemClickListener itemClickListener) {
        QMUIBottomSheet.BottomGridSheetBuilder builder = new QMUIBottomSheet.BottomGridSheetBuilder(mContext);
        for (int i = 0; i < mipmaps.size(); i++) {
            if (i < 4) {  // 控制每行的数量为4个
                // 第一行
                builder.addItem(mipmaps.get(i), titles.get(i), tags.get(i), QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE);
            } else {
                // 第二行
                builder.addItem(mipmaps.get(i), titles.get(i), tags.get(i), QMUIBottomSheet.BottomGridSheetBuilder.SECOND_LINE);
            }
        }
        mQMUIBottomSheet = builder.setOnSheetItemClickListener(itemClickListener).build();
        mQMUIBottomSheet.show();
        return mQMUIBottomSheet;
    }
}
