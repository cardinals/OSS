package com.nobitastudio.oss.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/09 11:42
 * @description
 */
@Data
@Entity
@Table(name = "user_role")
@Getter
@Setter
public class UserRole implements Serializable {

    private static final long serialVersionUID = 4254637288594977399L;

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "role_id")
    private Integer roleId;
}
