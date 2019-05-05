package com.niubility.demo.https;

import com.niubility.library.common.config.BaseConfig;
import com.niubility.library.http.base.BaseHttpAPI;
import com.niubility.library.http.base.BaseRetrofit;

import java.util.ArrayList;
import java.util.List;

public class HttpsAPI extends BaseHttpAPI {

    private static final HttpsAPI ourInstance = new HttpsAPI();

    public static HttpsAPI getInstance() {
        return ourInstance;
    }

    private HttpsAPI() {

    }


    private HttpServiceStore httpServiceStore;
    private HttpServiceVip httpService_vip;


    private List<HttpServiceStore> httpServiceStoreList;
    private List<HttpServiceVip> httpServiceVipList;


    /**
     * 获取接口服务----便利店
     *
     * @return
     */
    public HttpServiceStore httpServiceStore() {

        if (httpServiceStoreList == null) {
            httpServiceStoreList = new ArrayList<>();
            for (int i = 0; i < BaseConfig.environment_count; i++) {
                httpServiceStoreList.add(i, null);
            }
        }

        httpServiceStore = httpServiceStoreList.get(BaseConfig.environment_index);
        if (httpServiceStore == null) {
            httpServiceStore = BaseRetrofit.getInstance()
                    .getCurrentEnvRetrofit(array_key_url[0])
                    .create(HttpServiceStore.class);

            httpServiceStoreList.set(BaseConfig.environment_index, httpServiceStore);
        }

        return httpServiceStore;

    }

    /**
     * 获取接口服务----会员服务
     *
     * @return
     */
    public HttpServiceVip httpServiceVip() {

        if (httpServiceVipList == null) {
            httpServiceVipList = new ArrayList<>();
            for (int i = 0; i < BaseConfig.environment_count; i++) {
                httpServiceVipList.add(i, null);
            }
        }

        httpService_vip = httpServiceVipList.get(BaseConfig.environment_index);
        if (httpService_vip == null) {
            httpService_vip = BaseRetrofit.getInstance()
                    .getCurrentEnvRetrofit(array_key_url[1])
                    .create(HttpServiceVip.class);

            httpServiceVipList.set(BaseConfig.environment_index, httpService_vip);
        }

        return httpService_vip;

    }


//    public <T> T httpServiceVip(int keyIndex, T httpService, List<T> httpServiceList) {
//
//        if (httpServiceList == null) {
//            httpServiceList = new ArrayList<T>();
//            httpServiceList.set(0, null);
//            httpServiceList.set(1, null);
//            httpServiceList.set(2, null);
//        }
//
//        httpService_vip = httpServiceList.get(BaseConfig.environment_index);
//        if (httpService_vip == null) {
//            httpService_vip = BaseRetrofit.getInstance()
//                    .getCurrentEnvRetrofit(array_key_url[1])
//                    .create(HttpServiceVip.class);
//
//            httpServiceList.set(BaseConfig.environment_index, httpService_vip);
//        }
//
//        return httpService_vip;
//
//    }

    @Override
    protected String[] getUrlKeyArray() {
        return new String[] {
                "KEY_URL_STORE",
                "KEY_URL_VIP"
        };
    }

    @Override
    protected String[][] getUrlArray() {

        String[] array_url_store = new String[] {
                "https://api.store.ieasygo.cn", //正式
                "https://staging.api.store.ieasygo.cn", //预发布
                "https://dev.api.store.ieasygo.cn", //测试
        };

        String[] array_url_vip = new String[] {
                "https://api.vip.ieasygo.cn",
                "https://staging.api.vip.ieasygo.cn",
                "https://dev.api.vip.ieasygo.cn",
        };

        return new String[][]{
                array_url_store,
                array_url_vip
        };
    }
}
