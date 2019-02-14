package com.nobitastudio.oss.model.entity;

import com.nobitastudio.oss.model.enumeration.Area;

import java.io.Serializable;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 10:14
 * @description 检查项实例
 */
public class CheckItem implements Serializable {

    private static final long serialVersionUID = -4213321743396895666L;

    private Integer id;

    private String name;

    private String roomAddress;

    private Integer roomLocation;

    private Integer roomFloor;

    private Area area;

    private Double price;

    private Integer doctorId;
}
