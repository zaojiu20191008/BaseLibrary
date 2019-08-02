package com.niubility.library.utils.network;

public interface NetStateChangeObserver {

    void onNetDisconnected();
    void onNetConnected(NetworkType networkType);

}
