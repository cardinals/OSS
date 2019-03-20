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
 * @date 2019/01/03 14:36
 * @description 用户收藏医生. 收藏关系
 */
@Data
@Entity
@Table(name = "collect_doctor")
@Getter
@Setter
public class CollectDoctor  implements Serializable {

    private static final long serialVersionUID = 2521556559394088770L;

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "doctor_id")
    private Integer doctorId;

    @Column(name = "create_time")
    private LocalDateTime createTime;
}
