package com.niubility.library.base;

import android.support.v7.app.AppCompatActivity;

import com.niubility.library.mvp.BaseView;
import com.niubility.library.utils.ToastUtils;

public class BaseActivity extends AppCompatActivity implements BaseView {


    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(this, msg);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
