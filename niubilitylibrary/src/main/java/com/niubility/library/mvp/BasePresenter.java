package com.niubility.library.mvp;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

import com.niubility.library.http.base.IHttpResult;
import com.niubility.library.http.rx.Retry;
import com.niubility.library.http.rx.Threadscheduler;
import com.niubility.library.http.rx.TransformToResult;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public abstract class BasePresenter<V extends BaseView> implements LifecycleObserver {

    public String TAG = getClass().getSimpleName();

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
    public <T, R extends IHttpResult<T>> void subscribeAsyncToResult(final Observable<R> observable, DisposableObserver<T> observer) {
        observable.retryWhen(new Retry<T>(1, 1000))
                .compose(new Threadscheduler<R>())
                .map(new TransformToResult<T>())
                .subscribe(observer);

        mCompositeDisposable.add(observer);
    }


    /**
     * 订阅
     */
    public <T> void subscribeAsync(Observable<T> observable, DisposableObserver<T> observer) {
        observable.retryWhen(new Retry<T>(1, 1000))
                .compose(new Threadscheduler<T>())
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
