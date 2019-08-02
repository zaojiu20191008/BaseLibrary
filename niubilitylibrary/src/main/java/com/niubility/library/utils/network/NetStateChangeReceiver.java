package com.niubility.library.utils.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import java.util.ArrayList;
import java.util.List;

public class NetStateChangeReceiver extends BroadcastReceiver {


    private NetworkType mType;

    public void init(Context appContext){
        this.mType = NetworkUtil.getNetworkType(appContext);
    }


    private static class InstanceHolder{
        private static final NetStateChangeReceiver INSTANCE = new NetStateChangeReceiver();
    }

    private List<NetStateChangeObserver> mObservers = new ArrayList<>();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            NetworkType networkType = NetworkUtil.getNetworkType(context);
            notifyObservers(networkType);
        }
    }

    public static void registerReceiver(Context context){

        InstanceHolder.INSTANCE.init(context.getApplicationContext());

        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver( InstanceHolder.INSTANCE,intentFilter);
    }

    public static void unRegisterReceiver(Context context){
        context.unregisterReceiver( InstanceHolder.INSTANCE);
    }

    public static void registerObserver(NetStateChangeObserver observer){
        if (observer == null) {
            return;
        }
        if (!InstanceHolder.INSTANCE.mObservers.contains(observer)){
            InstanceHolder.INSTANCE.mObservers.add(observer);
        }
    }

    public static void unRegisterObserver(NetStateChangeObserver observer){
        if (observer == null) {
            return;
        }
        if (InstanceHolder.INSTANCE.mObservers == null) {
            return;
        }
        InstanceHolder.INSTANCE.mObservers.remove(observer);
    }

    private void notifyObservers(NetworkType networkType){
        if (mType == networkType) {
            return;
        }
        mType = networkType;
        if (networkType == NetworkType.NETWORK_NO){
            for (NetStateChangeObserver observer : mObservers){
                observer.onNetDisconnected();
            }
        }else {
            for (NetStateChangeObserver observer : mObservers){
                observer.onNetConnected(networkType);
            }
        }
    }
}
