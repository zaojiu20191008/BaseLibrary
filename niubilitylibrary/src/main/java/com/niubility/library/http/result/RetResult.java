package com.niubility.library.http.result;

import com.niubility.library.http.base.IHttpResult;

import java.io.Serializable;

/**
 * 服务器通用返回格式
 */
public class RetResult<T> implements IHttpResult<T>, Serializable {

    private int ret;
    private String error_msg;
    private T data;

    @Override
    public int code() {
        return ret;
    }

    @Override
    public String msg() {
        return error_msg;
    }

    @Override
    public T result() {
        return data;
    }

    @Override
    public boolean isSuccessful() {
        return ret == 200;
    }
}
