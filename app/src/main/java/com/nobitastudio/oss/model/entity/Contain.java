package com.nobitastudio.oss.model.entity;

import com.nobitastudio.oss.model.enumeration.ItemType;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/10 12:04
 * @description 订单 包含  收费项  包含关系
 */
@Data
@Entity
@Table(name = "contain")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contain implements Serializable {

    private static final long serialVersionUID = 7468056643932027289L;

    public Contain(String orderId, ItemType itemType, String itemId) {
        this.orderId = orderId;
        this.itemType = itemType;
        this.itemId = itemId;
        this.amount = 1;
    }

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "item_type")
    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    @Column(name = "item_id")
    private String itemId;

    @Column(name = "amount")
    private Integer amount;
}
