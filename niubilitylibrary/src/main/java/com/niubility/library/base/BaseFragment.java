package com.niubility.library.base;

import android.support.v4.app.Fragment;

import com.niubility.library.mvp.BaseView;
import com.niubility.library.utils.ToastUtils;

public class BaseFragment extends Fragment implements BaseView {

    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(getActivity(), msg);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

}
