package com.nobitastudio.oss.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 15:31
 * @description 药品实例
 */
@Data
@Entity
@Table(name = "drug")
@Getter
@Setter
public class Drug implements Serializable {

    private static final long serialVersionUID = -235446322829903865L;

    @Column(name = "id")
    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "purchase_price")
    private Double purchasePrice;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "produce_time")
    private LocalDateTime produceTime;

    @Column(name = "effective_time")
    private Integer effectiveTime;
}
