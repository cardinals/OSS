package com.nobitastudio.oss.model.entity;

import com.nobitastudio.oss.model.enumeration.ItemType;
import com.nobitastudio.oss.model.enumeration.OrderState;
import com.nobitastudio.oss.model.enumeration.PaymentChannel;

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
public class OSSOrder implements Serializable {

    private static final long serialVersionUID = -8428754043223464384L;

    private String id;

    private Integer userId;

    private String medicalCardId;

    private ItemType name;

    private OrderState state;

    private LocalDateTime createTime;

    private PaymentChannel paymentChannel;

    private LocalDateTime payTime;

    private LocalDateTime cancelTime;
}
