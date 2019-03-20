package com.nobitastudio.oss.model.entity;

import com.nobitastudio.oss.model.enumeration.Area;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 10:14
 * @description 科室实例
 */
@Data
@Entity
@Table(name = "department")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department implements Serializable {

    private static final long serialVersionUID = -3712970229472760165L;

    public interface DepartmentSimpleView {};
    public interface DepartmentDetailView extends DepartmentSimpleView {};

    public Department(String name) {
        this.name = name;
    }

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

    @Column(name = "introduction")
    private String introduction;
}
