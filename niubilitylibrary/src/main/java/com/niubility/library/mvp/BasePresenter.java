package com.niubility.library.mvp;


public abstract class BasePresenter<M, V> {

    protected M mModel;
    protected V mView;

    public void attach(M m, V v) {
        this.mModel = m;
        this.mView = v;
    }

    public void detach() {
        this.mModel = null;
        this.mView = null;
    }


}
