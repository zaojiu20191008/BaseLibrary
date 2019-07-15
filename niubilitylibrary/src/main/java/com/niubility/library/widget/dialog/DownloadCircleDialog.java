package com.niubility.library.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.niubility.library.R;
import com.niubility.library.widget.custom.DownloadCircleView;

public class DownloadCircleDialog extends Dialog {

    public DownloadCircleDialog(Context context) {
        super(context, R.style.Theme_Ios_Dialog);
    }

    DownloadCircleView circleView;
    TextView tvMsg;

    ImageView iv_icon;

    int iconResId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.dialog_download_circle);
        this.setCancelable(false);//设置点击弹出框外部，无法取消对话框
        circleView = findViewById(R.id.view_custom_download_circle);
        tvMsg = findViewById(R.id.tv_msg);

        iv_icon = findViewById(R.id.iv_icon);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (iconResId != 0) {
            iv_icon.setBackgroundResource(iconResId);
        }

    }

    public void setProgress(int progress) {
        circleView.setProgress(progress);
        circleView.postInvalidate();
    }
    public void setMsg(String msg){
        tvMsg.setText(msg);
    }

    public void setIconResId(int resId) {
        if (resId != 0) {
            iconResId = resId;
        }
    }

}
