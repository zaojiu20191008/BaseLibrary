package com.niubility.library.http.rx;

import com.niubility.library.http.base.IHttpResult;
import com.niubility.library.http.exception.ApiException;
import com.niubility.library.http.exception.HttpExceptionEngine;

import java.util.Map;

/**
 * 通用处理HttpResult逻辑
 * @param <T>
 */
public abstract class BaseObserver<T> extends BaseErrorObserver<IHttpResult<T>> {

    @Override
    public void onNext(IHttpResult<T> httpResult) {
        if(httpResult.isSuccessful()) {
            onSuccess(httpResult);
        } else {
            //-----------------业务逻辑错误---------------------
            throw new ApiException(httpResult.code(), httpResult.msg());
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

    protected abstract void onSuccess(IHttpResult<T> httpResult);
//    protected abstract void onFailure(String msg);
    protected abstract void onFailure(Map<String, Object> map);

}
