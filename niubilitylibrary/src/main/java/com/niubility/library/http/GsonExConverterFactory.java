package com.niubility.library.http;

import com.google.gson.Gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.annotations.Nullable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by asus-e on 2018/1/24.
 */

public class GsonExConverterFactory extends Converter.Factory {

    private final Gson gson;

    public static GsonExConverterFactory create() {
        return new GsonExConverterFactory(new Gson());
    }

    public GsonExConverterFactory create(Gson gson) {
        return new GsonExConverterFactory(gson);
    }

    private GsonExConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    @Nullable
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new GsonResponseBody<>(gson, type);
    }

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new GsonResponseBody<>(gson, type);
    }


}
