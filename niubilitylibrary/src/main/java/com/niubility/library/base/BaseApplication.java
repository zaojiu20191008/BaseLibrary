package com.niubility.library.base;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {

    public static Context sApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;

    }


}
