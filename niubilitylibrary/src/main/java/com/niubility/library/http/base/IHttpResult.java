package com.niubility.library.http.base;

/**
 * 网络响应体 接口
 * @param <T> 数据
 */
public interface IHttpResult<T> {

    /**
     * 错误码
     */
    int code();

    /**
     * 错误描述
     */
    String msg();

    /**
     * 数据
     */
    T result();

    /**
     * 业务是否成功
     */
    boolean isSuccessful();
}
