package com.niubility.library.http;

import okhttp3.OkHttpClient;

/**
 * 配置接口，用于Retrofit配置BaseUrl及 OkhttpClient
 */
public interface HttpConfig {

    /**
     * 通用请求地址
     *
     * @return
     */
    String configBaseUrl();

    /**
     * 网络客户端
     *
     * @return
     */
    OkHttpClient configClient();

}
