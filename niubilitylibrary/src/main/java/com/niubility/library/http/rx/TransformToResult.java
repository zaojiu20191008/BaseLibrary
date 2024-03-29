package com.niubility.library.http.rx;

import com.niubility.library.http.base.IHttpResult;
import com.niubility.library.http.exception.ApiException;

import io.reactivex.functions.Function;

/**
 * 从服务器通用返回格式中 取出关键数据，否则抛出 ApiException异常
 */
public class TransformToResult<T> implements Function<IHttpResult<T>, T> {

    @Override
    public T apply(IHttpResult<T> httpResult) throws Exception {
//        T result = httpResult.getResult();
//        if(result == null) {
//            throw new ApiException(0, "服务器返回格式与预定义解析格式不一样");
//        }
        if(!httpResult.isSuccessful()) {
            throw new ApiException(httpResult.code(), httpResult.msg());
        }

        return httpResult.result();
    }
}
