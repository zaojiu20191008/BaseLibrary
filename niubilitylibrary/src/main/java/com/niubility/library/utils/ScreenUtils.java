package com.niubility.library.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;

public class ScreenUtils {

    public static DisplayMetrics getScreenSize(Activity context) {
        Display defaultDisplay = context.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        defaultDisplay.getMetrics(dm);

        return dm;
    }

//    public static DisplayMetrics getScreenRealSize(Activity context) {
//        Display defaultDisplay = context.getWindowManager().getDefaultDisplay();
//        DisplayMetrics dm = new DisplayMetrics();
//        defaultDisplay.getRealMetrics(dm);
//
//        return dm;
//    }

}
