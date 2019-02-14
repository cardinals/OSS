package com.nobitastudio.oss.model.entity;

import com.nobitastudio.oss.model.enumeration.Area;
import java.io.Serializable;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 10:14
 * @description 诊疗室实例
 */
public class DiagnosisRoom implements Serializable {


    private static final long serialVersionUID = 2159462784859847255L;

    private Integer id;

    private String name;

    private String address;

    private Integer location;

    private Integer floor;

    private Area area;

    private Integer departmentId;
}
