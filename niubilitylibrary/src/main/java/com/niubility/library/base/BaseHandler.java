package com.niubility.library.base;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

public class BaseHandler<T> extends Handler {

    //持有弱引用HandlerActivity,GC回收时会被回收掉.
    protected final WeakReference<T> mActivty;

    public BaseHandler(T activity) {
        this.mActivty = new WeakReference<T>(activity);
    }

    public T getRefActivity() {
        return  mActivty.get();
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        T refActivity = getRefActivity();
        if(refActivity != null) {
            onHandleMessage(msg, refActivity);
        }
    }

    public void onHandleMessage(Message msg, T activity){};
}
