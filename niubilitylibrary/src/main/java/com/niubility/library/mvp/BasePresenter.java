package com.niubility.library.mvp;


import com.niubility.library.http.HttpResult;
import com.niubility.library.http.rx.RxSchedulers;
import com.niubility.library.http.rx.Threadscheduler;
import com.niubility.library.http.rx.TransformToResult;

import io.reactivex.Observable;
import io.reactivex.Observer;

public abstract class BasePresenter<V extends BaseRxView> {

    protected V mView;

    public void attach(V v) {
        this.mView = v;
    }

    public void detach() {
        this.mView = null;
    }

    public <T> void subscribeBindLifecycle(Observable<HttpResult<T>> observable, Observer<T> observer) {
        observable.compose(RxSchedulers.<HttpResult<T>>observableIO2Main(mView))//绑定生命周期，防止内存泄露
                .map(new TransformToResult<T>())
                .subscribe(observer);

    }


    public <T> void subscribeAsyn(Observable<HttpResult<T>> observable, Observer<T> observer) {

        observable.compose(new Threadscheduler<HttpResult<T>>())
                .map(new TransformToResult<T>())
                .subscribe(observer);
    }


}
