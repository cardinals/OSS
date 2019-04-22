package com.nobitastudio.oss.model.entity;

import com.nobitastudio.oss.model.enumeration.ItemType;
import com.nobitastudio.oss.model.enumeration.OrderState;
import com.nobitastudio.oss.model.enumeration.PaymentChannel;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 10:14
 * @description 订单实例
 * id生成策略：通过雪花算法生成唯一id,配合订单的类型所绑定的作为 数据中心
 * 流水号生成策略 :
 */
@Data
@Entity
@Table(name = "oss_order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OSSOrder implements Serializable {

    private static final long serialVersionUID = -8428754043223464384L;

    public OSSOrder(ItemType itemType, Integer userId, String medicalCardId) {
        this.userId = userId;
        this.medicalCardId = medicalCardId;
        this.itemType = itemType;
        this.state = OrderState.WAITING_PAY;
        this.createTime = LocalDateTime.now();
    }

    @Column(name = "id")
    @Id
    private String id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "medical_card_id")
    private String medicalCardId;

    @Column(name = "item_type")
    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private OrderState state;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "payment_channel")
    @Enumerated(EnumType.STRING)
    private PaymentChannel paymentChannel;

    @Column(name = "pay_time")
    private LocalDateTime payTime;

    @Column(name = "cancel_time")
    private LocalDateTime cancelTime;

    @Column(name = "cost")
    private Double cost;
}
