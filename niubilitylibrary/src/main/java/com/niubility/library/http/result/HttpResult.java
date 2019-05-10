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

    private int ret;
    private String error_msg;
    private T data;

    private int return_code;
    private String return_msg;

    @Override
    public int code() {
        if(err_code != 0) {
            return err_code;
        } else if(ret != 0) {
            return ret;
        } else if(return_code != 0) {
            return return_code;
        }
        return 0;
    }

    @Override
    public String msg() {
        if(err_msg != null) {
            return err_msg;
        } else if(error_msg != null) {
            return error_msg;
        } else if(return_msg != null) {
            return return_msg;
        }
        return "";
    }

    @Override
    public T result() {
        if(result != null) {
            return result;
        } else if(data != null) {
            return data;
        }
        return null;
    }

    @Override
    public boolean isSuccessful() {
        return err_code == 200 || ret == 200 || return_code == 200;
    }
}
