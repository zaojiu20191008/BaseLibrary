package com.niubility.library.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * @Describe：RxPermission
 * @Date：2019-03-07
 */
public class RxPermissionUtils {
    /*
    *   1. checkPermission();
    *   2. checkPermissionIsOpen(); true:执行操作
    *   3. setCallbackListener(); 若点击开启，执行操作
    */
    private boolean isOpen = false;
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
        void onCallback();
    }

    public void setCallbackListener(CallbackListener listener) {
        this.listener = listener;
    }

    /**
     * Description：可一次性申请多个权限，权限申请和说明按对应顺序填写。
     */
    @SuppressLint("CheckResult")
    public void checkPermission(final Activity activity, final String[] permissions, final String[] describe) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final RxPermissions rxPermissions = new RxPermissions(activity);
            rxPermissions.requestEach(permissions).subscribe(new Consumer<Permission>() {
                @Override
                public void accept(Permission permission) throws Exception {
                    if (permission.granted) {
                        if (listener != null) {
                            listener.onCallback();
                        }
                        isOpen = true;
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        for (int i = 0; i < permissions.length; i++) {
                            if (permissions[i].equals(permission.name)) {
                                Toast.makeText(activity, "您拒绝了" + describe[i] + "权限", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                        isOpen = false;
                    } else {
                        for (int i = 0; i < permissions.length; i++) {
                            if (permissions[i].equals(permission.name)) {
                                Toast.makeText(activity, "您拒绝了" + describe[i] + "权限，需要您到设置手动开启", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                        isOpen = false;
                    }
                }
            });
        }
    }

    public boolean checkPermissionIsOpen(){
        return isOpen;
    }
}
