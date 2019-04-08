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
     * 环境变量指示变量
     */
    public static int environment_index;
    /**
     * 环境数量
     */
    public static int environment_count;

    /**
     * 读取配置信息
     */
    public static void readConfig(Context context) {
        SharedPreferences sp = context.getSharedPreferences(sp_config, Context.MODE_PRIVATE);
        environment_index = Integer.valueOf(sp.getString(context.getString(R.string.key_environment), BuildConfig.DEBUG? "0": "2"));

        environment_count = context.getResources().getStringArray(R.array.attr_environment).length;

    }
}
