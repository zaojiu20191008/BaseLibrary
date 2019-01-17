package com.niubility.library.utils;

import android.util.Log;

import org.xutils.common.util.MD5;

/**
 * Created by asus-e on 2018/1/22.
 */

public class GetSign {
    private static final String web_key = "723949279";
    private static final String web_secret = "d908936a0d53a459c117227b8f1d8608";

    public static String getSign(long time) {
        String s = web_key + String.valueOf(time) + web_secret;
        Log.i("MD5测试", MD5.md5(web_key + "1516602310" + web_secret) + " <----------------------> " + s);
        return MD5.md5(s);
    }

    public static String getSigns(long time) {
        String s = "24" + String.valueOf(time) + web_secret;
        return MD5.md5(s);
    }
}
