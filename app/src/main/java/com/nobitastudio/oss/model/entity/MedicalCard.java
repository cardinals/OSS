package com.nobitastudio.oss.model.entity;

import com.nobitastudio.oss.model.enumeration.Sex;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 16:08
 * @description 诊疗卡实例
 */
public class MedicalCard implements Serializable {

    private static final long serialVersionUID = -914979755295639051L;

    private String id;

    private String ownerName;

    private String ownerIdCard;

    private Sex ownerSex;

    private String ownerAddress;

    private String ownerMobile;

    private LocalDateTime createTime;

}
