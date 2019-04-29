package com.niubility.demo.common;

import android.content.Context;
import android.content.SharedPreferences;
import com.niubility.demo.bean.InitResult;
import com.niubility.library.common.config.BaseConfig;

import java.util.Date;

public class LocalConfig extends BaseConfig {


    public static String ip = null;//uhf
    public static int port = 7777;
    public static String getRfidsOrder = "read";
    public static String resetRfidOrder = "cuid,1";
    public static boolean isReset = false;
    public static String openDoorOrder = "switch,1:5";


    /**
     * 设备序列号
     */
    public static String serial_number;
    /**
     * 设备WLANMAC地址
     */
    public static String wlan_mac;



    /**
     * 店铺ID
     */
    public static String shop_id;
    /**
     * 对应门店的店名
     */
    public static String store_name;
    /**
     * 对应门店的
     */
    public static String asid;
    /**
     * 公众号
     */
    public static String appid;
    /**
     * 商户号
     */
    public static String mch_id;
    /**
     * 特约商户公众号
     */
    public static String sub_appid = "wx1d1efad39ae132f1";
    /**
     * 特约商户号
     */
    public static String sub_mch_id = "1487696602";
    /**
     * 商户密匙
     */
    public static String mch_key;


    /**
     * 微信人脸扫描SDK初始数据
     */
    public static String rawdata;

    /**
     * 微信人脸扫描SDK认证信息
     */
    public static String wxAuthinfo;

    /**
     * 微信人脸扫描SDK认证信息有效时间
     */
    public static long wxExpires_in;
    /**
     * 微信人脸扫描SDK认证信息有效截止时间
     */
    public static long wxValidity;

    /**
     * 订单号
     */
    public static String order_no;
    /**
     * 订单ID
     */
    public static long order_id;



    /**
     * 保存配置数据
     *
     * @param initResult
     */
    public static void saveLocalConfig(Context context, InitResult initResult) {
        LocalConfig.shop_id = initResult.getShop_id();
        LocalConfig.asid = initResult.getAsid();
        LocalConfig.store_name = initResult.getStore_name();
        LocalConfig.appid = initResult.getAppid();
        LocalConfig.mch_id = initResult.getMch_id();
        LocalConfig.sub_appid = initResult.getSub_appid();
        LocalConfig.sub_mch_id = initResult.getSub_mch_id();

        SharedPreferences.Editor editor = context.getSharedPreferences(LocalConstants.LocalConfig, Context.MODE_PRIVATE).edit();
        editor.putString("shop_id", LocalConfig.shop_id);
        editor.putString("asid", LocalConfig.asid);
        editor.putString("store_name", LocalConfig.store_name);
        editor.putString("appid", LocalConfig.appid);
        editor.putString("mch_id", LocalConfig.mch_id);
        editor.putString("sub_appid", LocalConfig.sub_appid);
        editor.putString("sub_mch_id", LocalConfig.sub_mch_id);
        editor.apply();
    }


    public static void saveAuthInfo(Context context, String authInfo, String expires_in) {

        wxAuthinfo = authInfo;
        wxExpires_in = Long.valueOf(expires_in);
        wxValidity = new Date().getTime() + 1000 * (wxExpires_in - 60);

        SharedPreferences.Editor editor = context.getSharedPreferences(LocalConstants.WxAuthInfo, Context.MODE_PRIVATE).edit();
        editor.putString("WxAuthinfo", wxAuthinfo);
        editor.putLong("wxExpires_in", wxExpires_in);
        editor.putLong("wxValidity", wxValidity);

        editor.apply();
    }


}
