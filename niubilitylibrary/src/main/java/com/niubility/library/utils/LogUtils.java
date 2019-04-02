package com.niubility.library.utils;

import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class LogUtils {

    public static final String TAG = LogUtils.class.getSimpleName();

    /**清理间隔天数*/
    public static int clearInterval = 7;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);


    /**
     * 获取格式化的log名称
     */
    public static String getFormatFileName(String name) {
        String day = sdf.format(new Date());
        return name + "-" + day + ".txt";
    }

    /**
     * 清理日志
     * @param dir
     */
    public static void autoClearInDir(File dir) {
        if(dir != null && dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if(files != null) {
                for (File file : files) {
                    autoClear(file);
                }
            }
        }
    }

    public static void autoClear(File file) {
        if(file == null || !file.exists()) {
            return;
        }
        String name = file.getName();
        //截取扩展名以外的名字
        int last = name.lastIndexOf(".");
        name = name.substring(0, last);
        String[] split = name.split("-");
        if(split.length != 4) {
            return;
        }

        int year = 0;
        int month = 0;
        int day = 0;
        try {
            year = Integer.valueOf(split[1]);
            month = Integer.valueOf(split[2]);
            day = Integer.valueOf(split[3]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month-1, day);
        long logTime = calendar.getTimeInMillis();

        if(System.currentTimeMillis() - logTime > clearInterval * 24 * 60 * 60 * 1000) {
            Log.i(TAG, "autoClear: 删除 -> " + name);
            file.delete();
        }
    }

}
