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
 * @description 诊疗室实例
 */
@Data
@Entity
@Table(name = "diagnosis_room")
@Getter
@Setter
public class DiagnosisRoom implements Serializable {


    private static final long serialVersionUID = 2159462784859847255L;

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "location")
    private Integer location;

    @Column(name = "floor")
    private Integer floor;

    @Column(name = "area")
    @Enumerated(EnumType.STRING)
    private Area area;

    @Column(name = "department_id")
    private Integer departmentId;
}
