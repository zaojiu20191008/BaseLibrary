package com.niubility.library.http;

import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.niubility.library.http.base.HttpResult;
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
                    return f.getName().equals("data");
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            }).create();
            HttpResult result = mGson.fromJson(response, HttpResult.class);
            int err_code = result.getErr_code();
            int ret = result.getRet();
            int return_code = result.getReturn_code();
            if ((err_code == 200) || (ret == 200)
                    || return_code == 200) {
                return gson.fromJson(response, type);
            } else {
                Log.i("GsonResponseBody", result.toString());
                ApiException resultException;
                if(err_code != 200 && ret == 0 && return_code == 0) {
                    resultException = new ApiException(err_code, result.getErr_msg());
                } else if(err_code == 0 && ret != 200 && return_code == 0) {
                    resultException = new ApiException(ret,
                            result.getErr_msg() != null ? result.getErr_msg() : result.getError_msg());
                } else {
                    resultException = new ApiException(return_code, result.getReturn_msg());
                }
                throw resultException;
            }
        } finally {
            value.close();
        }
    }
}