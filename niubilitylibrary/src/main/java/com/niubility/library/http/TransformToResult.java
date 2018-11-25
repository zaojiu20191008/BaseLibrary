package com.niubility.library.http;

import io.reactivex.functions.Function;

public class TransformToResult<T> implements Function<HttpResult<T>, T> {

    @Override
    public T apply(HttpResult<T> httpResult) throws Exception {
        if(!httpResult.isSuccessful()) {
            throw new ApiException(httpResult.getErr_code(), httpResult.getErr_msg());
        }
        return httpResult.getResult();
    }
}
