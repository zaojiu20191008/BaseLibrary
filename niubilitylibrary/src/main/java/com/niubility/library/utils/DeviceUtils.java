package com.niubility.library.utils;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.niubility.library.service.InstallAccessibilityService;

import java.io.File;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class DeviceUtils {

    public static final String TAG = "DeviceUtils";
    private static String sMacAddress;

    /**
     * 获取MAC地址
     *
     * @return MAC地址(mMacAddress)
     */
    public static String getMacAddress() {
        if (TextUtils.isEmpty(sMacAddress)) {
            sMacAddress = "";
            try {
                List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
                for (NetworkInterface nif : all) {
                    if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                    byte[] macBytes = nif.getHardwareAddress();
                    if (macBytes == null) {
                        return sMacAddress;
                    }

                    StringBuilder res1 = new StringBuilder();
                    for (byte b : macBytes) {
                        res1.append(String.format("%02X:", b));
                    }
                    if (res1.length() > 0) {
                        res1.deleteCharAt(res1.length() - 1);
                    }
                    sMacAddress = res1.toString();
                    Log.i(TAG, "mac address====>>" + sMacAddress);
                    return sMacAddress;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                Log.i(TAG, "mac address====>> null");
                sMacAddress = "";
            }
        }
        return sMacAddress;
    }


    /**

     * getSerialNumber

     * @return result

     */

    public static String getSerialNumber(){

//        String serial = null;
//
//        try {
//
//            Class<?> c = Class.forName("android.os.SystemProperties");
//
//            Method get =c.getMethod("get", String.class);
//
//            serial = (String)get.invoke(c, "ro.serialno");
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//        }
//
//        return serial;

        return android.os.Build.SERIAL;


    }


    public static boolean checkRooted() {
        boolean result = false;
        try {
            result = new File("/system/bin/su").exists() || new File("/system/xbin/su").exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
