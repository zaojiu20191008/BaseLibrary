package com.niubility.library.http;

import java.util.Map;

import io.reactivex.observers.DisposableObserver;

public abstract class BaseObserver<T> extends DisposableObserver<HttpResult<T>> {

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


        Map map = HttpExceptionEngine.handleExceptionToMap(e);
        onFailure(map);

    }

    @Override
    public void onComplete() {

    }

    protected abstract void onSuccess(HttpResult<T> httpResult);
//    protected abstract void onFailure(String msg);
    protected abstract void onFailure(Map map);

}
