package com.niubility.library.http;

import android.content.Context;

import com.niubility.library.BuildConfig;
import com.niubility.library.base.BaseApplication;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class HttpClient {

    private final static String TAG = "HttpClient";
    public OkHttpClient mOkHttpClient;

    /**
     * 根据类型生成并获取实例
     */
    public static HttpClient getInstance() {
        return Holder.sHttpClient;
    }

    public static class Holder {
        static HttpClient sHttpClient = new HttpClient(BaseApplication.sApplication.getApplicationContext());
    }

    public HttpClient(Context context) {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder.addInterceptor(httpLoggingInterceptor);
        }
        okHttpClientBuilder.retryOnConnectionFailure(true)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS);
        mOkHttpClient = okHttpClientBuilder.build();
    }
}
