package com.niubility.library.mvp;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

import com.niubility.library.http.base.HttpResult;
import com.niubility.library.http.rx.Threadscheduler;
import com.niubility.library.http.rx.TransformToResult;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public abstract class BasePresenter<V extends BaseView> implements LifecycleObserver {

    private static final String TAG = "BasePresenter";

    protected V mView;

    public void attach(V v) {
        this.mView = v;
    }

    public void detach() {
        this.mView = null;
    }

    /**
     * 收集订阅的observer，页面销毁时统一进行停止订阅操作
     */
    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();


    /**
     * 订阅，并将HttpResult中关键数据剥离出来
     */
    public <T> void subscribeAsyncToResult(Observable<HttpResult<T>> observable, DisposableObserver<T> observer) {
        observable.compose(new Threadscheduler<HttpResult<T>>())
                .map(new TransformToResult<T>())
                .subscribe(observer);

        mCompositeDisposable.add(observer);
    }

    /**
     * 订阅
     */
    public <T> void subscribeAsync(Observable<T> observable, DisposableObserver<T> observer) {
        observable.compose(new Threadscheduler<T>())
                .subscribe(observer);

        mCompositeDisposable.add(observer);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
        detach();
    }



}
