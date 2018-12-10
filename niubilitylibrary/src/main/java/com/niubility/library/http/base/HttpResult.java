package com.niubility.library.http.base;

import java.io.Serializable;

/**
 * 服务器通用返回格式
 */
public class HttpResult<T> implements Serializable {

    private int err_code;
    private String err_msg;
    private String meta;
    private T result;


    public boolean isSuccessful() {
        return err_code == 200;
    }

    public int getErr_code() {
        return err_code;
    }

    public void setErr_code(int err_code) {
        this.err_code = err_code;
    }

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
