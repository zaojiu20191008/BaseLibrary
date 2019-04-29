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
    private SharedPreferences.Editor editor;

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


    public void addSharedPreferenceLong(Context context, String key, long value) {

        if (mSharedPreference == null) {
            getSharedPreferences(context);
        }
        if (editor == null) {
            editor = mSharedPreference.edit();
        }
        editor.putLong(key, value);
        editor.apply();
    }

    public long getSharedPreferenceLong(Context context, String key, long defaultValue) {

        if (mSharedPreference == null) {
            getSharedPreferences(context);
        }
        return mSharedPreference.getLong(key, defaultValue);
    }

}
