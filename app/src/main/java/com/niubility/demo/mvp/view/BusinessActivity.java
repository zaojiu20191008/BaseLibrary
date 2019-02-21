package com.niubility.demo.mvp.view;

import android.os.Bundle;
import com.niubility.demo.R;
import com.niubility.demo.bean.RequestResult;
import com.niubility.demo.mvp.base.LocalBaseMvpActivity;
import com.niubility.demo.mvp.contract.BusinessContract;
import com.niubility.demo.mvp.presenter.BusinessPresenter;

import java.util.Map;

public class BusinessActivity extends LocalBaseMvpActivity<BusinessContract.IBusinessView, BusinessPresenter>
        implements BusinessContract.IBusinessView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_business);
    }

    @Override
    protected BusinessPresenter createPresenter() {
        return null;
    }

    @Override
    protected BusinessContract.IBusinessView createView() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_business;
    }

    @Override
    protected void init() {

    }

    @Override
    public void requestSuccess(RequestResult requestResult) {

    }

    @Override
    public void requestFail(Map<String, Object> resultMap) {

    }
}
