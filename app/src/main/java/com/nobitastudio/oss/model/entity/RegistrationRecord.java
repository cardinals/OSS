package com.nobitastudio.oss.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/06 15:07
 * @description id 生成策略,通过雪花算法生成唯一id,配合挂号渠道作为 数据中心
 */
public class RegistrationRecord implements Serializable {

    private static final long serialVersionUID = -7653921447104816273L;

    private String id;

    private Integer userId;

    private Integer visitId;

    private String medicalCardId;

    private Integer diagnosisNo;

    private LocalDateTime createTime;
}
