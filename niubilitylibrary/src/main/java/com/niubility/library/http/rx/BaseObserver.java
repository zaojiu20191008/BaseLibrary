package com.niubility.library.http.rx;

import com.niubility.library.http.base.HttpResult;
import com.niubility.library.http.exception.ApiException;
import com.niubility.library.http.exception.HttpExceptionEngine;

import java.util.Map;

/**
 * 通用处理HttpResult逻辑
 * @param <T>
 */
public abstract class BaseObserver<T> extends BaseErrorObserver<HttpResult<T>> {

    @Override
    public void onNext(HttpResult<T> httpResult) {
        if(httpResult.isSuccessful()) {
            onSuccess(httpResult);
        } else {
            //-----------------业务逻辑错误---------------------
            throw new ApiException(httpResult.getErr_code(), httpResult.getErr_msg());
        }
    }

    @Override
    public void onError(Throwable e) {
//        ApiException apiException = HttpExceptionEngine.handleException(e);
//        onFailure(apiException.getErr_msg());


        Map<String, Object> map = HttpExceptionEngine.handleExceptionToMap(e);
        onFailure(map);

    }

    @Override
    public void onComplete() {

    }

    protected abstract void onSuccess(HttpResult<T> httpResult);
//    protected abstract void onFailure(String msg);
    protected abstract void onFailure(Map<String, Object> map);

}
