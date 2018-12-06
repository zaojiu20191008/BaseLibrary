package com.niubility.library.utils;

import com.google.gson.Gson;

/**
 * 单例，返回Gson
 */
public class GsonUtils {

    private Gson mGson;

    private GsonUtils() {
        mGson = new Gson();
    }

    public static GsonUtils getInstance() {
        return Holder.sInstance;
    }

    static class Holder {
        static final GsonUtils sInstance = new GsonUtils();
    }

    public Gson getGson() {
        return mGson;
    }

}
