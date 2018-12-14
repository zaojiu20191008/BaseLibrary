package com.niubility.library.utils;

import android.text.TextUtils;
import android.util.Log;

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
}
