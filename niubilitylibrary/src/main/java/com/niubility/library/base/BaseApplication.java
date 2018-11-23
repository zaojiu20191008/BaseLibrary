package com.niubility.library.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class BaseApplication extends Application {

    public static Context sApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;

    }


}
