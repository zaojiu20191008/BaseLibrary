package com.niubility.library.base;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niubility.library.mvp.BasePresenter;
import com.niubility.library.mvp.BaseRxView;
import com.niubility.library.utils.ToastUtils;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * MVP模式，绑定生命周期，防止内存泄漏，基类
 */
public abstract class BaseRxFragment<V extends BaseRxView, P extends BasePresenter<V>> extends RxFragment implements BaseRxView {

    protected P mPresenter;
    protected V mView;

    protected Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
    public void showToast(String msg) {
        ToastUtils.showToast(getActivity(), msg);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public <T> LifecycleTransformer<T> bindLifecycle() {
        return bindToLifecycle();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
