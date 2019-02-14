package com.nobitastudio.oss.model.vo;

import com.nobitastudio.oss.R;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/02/12 13:40
 * @description 用来描述 消息模块的封装类型
 */
public class ItemDescription {

    private String name;

    private int iconRes;

    public ItemDescription() {
    }

    public ItemDescription(String name) {
        this(name, R.mipmap.icon_grid_popup);
    }

    public ItemDescription(String name, int iconRes) {
        this.name = name;
        this.iconRes = iconRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }
}
