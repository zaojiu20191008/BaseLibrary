package com.niubility.library.http;


import com.niubility.library.mvp.BaseRxView;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.reactivestreams.Subscriber;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 默认模板，统一管理多个Service接口(e.g. HttpService1、 HttpService2）
 * <p>
 * 根据此模板 编写api接口的获取逻辑
 * <p>
 * <p>
 * 调用方式e.g.  (API).getInstance.httpService()
 */
public class API {


//    private HttpService httpService;
//
//    public HttpService httpService() {
//        if(httpService == null) {
//            httpService = BaseRetrofit.getInstance()
//                    .getRetrofit(BuildConfig.API_URL)
//                    .create(HttpService.class);
//        }
//        return httpService;
//    }
//
//
//
//    public static HttpAPI getInstance() {
//        return Holder.sInstance;
//    }
//
//    private static class Holder {
//        private static HttpAPI sInstance = new HttpAPI();
//    }


}
