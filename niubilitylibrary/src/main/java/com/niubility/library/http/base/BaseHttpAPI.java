package com.niubility.library.http.base;

import com.niubility.library.common.config.BaseConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseHttpAPI {

    protected String[] array_key_url;
    protected String[][] array_url;

    protected BaseHttpAPI() {
        init();
    }

    public void init() {
        array_key_url = getUrlKeyArray();
        array_url = getUrlArray();

        if(array_key_url.length == 0 || array_url.length == 0) {
            throw new RuntimeException("请配置好URL");
        }

        init(array_key_url, array_url);
    }


    public void init(String[] urlKeyArray, String[]... urlTwoDimensionArray) {

        ArrayList<Map<String, String>> urlMapListTemp = BaseRetrofit.getInstance().getUrlMapList();

        BaseRetrofit.getInstance().reset();

        /**用来存储某一环境下的一个或多个域名*/
        HashMap<String, String> urlMapTemp;

        for (int i = 0; i < BaseConfig.environment_count; i++) {
            urlMapTemp = new HashMap<>();
            for (int j = 0; j < urlKeyArray.length; j++) {
                urlMapTemp.put(urlKeyArray[j], urlTwoDimensionArray[j][i]);
            }
            urlMapListTemp.add(i, urlMapTemp);
        }

    }

    /**
     * 获取 URL键名数组，元素为自定义的URL key值
     *
     * 比如：
     * new String[]{
     *     "KEY_URL_STORE",
     *     "KEY_URL_VIP"
     * }
     *
     * */
    protected abstract String[] getUrlKeyArray();


    /**
     * 获取 URL二维数组，
     * 第一维指代项目中使用到的各种URL，
     * 第二维存储着每种URL在各种运行环境下的URL值，且URL值需按照正式、预发布、测试的顺序来存储
     * 比如：
     * <p>
     *
     *     private static final String[] array_url_store = new String[] {<p>
     *           "https://api.store.ieasygo.cn/", //正式<p>
     *           "https://staging.api.store.ieasygo.cn", //预发布<p>
     *           "https://dev.api.store.ieasygo.cn", //测试<p>
     *     };<p>
     * <p>
     *     private static final String[] array_url_vip = new String[] {<p>
     *           "https://api.vip.ieasygo.cn/", //正式<p>
     *           "https://staging.api.vip.ieasygo.cn", //预发布<p>
     *           "https://dev.api.vip.ieasygo.cn", //测试<p>
     *     };<p>
     * <p>
     *     String[][] urlArray = { array_url_store, array_url_vip }; <p>
     * <p>
     * */
    protected abstract String[][] getUrlArray();



}
