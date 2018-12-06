package com.niubility.library.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.niubility.library.mvp.BaseModel;
import com.niubility.library.mvp.BasePresenter;
import com.niubility.library.mvp.BaseRxView;
import com.niubility.library.utils.ToastUtils;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

/**
 * MVP模式，绑定生命周期，防止内存泄漏，基类
 */
public abstract class BaseRxActivity<V extends BaseRxView, P extends BasePresenter<V>> extends RxAppCompatActivity implements BaseRxView {

    protected P mPresenter;
    protected V mView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());
        ButterKnife.bind(this);

        if(mPresenter == null)
            mPresenter = createPresenter();
        if(mView == null)
            mView = createView();

        if(mPresenter != null && mView != null)
            mPresenter.attach(mView);

        init();
    }

    protected abstract P createPresenter();
    protected abstract V createView();
    protected abstract int getLayoutId();
    protected abstract void init();

    @Override
    public <T>LifecycleTransformer<T> bindLifecycle() {
        return this.bindToLifecycle();
    }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter != null) {
            mPresenter.detach();
        }
    }
}
