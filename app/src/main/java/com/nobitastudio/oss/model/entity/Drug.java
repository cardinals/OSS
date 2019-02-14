package com.nobitastudio.oss.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 15:31
 * @description 药品实例
 */
public class Drug implements Serializable {

    private static final long serialVersionUID = -235446322829903865L;

    private String id;

    private String name;

    private Double price;

    private Double purchasePrice;

    private String manufacturer;

    private LocalDateTime produceTime;

    private Integer effectiveTime;
}
