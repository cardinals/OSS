package com.nobitastudio.oss.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/03 14:36
 * @description 用户收藏医生. 收藏关系
 */
public class CollectDoctor  implements Serializable {

    private static final long serialVersionUID = 2521556559394088770L;

    private Integer id;

    private Integer userId;

    private Integer doctorId;

    private LocalDateTime createTime;
}
