package com.niubility.library.base;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.niubility.library.common.config.BaseConfig;
import com.niubility.library.tools.CrashHandler;
import com.niubility.library.utils.FileUtils;
import com.niubility.library.utils.LogUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class BaseApplication extends Application {

    public static Context sApplication;
    protected boolean openCrashHandler = false;
    public static File logDir;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;

        ZXingLibrary.initDisplayOpinion(this);

        /* 加载全局配置 */
        BaseConfig.readConfig(this);

        if(isOpenCrashHandler()) {

            initDir();

            //定时清理日志
            LogUtils.getInstance().autoClearInDir(BaseApplication.logDir);

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


    private void initDir() {

        String internalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath();

        logDir = new File(internalStoragePath
                + File.separator + "zaojiu"
                + File.separator + "log");

        if (!logDir.exists()){
            logDir.mkdirs();
        }
    }

    public void onCrash(Thread t, Throwable e) {
        writeCrashLog(e);
    }


    private void writeCrashLog(Throwable e) {
        StringBuilder sb = new StringBuilder();
        String exceptionString = FileUtils.getExceptionString(e);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        String formatDate = simpleDateFormat.format(new Date());

        String content = sb.append("====================crash====================").append("\n")
                .append(formatDate).append("\n")
                .append(exceptionString)
                .append("\n")
                .append("\n")
                .toString();

        File testFile = new File(logDir, LogUtils.getInstance().getFormatFileName("crash"));
        FileUtils.writeToFile(testFile, content, true);
    }

//    private void restartAppDelayed(long delayMillis) {
//        Intent intent = new Intent(sApplication, MainActivity.class);
//        @SuppressLint("WrongConstant") PendingIntent restartIntent = PendingIntent.getActivity(
//                mContext, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
//        //退出程序
//        AlarmManager mgr = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
//        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + delayMillis,
//                restartIntent); // 5秒钟后重启应用
//
//        //结束进程之前可以把你程序的注销或者退出代码放在这段代码之前
//        android.os.Process.killProcess(android.os.Process.myPid());
//    }


}
