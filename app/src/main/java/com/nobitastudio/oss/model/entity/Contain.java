package com.nobitastudio.oss.model.entity;

import com.nobitastudio.oss.model.enumeration.ItemType;

import java.io.Serializable;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/10 12:04
 * @description 订单 包含  收费项  包含关系
 */
public class Contain implements Serializable {

    private static final long serialVersionUID = 7468056643932027289L;

    public Contain(String orderId, ItemType itemType, String itemId) {
        this.orderId = orderId;
        this.itemType = itemType;
        this.itemId = itemId;
        this.amount = 1;
    }

    private Integer id;

    private String orderId;

    private ItemType itemType;

    private String itemId;

    private Integer amount;
}
