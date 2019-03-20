package com.nobitastudio.oss.model.dto;

import com.alibaba.fastjson.TypeReference;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/20 15:12
 * @description 用于映射为指定类型的策略
 */
public class ReflectStrategy<T> {

    // class 类型
    private Class<T> tClass;

    // 带有泛型的类型：map  list
    private TypeReference<T> typeReference;

    public ReflectStrategy(Class<T> tClass) {
        this.tClass = tClass;
    }

    public ReflectStrategy(TypeReference<T> typeReference) {
        this.typeReference = typeReference;
    }

    // 是否简单的class类型
    public boolean isClass() {
        return tClass == null;
    }

}
