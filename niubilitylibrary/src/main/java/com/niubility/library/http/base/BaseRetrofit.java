package com.niubility.library.http.base;

import java.util.HashMap;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * 单例,返回Retrofit
 */
public class BaseRetrofit {


    private HashMap<String, Retrofit> mRetrofitHashMap = new HashMap<>();

    public static BaseRetrofit getInstance() {
        return Holder.sInstance;
    }

    private static class Holder {
        private static BaseRetrofit sInstance = new BaseRetrofit();
    }


    private BaseRetrofit() {
    }


    /**
     * 获取retrofit的实例
     *
     * @return Retrofit
     */
    public Retrofit getRetrofit(String baseurl) {
        Retrofit retrofit;

        if (mRetrofitHashMap.containsKey(baseurl)) {
            retrofit = mRetrofitHashMap.get(baseurl);
        } else {
            retrofit = createrRetrofit(baseurl);
        }

        return retrofit;
    }


    private Retrofit createrRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(HttpClient.getInstance().getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }




}
