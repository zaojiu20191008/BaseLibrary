package com.niubility.library.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ToastUtils {

    private static Toast mToast;

    public static float text_size_times = 1f;

    public static int gravity = Gravity.BOTTOM;
    public static int xOffset = 0;
    public static int yOffset = 0;

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
        mToast.setGravity(gravity, xOffset, yOffset);
        mToast.show();
    }
    public static void showToast(Context context,int resId){
        showToast(context,context.getString(resId));
    }

    public static void showToast(Context context,int resId,int errorCode){
        showToast(context,context.getString(resId)+":"+String.valueOf(errorCode));
    }


    public static void setToastSizeAndPosition(float text_size_times, int gravity, int xOffset, int yOffset) {
        ToastUtils.text_size_times = text_size_times;
        ToastUtils.gravity = gravity;
        ToastUtils.xOffset = xOffset;
        ToastUtils.yOffset = yOffset;
    }


}