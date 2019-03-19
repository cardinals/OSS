package com.nobitastudio.oss.model.dto;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/19 17:28
 * @description 生成 get请求中后面的参数  http:www.nobitastudio.cn/?key=value&key2=value2
 */
public class GetParam {
    // 参数名
    private String key;
    // 值
    private String value;

    public GetParam() {
    }

    public GetParam(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
