package com.niubility.demo.https;


import com.niubility.demo.bean.InitResult;
import com.niubility.demo.bean.RequestResult;
import com.niubility.library.http.base.HttpResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

import java.util.Map;

public interface HttpServiceStore {


    @GET("/api/weixin/minapp/eg_s_debug/pay/counter_init")
    Observable<HttpResult<RequestResult>> request(@QueryMap Map<String, Object> requestMap);

    @GET("/api/weixin/minapp/eg_s_debug/pay/counter_init")
    Observable<HttpResult<InitResult>> init(@QueryMap Map<String, Object> requestMap);


}
