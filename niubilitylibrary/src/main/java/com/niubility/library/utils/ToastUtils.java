package com.niubility.library.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

public class ToastUtils {

    private static Toast mToast;

    public static void showToast(Context context, String res){
        if (TextUtils.isEmpty(res)){
            return;
        }
        if (mToast != null){
            mToast.cancel();
        }
        mToast = Toast.makeText(context,res, Toast.LENGTH_LONG);
        mToast.show();
    }
    public static void showToast(Context context,int resId){
        showToast(context,context.getString(resId));
    }

    public static void showToast(Context context,int resId,int errorCode){
        showToast(context,context.getString(resId)+":"+String.valueOf(errorCode));
    }

}