package com.niubility.library.tools;

import android.content.Context;

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public final String TAG = getClass().getSimpleName();
    private static CrashHandler INSTANCE = new CrashHandler();
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private OnCrashHandleListener mListener;

    public interface OnCrashHandleListener {
        void onCrashHandle(Thread t, Throwable e);
    }


    private CrashHandler() {
    }


    public static CrashHandler getInstance() {
        return INSTANCE;
    }


    public void init(Context ctx, OnCrashHandleListener listener) {
        mContext = ctx;
        mListener = listener;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {

        e.printStackTrace();
        if(mListener != null) {
            mListener.onCrashHandle(t, e);
        }
//        writeCrashLog(e);
//        restartAppDelayed(2000);
    }
//
//    private void writeCrashLog(Throwable e) {
//        StringBuilder sb = new StringBuilder();
//        String exceptionString = FileUtils.getExceptionString(e);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
//        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
//        String formatDate = simpleDateFormat.format(new Date());
//
//        String content = sb.append("====================crash====================").append("\n")
//                .append(formatDate).append("\n")
//                .append("订单号：" + BaseConfig.out_trade_no).append("\n")
//                .append(exceptionString)
//                .append("\n")
//                .append("\n")
//                .toString();
//
//        File testFile = new File(MyApplication.logDir, "crash.txt");
//        FileUtils.writeToFile(testFile, content, true);
//    }
//
//    private void restartAppDelayed(long delayMillis) {
//        Intent intent = new Intent(mContext, MainActivity.class);
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
