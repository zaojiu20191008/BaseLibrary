package com.niubility.library.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * 单例，返回SharedPreference
 */
public class SharedPreferencesUtils {

    private static final String TAG = "SharedPreferencesUtils";

    /** 变量/常量说明 */
    public static final String PREFERENCE_NAME = "BaseConfig";
    private SharedPreferences mSharedPreference;

    public static SharedPreferencesUtils getInstance() {
        return Holder.sInstance;
    }

    private SharedPreferencesUtils(){}

    private static class Holder {
        private static final SharedPreferencesUtils sInstance = new SharedPreferencesUtils();
    }


    public SharedPreferences getSharedPreferences(Context context) {
        if(mSharedPreference == null)
//            mSharedPreference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            mSharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
        return mSharedPreference;
    }
}
