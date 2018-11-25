package com.niubility.library.http;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class HttpClient {

    private final static String TAG = "HttpClient";
    private OkHttpClient mOkHttpClient;

    private static final long DEFAULT_CONNECT_TIMEOUT = 10;
    private static final long DEFAULT_WRITE_TIMEOUT  = 10;
    private static final long DEFAULT_READ_TIMEOUT   = 10;

    /**
     * 根据类型生成并获取实例
     */
    public static HttpClient getInstance() {
        return Holder.sHttpClient;
    }

    public static class Holder {
        static HttpClient sHttpClient = new HttpClient();
    }

    private HttpClient() {

        //日志拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger(){
            @Override
            public void log(String message) {
                Log.i(TAG, "log: " + message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //可统一添加请求头
//        Interceptor networkInterceptor = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request original = chain.request();
//                Request request = original.newBuilder()
//                        .addHeader()
//                        .addHeader()
//                        .method(original.method(), original.body())
//                        .build();
//
//                return chain.proceed(request);
//            }
//        };


        mOkHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)
                            .addInterceptor(httpLoggingInterceptor)
//                            .addInterceptor(networkInterceptor)
                            .retryOnConnectionFailure(true)
                            .build();
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }


}
