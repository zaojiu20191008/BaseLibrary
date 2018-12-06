package com.niubility.library.mvp;


import com.trello.rxlifecycle2.LifecycleTransformer;

public interface BaseRxView extends BaseView {

    <T>LifecycleTransformer<T> bindLifecycle();
}
