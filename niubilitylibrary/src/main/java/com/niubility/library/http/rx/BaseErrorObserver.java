package com.niubility.library.http.rx;

import com.niubility.library.http.exception.HttpExceptionEngine;

import java.util.Map;

import io.reactivex.observers.DisposableObserver;

/**
 * 基础错误处理
 * @param <T>
 */
public abstract class BaseErrorObserver<T> extends DisposableObserver<T> {


    @Override
    public void onError(Throwable e) {

        Map<String, Object> map = HttpExceptionEngine.handleExceptionToMap(e);
        onFailure(map);

    }

    @Override
    public void onComplete() {

    }

    protected abstract void onFailure(Map<String, Object> map);

}
