package com.niubility.demo.mvp.contract;


import com.niubility.demo.bean.InitResult;
import com.niubility.demo.bean.RequestResult;
import com.niubility.demo.mvp.base.LocalBaseContract;

import java.util.Map;

public class BusinessContract {

    public interface IBusinessView extends LocalBaseContract.ILocalBaseView {
        void requestSuccess(RequestResult requestResult);
        void requestFail(Map<String, Object> resultMap);

        void initSuccess(InitResult initResult);
        void initFail(Map<String, Object> resultMap);

    }

    public interface IBusinessPresenter extends LocalBaseContract.ILocalBasePresenter {

        void request(Map<String, Object> paramMap);

        void init(Map<String, Object> paramMap);

    }


}
