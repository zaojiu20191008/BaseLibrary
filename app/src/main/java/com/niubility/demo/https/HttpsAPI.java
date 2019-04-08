package com.niubility.demo.https;

import com.niubility.library.common.config.Config;
import com.niubility.library.http.base.BaseHttpAPI;
import com.niubility.library.http.base.BaseRetrofit;

import java.util.ArrayList;
import java.util.List;

public class HttpsAPI extends BaseHttpAPI {

    private static final HttpsAPI ourInstance = new HttpsAPI();

    static HttpsAPI getInstance() {
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
            httpServiceStoreList.set(0, null);
            httpServiceStoreList.set(1, null);
            httpServiceStoreList.set(2, null);
        }

        httpServiceStore = httpServiceStoreList.get(Config.environment_index);
        if (httpServiceStore == null) {
            httpServiceStore = BaseRetrofit.getInstance()
                    .getCurrentEnvRetrofit(array_key_url[0])
                    .create(HttpServiceStore.class);

            httpServiceStoreList.set(Config.environment_index, httpServiceStore);
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
            httpServiceVipList.set(0, null);
            httpServiceVipList.set(1, null);
            httpServiceVipList.set(2, null);
        }

        httpService_vip = httpServiceVipList.get(Config.environment_index);
        if (httpService_vip == null) {
            httpService_vip = BaseRetrofit.getInstance()
                    .getCurrentEnvRetrofit(array_key_url[1])
                    .create(HttpServiceVip.class);

            httpServiceVipList.set(Config.environment_index, httpService_vip);
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
//        httpService_vip = httpServiceList.get(Config.environment_index);
//        if (httpService_vip == null) {
//            httpService_vip = BaseRetrofit.getInstance()
//                    .getCurrentEnvRetrofit(array_key_url[1])
//                    .create(HttpServiceVip.class);
//
//            httpServiceList.set(Config.environment_index, httpService_vip);
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
                "https://api.store.ieasygo.cn/", //正式
                "https://staging.api.store.ieasygo.cn", //预发布
                "https://dev.api.store.ieasygo.cn", //测试
        };

        String[] array_url_vip = new String[] {
                "https://api.vip.ieasygo.cn/",
                "https://staging.api.vip.ieasygo.cn",
                "https://dev.api.vip.ieasygo.cn",
        };

        return new String[][]{
                array_url_store,
                array_url_vip
        };
    }
}
