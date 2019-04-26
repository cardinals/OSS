package com.nobitastudio.oss.model.vo;

import com.nobitastudio.oss.model.entity.CheckItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/04/25 22:32
 * @description 这是类描述
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckItemAndCount {

    //检查项
    private CheckItem checkItem;

    // 数量
    private Integer count;
}
