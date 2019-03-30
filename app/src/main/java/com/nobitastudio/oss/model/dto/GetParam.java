package com.nobitastudio.oss.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/19 17:28
 * @description 生成 get请求中后面的参数  http:www.nobitastudio.cn/?key=value&key2=value2
 */
@Getter
@Setter
@AllArgsConstructor
public class GetParam {
    // 参数名
    private String key;
    // 值
    private String value;
}
