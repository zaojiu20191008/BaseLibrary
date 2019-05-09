package com.niubility.library.http.result;

import com.niubility.library.http.base.IHttpResult;

import java.io.Serializable;

/**
 * 服务器通用返回格式
 */
public class ReturnResult<T> implements IHttpResult<T>, Serializable {

    private int return_code;
    private String return_msg;
    private T data;

    @Override
    public int code() {
        return return_code;
    }

    @Override
    public String msg() {
        return return_msg;
    }

    @Override
    public T result() {
        return data;
    }

    @Override
    public boolean isSuccessful() {
        return return_code == 200;
    }
}
