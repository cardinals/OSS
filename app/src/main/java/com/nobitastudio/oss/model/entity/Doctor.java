package com.nobitastudio.oss.model.entity;

import com.nobitastudio.oss.model.enumeration.DoctorLevel;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 13:40
 * @description 医生实例
 */
@Data
@Entity
@Table(name = "doctor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Doctor implements Serializable {

    private static final long serialVersionUID = -1767052683247139655L;

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "specialty")
    private String specialty;

    @Column(name = "subMajor")
    private String subMajor;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "level")
    @Enumerated(EnumType.STRING)
    private DoctorLevel level;

    private String iconUrl;

    @Column(name = "department_id")
    private Integer departmentId;
}
