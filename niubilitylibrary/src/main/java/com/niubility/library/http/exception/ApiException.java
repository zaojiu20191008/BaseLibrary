package com.niubility.library.http.exception;

/**
 * 服务器正常返回，但err_code != 200的情况下 抛出此异常
 */
public class ApiException extends RuntimeException {

    private int err_code;
    private String err_msg;

    public ApiException(int err_code, String err_msg) {
        this.err_code = err_code;
        this.err_msg = err_msg;
    }

    @Override
    public String toString() {
        return "ApiException{" +
                "err_code=" + err_code +
                ", err_msg='" + err_msg + '\'' +
                '}';
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
}
