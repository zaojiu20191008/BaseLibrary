package com.niubility.library.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.niubility.library.R;

public class ChoosePayModeDialog extends Dialog implements View.OnClickListener {


    private TextView tv_title;
    private ConstraintLayout cl_pay_face;
    private ConstraintLayout cl_pay_code;
    private ImageView iv_close;

    private String title;
    private OnChooseDialogClickListener onChooseDialogClickListener;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOnChooseDialogClickListener(OnChooseDialogClickListener onChooseDialogClickListener) {
        this.onChooseDialogClickListener = onChooseDialogClickListener;
    }



    public ChoosePayModeDialog(@NonNull Context context) {
        super(context, R.style.CustomDialogStyle);
    }

    public ChoosePayModeDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public ChoosePayModeDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中

        //隐藏通知栏和导航栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            window.setAttributes(attributes);
        }

        setContentView(R.layout.dialog_choose_pay_mode);

        tv_title = findViewById(R.id.tv_title);
        cl_pay_face = findViewById(R.id.cl_pay_face);
        cl_pay_face.setOnClickListener(this);
        cl_pay_code = findViewById(R.id.cl_pay_code);
        cl_pay_code.setOnClickListener(this);
        iv_close = findViewById(R.id.iv_close);
        iv_close.setOnClickListener(this);

        if (!TextUtils.isEmpty(title)) {
            tv_title.setText(title);
        }

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.iv_close) {

            cancel();

        } else if (id == R.id.cl_pay_face) {

            if (onChooseDialogClickListener != null) {
                onChooseDialogClickListener.onFacePayClick(this);
            }

        } else if (id == R.id.cl_pay_code) {

            if (onChooseDialogClickListener != null) {
                onChooseDialogClickListener.onCodePayClick(this);
            }

        }

    }

    public interface OnChooseDialogClickListener {
        void onFacePayClick(ChoosePayModeDialog currentDialog);
        void onCodePayClick(ChoosePayModeDialog currentDialog);
    }

}
