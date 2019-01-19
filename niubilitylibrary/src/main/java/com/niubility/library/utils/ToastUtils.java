package com.niubility.library.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ToastUtils {

    private static Toast mToast;

    public static float text_size_times = 1f;

    public static void showToast(Context context, String res){
        if (TextUtils.isEmpty(res)){
            return;
        }
        if (mToast != null){
            mToast.cancel();
        }
        mToast = Toast.makeText(context,res, Toast.LENGTH_LONG);
        LinearLayout view = (LinearLayout) mToast.getView();
        TextView v = (TextView) view.getChildAt(0);
        v.setTextSize(TypedValue.COMPLEX_UNIT_PX, v.getTextSize() * text_size_times);
        mToast.show();
    }
    public static void showToast(Context context,int resId){
        showToast(context,context.getString(resId));
    }

    public static void showToast(Context context,int resId,int errorCode){
        showToast(context,context.getString(resId)+":"+String.valueOf(errorCode));
    }

}