package com.niubility.library.base;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.niubility.library.tools.CrashHandler;
import com.niubility.library.tools.FileUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class BaseApplication extends Application {

    public static Context sApplication;
    protected boolean openCrashHandler = false;
    public File logDir;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;

        if(isOpenCrashHandler()) {

            initDir();

            CrashHandler crashHandler = CrashHandler.getInstance();
            crashHandler.init(sApplication, new CrashHandler.OnCrashHandleListener() {
                @Override
                public void onCrashHandle(Thread t, Throwable e) {
                    onCrash(t, e);
                }
            });
        }

    }

    protected boolean isOpenCrashHandler() {
        return openCrashHandler;
    }


}
