package com.niubility.library.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;

public class NetworkUtils {

    public static final String TAG = NetworkUtils.class.getSimpleName();

    public static final int NETWORK_WIFI = 0;
    public static final int NETWORK_MOBILE = 1;
    public static final int NETWORK_NONE = -1;

    public static int getNetworkState(Context context) {
        //得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        //如果网络连接，判断该网络类型
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                return NETWORK_WIFI;//wifi
            } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                return NETWORK_MOBILE;//mobile
            }
        } else {
            //网络异常
            return NETWORK_NONE;
        }
        return NETWORK_NONE;
    }

    public static boolean isConnected(int state) {
        return state != NETWORK_NONE;
    }
    public static boolean isAvalible(Context context) {
        if (context != null) {
            // 获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context
                    .CONNECTIVITY_SERVICE);
            // 获取NetworkInfo对象
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            //判断NetworkInfo对象是否为空
            if (networkInfo != null)
                return networkInfo.isAvailable();
        }
        return false;
    }



    /**
     * 判断网络是否连通
     * -c 次数
     * -w 超时时间  单位(秒)
     * @return 是否连通   exitValue = 0 表示 连通
     */
    public static boolean isNetworkOnline(String address) {
        Runtime runtime = Runtime.getRuntime();
        try {
            Log.i(TAG, "开始检测连通状态");
//            Process ipProcess = runtime.exec("ping -c 3 www.baidu.com");
            Process ipProcess = runtime.exec("ping -c 3 -w 5 " + address);
//            InputStreamReader r = new InputStreamReader(ipProcess.getInputStream());
//            LineNumberReader returnData = new LineNumberReader(r);
//            String returnMsg="";
//            String line = "";
//            while ((line = returnData.readLine()) != null) {
//                System.out.println(line);
//                returnMsg += line;
//            }
//            Log.i(TAG, "returnMsg:" + returnMsg);

            int exitValue = ipProcess.waitFor();
            Log.i(TAG, "Process:" + exitValue);
            return (exitValue == 0);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isNetworkOnline() {
        return isNetworkOnline("www.baidu.com");
    }
}
