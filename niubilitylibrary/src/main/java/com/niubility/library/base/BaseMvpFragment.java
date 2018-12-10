package com.niubility.library.base;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niubility.library.mvp.BasePresenter;
import com.niubility.library.mvp.BaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * MVP模式，绑定生命周期，防止内存泄漏，基类
 */
public abstract class BaseMvpFragment<V extends BaseView, P extends BasePresenter<V>> extends BaseFragment {

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

        if(mPresenter != null && mView != null) {
            mPresenter.attach(mView);
            //添加生命周期观察者
            getLifecycle().addObserver(mPresenter);
        }

        init();
    }

    protected abstract P createPresenter();
    protected abstract V createView();
    protected abstract int getLayoutId();
    protected abstract void init();


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
