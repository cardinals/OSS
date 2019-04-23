package com.nobitastudio.oss.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/03 14:36
 * @description 用户绑定诊疗卡诊疗卡关系.
 */
@Data
@Entity
@Table(name = "bind")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bind implements Serializable {

    private static final long serialVersionUID = -9124180682454727169L;

    public Bind(Integer userId, String medicalCardId) {
        this.userId = userId;
        this.medicalCardId = medicalCardId;
    }

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "medical_card_id")
    private String medicalCardId;

    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 创建时进行初始化
     */
    public void init(){
        this.createTime = LocalDateTime.now();
    }
}
