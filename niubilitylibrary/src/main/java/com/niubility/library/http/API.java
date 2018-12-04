package com.niubility.library.http;

import okhttp3.OkHttpClient;


/**
 * 默认模板，统一管理多个Service接口(e.g. HttpService1、 HttpService2）
 * <p>
 * 子类必须重写getApiUrl() 以正确配置Retrofit
 * <p>
 * 基础库中 HttpService为空实现， getHttpService()提供参考，根据项目需求具体编写逻辑
 * <p>
 * 调用方式e.g.  API.getInstance.getHttpService()
 */
public class API {

    private String DEFAULT_API_URL;
    private HttpService mHttpService;

    public API() {
        DEFAULT_API_URL = getApiUrl();
    }

    /**
     * @return 默认的BaseUrl地址
     */
    protected String getApiUrl() {
        return "";
    }


    public static API getInstance() {
        return Holder.sInstance;
    }

    private static class Holder {
        private static API sInstance = new API();
    }

    /**
     * 默认配置
     */
    protected HttpConfig mDefaultHttpConfig = new HttpConfig() {
        @Override
        public String configBaseUrl() {
            return DEFAULT_API_URL;
        }

        @Override
        public OkHttpClient configClient() {
            return HttpClient.getInstance().getOkHttpClient();
        }

    };

    public HttpService getHttpService() {
        if(mHttpService == null) {
            mHttpService = BaseRetrofit.getInstance()
                    .getRetrofit(mDefaultHttpConfig)
                    .create(HttpService.class);
        }
        return mHttpService;
    }


}
