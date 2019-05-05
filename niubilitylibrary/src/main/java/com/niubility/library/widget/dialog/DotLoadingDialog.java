package com.niubility.library.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.niubility.library.R;

import java.lang.ref.WeakReference;

public class DotLoadingDialog extends Dialog {

    private static final int MSG_UPDATE_LOADING_DOT = 1;
    private ImageView mLoadingDot1;
    private ImageView mLoadingDot2;
    private ImageView mLoadingDot3;
    private Handler mHandler;
    private int mDotSeq;

    private TextView loading_brand;
    private ImageView icon_loading;
    private String text_loading;
    private int id_drawable_loading;

    private boolean enableCD = false;
    private int cdDuration = 20;
    private CountDownTimer countDownTimer;



    public DotLoadingDialog(Context context) {
        super(context, R.style.Loading);
//        this.setContentView(R.layout.layout_dialog_loading);
//        this.setContentView(R.layout.layout_dialog_loading_custom);
        this.setContentView(R.layout.layout_dialog_loading_custom2);
        Window window = this.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = 17;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            params.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }
        window.setAttributes(params);
        this.mLoadingDot1 = (ImageView)this.findViewById(R.id.loading_dot1);
        this.mLoadingDot2 = (ImageView)this.findViewById(R.id.loading_dot2);
        this.mLoadingDot3 = (ImageView)this.findViewById(R.id.loading_dot3);
        this.mHandler = new MHandler(DotLoadingDialog.this);

        loading_brand = findViewById(R.id.loading_brand);
        icon_loading = findViewById(R.id.loading_icon);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    private static class MHandler extends Handler {
        private final WeakReference<DotLoadingDialog> mDialog;

        public MHandler(DotLoadingDialog mDialog) {
            this.mDialog = new WeakReference<>(mDialog);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            
            DotLoadingDialog dialog = mDialog.get();

            switch(msg.what) {
                case 1:
                    if (dialog.mDotSeq == 0) {
                        dialog.mLoadingDot1.setAlpha(0.6F);
                        dialog.mLoadingDot2.setAlpha(1.0F);
                        dialog.mLoadingDot3.setAlpha(0.6F);
                        dialog.mDotSeq++;
                    } else if (dialog.mDotSeq == 1) {
                        dialog.mLoadingDot1.setAlpha(0.3F);
                        dialog.mLoadingDot2.setAlpha(0.6F);
                        dialog.mLoadingDot3.setAlpha(1.0F);
                        dialog.mDotSeq++;
                    } else if (dialog.mDotSeq == 2) {
                        dialog.mLoadingDot1.setAlpha(1.0F);
                        dialog.mLoadingDot2.setAlpha(0.6F);
                        dialog.mLoadingDot3.setAlpha(0.3F);
                        dialog.mDotSeq = 0;
                    }

                    dialog.mHandler.sendEmptyMessageDelayed(1, 800L);
                default:
            }
        }
    }

    public String getText_loading() {
        return text_loading;
    }

    public void setText_loading(String text_loading) {
        this.text_loading = text_loading;
    }

    public void show() {
        if (!TextUtils.isEmpty(text_loading)){
            loading_brand.setText(text_loading);
        }
        if (id_drawable_loading != 0) {
            icon_loading.setBackgroundResource(id_drawable_loading);
        }

        this.mHandler.sendEmptyMessageDelayed(1, 800L);
        this.mDotSeq = 0;

        super.show();



        if (enableCD) {
            countDownTimer = new CountDownTimer(1000 * cdDuration, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {

                    if (cdListener != null) {
                        cdListener.onCDFinish(DotLoadingDialog.this);
                    }

                }
            };

            countDownTimer.start();
        }
    }

    public void hide() {
        this.mHandler.removeMessages(1);
        this.mDotSeq = 0;
        this.mLoadingDot1.setAlpha(1.0F);
        this.mLoadingDot2.setAlpha(0.6F);
        this.mLoadingDot3.setAlpha(0.3F);


        if (this.countDownTimer != null ) {
            this.countDownTimer.cancel();
        }

        super.dismiss();
    }

    public void setId_drawable_loading(int id_drawable_loading) {
        this.id_drawable_loading = id_drawable_loading;
    }




    public void setEnableCD(boolean enableCD) {
        this.enableCD = enableCD;
    }

    public void setCdDuration(int cdDuration) {
        this.cdDuration = cdDuration;
    }

    private CDListener cdListener;

    public void setCdListener(CDListener cdListener) {
        this.cdListener = cdListener;
    }

    /**
     * 倒计时结束监听器
     */
    public interface CDListener {

        /**
         * 倒计时结束时回调此函数
         *
         * @param currentDialog
         */
        void onCDFinish(DotLoadingDialog currentDialog);
    }
}
