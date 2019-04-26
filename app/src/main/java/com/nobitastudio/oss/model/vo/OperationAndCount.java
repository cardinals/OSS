package com.nobitastudio.oss.model.vo;

import com.nobitastudio.oss.model.entity.OperationItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/04/26 12:21
 * @description 这是类描述
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperationAndCount {

    // 手术项
    private OperationItem operationItem;

    // 数量
    private Integer count;
}
