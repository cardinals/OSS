package com.nobitastudio.oss.model.entity;

import com.nobitastudio.oss.model.enumeration.Area;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 10:14
 * @description 检查项实例
 */
@Data
@Entity
@Table(name = "operation_item")
@Getter
@Setter
public class OperationItem implements Serializable {

    private static final long serialVersionUID = -8885465986788787355L;

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "room_address")
    private String roomAddress;

    @Column(name = "room_location")
    private Integer roomLocation;

    @Column(name = "room_floor")
    private Integer roomFloor;

    @Column(name = "room_area")
    @Enumerated(EnumType.STRING)
    private Area area;

    @Column(name = "price")
    private Double price;

    @Column(name = "doctor_id")
    private Integer doctorId;
}
