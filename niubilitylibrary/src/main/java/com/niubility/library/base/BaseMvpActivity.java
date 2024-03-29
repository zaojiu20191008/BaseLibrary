package com.niubility.library.base;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.niubility.library.mvp.BasePresenter;
import com.niubility.library.mvp.BaseView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * MVP模式，绑定生命周期，防止内存泄漏，基类
 */
public abstract class BaseMvpActivity<V extends BaseView, P extends BasePresenter<V>> extends BaseActivity {

    private Unbinder unbinder;

    protected P mPresenter;
    protected V mView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);

        if(mPresenter == null)
            mPresenter = createPresenter();
        if(mView == null)
            mView = createView();

        if(mPresenter != null && mView != null) {
            mPresenter.attach(mView);
            //添加生命周期观察者
            getLifecycle().addObserver(mPresenter);
        }
        init();
        EventBus.getDefault().register(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (unbinder != null) {
            unbinder.unbind();
        }

        EventBus.getDefault().unregister(this);
    }



    protected abstract P createPresenter();
    protected abstract V createView();
    protected abstract int getLayoutId();
    protected abstract void init();

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent event){

    }


}
