package com.niubility.library.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Arrays;

import io.reactivex.functions.Consumer;

/**
 * @Describe：RxPermission
 * @Date：2019-03-07
 */
public class RxPermissionUtils {
    /*
     *   1. setCallbackListener(); 若点击开启，执行操作 或者 已经同意过的权限，执行操作
     *   2. checkPermission();
     *   TODO：(回调设置在checkPermission方法之前，当已经同意过的权限的检查速率大于设置监听的速率，
     *   TODO：导致已经同意过的权限执行时并未设置回调 listener == null)
     */
    private static volatile RxPermissionUtils instance;

    public static RxPermissionUtils getInstance() {
        if (instance == null) {
            synchronized (RxPermissionUtils.class) {
                if (instance == null) {
                    instance = new RxPermissionUtils();
                }
            }
        }
        return instance;
    }

    private CallbackListener listener;

    public interface CallbackListener {
        void onGrantedCallback();
        void onRefuseCallback();
        void onBanCallback();
    }

    public void setCallbackListener(CallbackListener listener) {
        this.listener = listener;
    }

    /**
     * Description：可一次性申请多个权限，权限申请和说明按对应顺序填写。
     */
    @SuppressLint("CheckResult")
    public void checkPermission(final boolean isCallback, final Activity activity, final String[] permissions, final String[] describe) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final RxPermissions rxPermissions = new RxPermissions(activity);
            rxPermissions.requestEachCombined(permissions).subscribe(new Consumer<Permission>() {
                @Override
                public void accept(Permission permission) throws Exception {
                    if (permission.granted) {
                        if (listener != null && isCallback) {
                            listener.onGrantedCallback();
                        }
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        Toast.makeText(activity, "您拒绝了 " + Arrays.toString(describe) + " 权限", Toast.LENGTH_SHORT).show();
                        if (listener != null && isCallback) {
                            listener.onRefuseCallback();
                        }
                    } else {
                        Toast.makeText(activity, "您已禁止开启 " + Arrays.toString(describe) + " 权限，需要您到设置手动开启", Toast.LENGTH_SHORT).show();
                        if (listener != null && isCallback) {
                            listener.onBanCallback();
                        }
                    }
                }
            });
        } else {
            if (listener != null && isCallback) {
                listener.onGrantedCallback();
            }
        }
    }
}
