package com.niubility.demo.mvp.presenter;

import com.niubility.demo.mvp.base.LocalBasePresenter;
import com.niubility.demo.mvp.contract.BusinessContract;

import java.util.Map;

public class BusinessPresenter extends LocalBasePresenter<BusinessContract.IBusinessView>
        implements BusinessContract.IBusinessPresenter {

    private String TAG = getClass().getSimpleName();

    @Override
    public void request(Map<String, Object> paramMap) {

        //HashMap<String, Object> requestParamMap = new HashMap<>(paramMap);

        /*subscribeAsyncToResult(
                BaseHttpAPI.getInstance().httpService().init(paramMap),
                new BaseResultObserver<InitResult>() {
                    @Override
                    protected void onSuccess(InitResult result) {
                        mView.initSuccess(result);
                    }


                    @Override
                    protected void onFailure(Map<String, Object> map) {
                        mView.initFail(map);
                    }
                });*/
    }


}
