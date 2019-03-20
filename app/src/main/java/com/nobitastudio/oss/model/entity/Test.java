package com.nobitastudio.oss.model.entity;

import lombok.*;

import javax.persistence.*;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/10 22:46
 * @description
 */
@Data
@Entity
@Table(name = "test")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Test {
    @Column(name = "id")
    @Id
    private String id;
}
