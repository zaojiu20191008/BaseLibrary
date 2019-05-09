package com.niubility.demo.https;


import com.niubility.demo.bean.RequestResult;
import com.niubility.library.http.result.HttpResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

import java.util.Map;

public interface HttpServiceVip {


    @GET("/api/v1/member/info_by_card")
    Observable<HttpResult<RequestResult>> getVipInfoByCardId(@QueryMap Map<String, Object> requestMap);


}
