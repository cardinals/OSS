package com.nobitastudio.oss.model.entity;

import com.nobitastudio.oss.model.enumeration.Sex;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 16:08
 * @description 诊疗卡实例
 */
@Data
@Entity
@Table(name = "medical_card")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalCard implements Serializable {

    private static final long serialVersionUID = -914979755295639051L;

    @Column(name = "id")
    @Id
    private String id;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "owner_id_card")
    private String ownerIdCard;

    @Column(name = "owner_sex")
    @Enumerated(EnumType.STRING)
    private Sex ownerSex;

    @Column(name = "owner_address")
    private String ownerAddress;

    @Column(name = "owner_mobile")
    private String ownerMobile;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "password")
    private String password;

    /**
     * 更新诊疗卡信息
     * @param medicalCard
     */
    public MedicalCard update(MedicalCard medicalCard) {
        if (medicalCard.getOwnerName() != null) {
            this.ownerName = medicalCard.getOwnerName();
        }
        if (medicalCard.getOwnerIdCard() != null) {
            this.ownerIdCard = medicalCard.getOwnerIdCard();
        }
        if (medicalCard.getOwnerSex() != null) {
            this.ownerSex = medicalCard.getOwnerSex();
        }
        if (medicalCard.getOwnerAddress() != null) {
            this.ownerAddress = medicalCard.getOwnerAddress();
        }
        if (medicalCard.getOwnerMobile() != null) {
            this.ownerMobile = medicalCard.getOwnerMobile();
        }
        return this;
    }

    // 检查创建时的诊疗卡信息是否完整
    public boolean createInfoIsComplete() {
        return ownerMobile != null && ownerName != null && ownerIdCard != null && ownerSex != null && ownerAddress != null && password != null;
    }

}
