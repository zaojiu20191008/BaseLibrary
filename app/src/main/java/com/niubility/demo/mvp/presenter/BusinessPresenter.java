package com.niubility.demo.mvp.presenter;

import com.niubility.demo.bean.InitResult;
import com.niubility.demo.bean.RequestResult;
import com.niubility.demo.https.HttpsAPI;
import com.niubility.demo.mvp.base.LocalBasePresenter;
import com.niubility.demo.mvp.contract.BusinessContract;
import com.niubility.library.http.base.BaseHttpAPI;
import com.niubility.library.http.rx.BaseResultObserver;

import java.util.Map;

public class BusinessPresenter extends LocalBasePresenter<BusinessContract.IBusinessView>
        implements BusinessContract.IBusinessPresenter {

    private String TAG = getClass().getSimpleName();

    @Override
    public void request(Map<String, Object> paramMap) {

        //HashMap<String, Object> requestParamMap = new HashMap<>(paramMap);

        subscribeAsyncToResult(
                HttpsAPI.getInstance().httpServiceStore().request(paramMap),
                new BaseResultObserver<RequestResult>() {
                    @Override
                    protected void onSuccess(RequestResult result) {
                        mView.requestSuccess(result);
                    }


                    @Override
                    protected void onFailure(Map<String, Object> map) {
                        mView.requestFail(map);
                    }
                });
    }

    @Override
    public void init(Map<String, Object> paramMap) {

        //HashMap<String, Object> requestParamMap = new HashMap<>(paramMap);

        subscribeAsyncToResult(
                HttpsAPI.getInstance().httpServiceStore().init(paramMap),
                new BaseResultObserver<InitResult>() {
                    @Override
                    protected void onSuccess(InitResult result) {
                        mView.initSuccess(result);
                    }


                    @Override
                    protected void onFailure(Map<String, Object> map) {
                        mView.initFail(map);
                    }
                });

    }


}
