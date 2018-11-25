package com.niubility.library.http;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * 单例,根据配置返回Retrofit
 */
public class BaseRetrofit {

    private Retrofit mDefaultRetrofit;

    public static BaseRetrofit getInstance() {
        return Holder.sInstance;
    }

    private static class Holder {
        private static BaseRetrofit sInstance = new BaseRetrofit();
    }


    private BaseRetrofit() {

    }

    public Retrofit getRetrofit(HttpConfig httpConfig) {
        if(mDefaultRetrofit == null) {
            mDefaultRetrofit = new Retrofit.Builder()
                    .baseUrl(httpConfig.configBaseUrl())
                    .client(httpConfig.configClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

        }
        return mDefaultRetrofit;

    }


}
