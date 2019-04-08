package com.niubility.library.http.base;

import com.niubility.library.common.config.Config;
import com.niubility.library.http.GsonExConverterFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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



    /** Map数组，数组下标对应环境配置的下标，Map里存放某个运行环境下的一个或多个Retrofit */
    private ArrayList<Map<String, Retrofit>> retrofitMapList = new ArrayList<>();

    /** Map数组，数组下标对应环境配置的下标，Map里存放某个运行环境下的一个或多个URL */
    private ArrayList<Map<String, String>> urlMapList;

    public ArrayList<Map<String, String>> getUrlMapList() {
        if (urlMapList == null) {
            urlMapList = new ArrayList<>();
        }
        return urlMapList;
    }


    private Map<String, Retrofit> currentEnvRetrofitMap;

    /**
     * 获取当前运行环境下URL对应的Retrofit
     *
     * @param urlKey URL的Key名
     * @return
     */
    public Retrofit getCurrentEnvRetrofit(String urlKey) {

        currentEnvRetrofitMap = retrofitMapList.get(Config.environment_index);

        if (currentEnvRetrofitMap.containsKey(urlKey)) {
            return currentEnvRetrofitMap.get(urlKey);
        }

        Retrofit retrofit = createrRetrofit(urlMapList.get(Config.environment_index).get(urlKey));
        currentEnvRetrofitMap.put(urlKey, retrofit);
        return retrofit;

    }

//====================================================================================

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
            mRetrofitHashMap.put(baseurl, retrofit);
        }

        return retrofit;
    }


    private Retrofit createrRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(HttpClient.getInstance().getOkHttpClient())
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(GsonExConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }




}
