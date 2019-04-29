package com.niubility.library.common.config;

import android.content.Context;
import android.content.SharedPreferences;

import com.niubility.library.BuildConfig;
import com.niubility.library.R;

public class BaseConfig {



    /**
     * 配置sharedPreference名称
     */
    public static final String sp_config = "BaseConfig";

    /**
     * 需要隐藏的preference的key值
     */
    public static String[] hideKeys;

    /**
     * 环境变量指示变量
     */
    public static int environment_index;
    /**
     * 环境数量
     */
    public static int environment_count;
    /**
     * ip/port
     */
    public static String environment_ip;

    /**
     * 读取配置信息
     */
    public static void readConfig(Context context) {
        SharedPreferences sp = context.getSharedPreferences(sp_config, Context.MODE_PRIVATE);
        environment_index = Integer.valueOf(sp.getString(context.getString(R.string.key_environment), BuildConfig.DEBUG? "2": "0"));

        environment_count = context.getResources().getStringArray(R.array.attr_environment).length;
        environment_ip = sp.getString(context.getString(R.string.key_ip_port), "192.168.1.1:8080");
    }


    /**
     * 设置IP和端口
     *
     * @param context
     * @param ipAndPort
     */
    public static void setIPAndPort(Context context, String ipAndPort) {
        SharedPreferences sp = context.getSharedPreferences(sp_config, Context.MODE_PRIVATE);
        sp.edit().putString(context.getString(R.string.key_ip_port), ipAndPort).apply();
    }
}
