package com.niubility.library.http;

import java.util.Map;

import io.reactivex.observers.DisposableObserver;

public abstract class BaseResultObserver<T> extends DisposableObserver<T> {

    @Override
    public void onNext(T t) {
        onSuccess(t);
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

    protected abstract void onSuccess(T result);
//    protected abstract void onFailure(String msg);
    protected abstract void onFailure(Map map);


}
