package com.niubility.library.http.rx;

import com.niubility.library.http.exception.HttpExceptionEngine;

import java.util.Map;

/**
 * 通用处理HttpResult剥离出来的关键数据逻辑
 * @param <T>
 */
public abstract class BaseResultObserver<T> extends BaseErrorObserver<T> {

    @Override
    public void onNext(T t) {
        onSuccess(t);
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

    protected abstract void onSuccess(T result);
    protected abstract void onFailure(Map<String, Object> map);


}
