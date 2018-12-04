package com.niubility.library.utils;

import com.google.gson.Gson;

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

}
