package com.niubility.library.mvp;


public interface BaseView {
//    void showMessage(String msg);
    void showToast(String msg);

    void showLoading();

    void showLoading(String msg);

    void hideLoading();
}
