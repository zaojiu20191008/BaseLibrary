package com.niubility.library.http.result;

import com.niubility.library.http.base.IHttpResult;

import java.io.Serializable;

/**
 * 服务器通用返回格式
 */
public class HttpResult<T> implements IHttpResult<T>, Serializable {

    private int err_code;
    private String err_msg;
    private T result;

    @Override
    public int code() {
        return err_code;
    }

    @Override
    public String msg() {
        return err_msg;
    }

    @Override
    public T result() {
        return result;
    }

    @Override
    public boolean isSuccessful() {
        return err_code == 200;
    }
}
