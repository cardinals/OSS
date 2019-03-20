package com.nobitastudio.oss.model.entity;

import com.nobitastudio.oss.model.enumeration.Channel;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/06 15:07
 * @description id 生成策略,通过雪花算法生成唯一id,配合挂号渠道作为 数据中心
 */
@Data
@Entity
@Table(name = "registration_record")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRecord implements Serializable {

    private static final long serialVersionUID = -7653921447104816273L;

    public RegistrationRecord(Channel channel, Integer userId, Integer visitId, String medicalCardId, Integer diagnosisNo) {
        this.userId = userId;
        this.visitId = visitId;
        this.medicalCardId = medicalCardId;
        this.diagnosisNo = diagnosisNo;
        createTime = LocalDateTime.now();
    }

    @Column(name = "id")
    @Id
    private String id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "visit_id")
    private Integer visitId;

    @Column(name = "medical_card_id")
    private String medicalCardId;

    @Column(name = "diagnosis_no")
    private Integer diagnosisNo;

    @Column(name = "create_time")
    private LocalDateTime createTime;
}
