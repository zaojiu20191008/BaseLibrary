package com.niubility.demo.mvp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.niubility.demo.R;
import com.niubility.demo.bean.InitResult;
import com.niubility.demo.bean.RequestResult;
import com.niubility.demo.common.LocalConstants;
import com.niubility.demo.mvp.base.LocalBaseMvpActivity;
import com.niubility.demo.mvp.contract.BusinessContract;
import com.niubility.demo.mvp.presenter.BusinessPresenter;
import com.niubility.library.common.config.ConfigPreferenceActivity;
import com.niubility.library.utils.DeviceUtils;

import java.util.HashMap;
import java.util.Map;

public class BusinessActivity extends LocalBaseMvpActivity<BusinessContract.IBusinessView, BusinessPresenter>
        implements BusinessContract.IBusinessView, View.OnClickListener {

    /**
     * 进入配置页面
     */
    private Button bt_go_to_config;
    /**
     * 进行网络请求
     */
    private Button bt_send_request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected BusinessPresenter createPresenter() {
        return new BusinessPresenter();
    }

    @Override
    protected BusinessContract.IBusinessView createView() {
        return this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_business;
    }

    @Override
    protected void init() {
        initView();
    }


    private void initView() {
        bt_go_to_config = findViewById(R.id.bt_go_to_config);
        bt_go_to_config.setOnClickListener(this);
        bt_send_request = findViewById(R.id.bt_send_request);
        bt_send_request.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (fastClick()) {
            return;
        }

        switch (v.getId()) {
            default:
                break;
            case R.id.bt_go_to_config:
                Intent it = new Intent(this, ConfigPreferenceActivity.class);
                startActivity(it);
                break;
            case R.id.bt_send_request:
                Map<String, Object> initRequestParams = new HashMap<>();
                initRequestParams.put(LocalConstants.device_id, DeviceUtils.getSerialNumber());
                mPresenter.init(initRequestParams);
                break;
        }
    }

    @Override
    public void requestSuccess(RequestResult requestResult) {

    }

    @Override
    public void requestFail(Map<String, Object> resultMap) {

    }

    @Override
    public void initSuccess(InitResult initResult) {

    }

    @Override
    public void initFail(Map<String, Object> resultMap) {

    }


}
