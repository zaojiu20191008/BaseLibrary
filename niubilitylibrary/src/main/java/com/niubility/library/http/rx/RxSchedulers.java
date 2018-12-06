package com.niubility.library.http.rx;


import com.niubility.library.mvp.BaseRxView;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxSchedulers<T> {

    /**
     * 线程切换，
     * 绑定生命周期，防止内存泄漏
     */
    public static <T> ObservableTransformer<T, T> observableIO2Main(final BaseRxView baseRxView) {
        return new ObservableTransformer<T, T>() {
                    @Override
                    public ObservableSource<T> apply(Observable<T> upstream) {
                        return upstream.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .compose(baseRxView.<T>bindLifecycle());
                    }};
    }
}

