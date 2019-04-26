package com.niubility.library.http.rx;

import com.niubility.library.utils.LogUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import retrofit2.HttpException;

/**
 * 从服务器通用返回格式中 取出关键数据，否则抛出 ApiException异常
 */
public class Retry<T> implements Function<Observable<Throwable>, ObservableSource<?>> {

    public final String TAG = Retry.class.getSimpleName();

    private int mCurrentRetryCount;
    private int mMaxRetryCount;
    private long mRetryDelayMillis;

    public Retry(int maxRetryCount, long retryDelayMillis) {
        this.mMaxRetryCount = maxRetryCount;
        this.mRetryDelayMillis = retryDelayMillis;
    }

    @Override
    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Throwable throwable) throws Exception {
                if (throwable instanceof IOException || throwable instanceof HttpException) {
                    if (mCurrentRetryCount < mMaxRetryCount) {
                        mCurrentRetryCount++;
                        LogUtils.getInstance().showILog(TAG, "重试次数 -> " + mCurrentRetryCount);
                        return Observable.just(1).delay(mRetryDelayMillis, TimeUnit.MILLISECONDS);
                    }
                }
                return Observable.error(throwable);
            }
        });
    }
}
