package com.nobitastudio.oss.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/03 14:36
 * @description 用户绑定诊疗卡诊疗卡关系.
 */
public class Bind implements Serializable {

    private static final long serialVersionUID = -9124180682454727169L;

    public Bind(Integer userId, String medicalCardId) {
        this.userId = userId;
        this.medicalCardId = medicalCardId;
        createTime = LocalDateTime.now();
    }

    private Integer id;

    private Integer userId;

    private String medicalCardId;

    private LocalDateTime createTime;

    /**
     * 创建时进行初始化
     */
    public void init(){
        this.createTime = LocalDateTime.now();
    }
}
