package com.niubility.library.http;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.niubility.library.http.base.IHttpResult;
import com.niubility.library.http.exception.ApiException;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by asus-e on 2018/1/24.
 */

final class GsonResponseBody<T> implements Converter<ResponseBody, T> {

    private final Gson gson;

    private final Type type;

    GsonResponseBody(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }


    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        try {
            Gson mGson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {

                    switch (f.getName()) {
                        default:
                            return false;
                        case "result":
                        case "data":
                            return true;
                    }
//                    return f.getName().equals("data");
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            }).create();
            //预解析，判断业务是否成功
            IHttpResult result = mGson.fromJson(response, type);

            int code = result.code();
            if (result.isSuccessful()) {
                return gson.fromJson(response, type);
            } else {
                throw new ApiException(code, result.msg());
            }
        } finally {
            value.close();
        }
    }
}