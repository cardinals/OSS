package com.nobitastudio.oss.model.common;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/19 11:27
 * @description 服务端返回结果封装 正常 0， 业务异常 1，未捕获异常 2, 未授权 3
 */
public class ServiceResult<T> {

    public static final int STATE_SUCCESS = 0;
    public static final int STATE_APP_EXCEPTION = 1;
    public static final int STATE_EXCEPTION = 2;
    public static final int STATE_NO_SESSION = 3;

    public static ServiceResult<?> noSession = new ServiceResult<>(null, STATE_NO_SESSION);

    // 结果
    private T result;

    // 标识成功还是失败
    private int state;

    // 错误详情藐视 一般是string类型
    private Object error;

    // 错误码
    private String errorCode;

    public ServiceResult() {
    }

    public ServiceResult(int state) {
        this.state = state;
    }

    public ServiceResult(T result, int state) {
        this.result = result;
        this.state = state;
    }

    public T getResult() {
        return result;
    }

    public ServiceResult<T> setResult(T result) {
        this.result = result;
        return this;
    }

    public int getState() {
        return state;
    }

    public ServiceResult<T> setState(int state) {
        this.state = state;
        return this;
    }

    public Object getError() {
        return error;
    }

    public ServiceResult<T> setError(Object error) {
        this.error = error;
        return this;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public ServiceResult<T> setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public static <T> ServiceResult<T> success(T result) {
        return new ServiceResult<>(result, STATE_SUCCESS);
    }

    public static <T> ServiceResult<T> failure(Object result) {
        ServiceResult<T> f = new ServiceResult<>(null, STATE_APP_EXCEPTION);
        f.setError(result).setErrorCode(Constant.NORMAL_ERROR);
        return f;
    }

    public static <T> ServiceResult<T> failure(Object result, String errorCode) {
        ServiceResult<T> f = new ServiceResult<>(null, STATE_APP_EXCEPTION);
        f.setError(result).setErrorCode(errorCode);
        return f;
    }

    public static <T> ServiceResult<T> exception(Object result) {
        ServiceResult<T> f = new ServiceResult<>(null, STATE_EXCEPTION);
        f.setError(result);
        return f;
    }

    public static <T> ServiceResult<T> noSession(Object result) {
        ServiceResult<T> f = new ServiceResult<>(null, STATE_NO_SESSION);
        f.setError(result);
        return f;
    }

}
