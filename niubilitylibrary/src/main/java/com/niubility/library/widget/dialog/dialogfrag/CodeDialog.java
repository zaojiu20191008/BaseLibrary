package com.niubility.library.widget.dialog.dialogfrag;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.niubility.library.R;
import com.niubility.library.base.BaseDialog;
import com.uuzuche.lib_zxing.activity.CodeUtils;

public class CodeDialog extends BaseDialog {

    private ImageView iv_close;
    private TextView tv_title;
    private ImageView iv_icon_title;
    private TextView tv_subtitle;
    private ImageView iv_code;
    private ProgressBar pb_circle_loading;
    private TextView tv_content;

    private String title = "微信支付";
    private String url_code;
    private String subTitle;

    private CountDownTimer countDownTimer;
    private int cdDuration = 120;

    public void setUrl_code(String url_code) {
        this.url_code = url_code;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void setCdDuration(int cdDuration) {
        this.cdDuration = cdDuration;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (url_code != null && !TextUtils.isEmpty(url_code)) {
            if (url_code.startsWith("http")){
                Glide.with(getActivity()).load(url_code)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .into(new CustomTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                pb_circle_loading.setVisibility(View.GONE);
                                iv_code.setVisibility(View.VISIBLE);
                                iv_code.setImageDrawable(resource);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {

                            }
                        });
            } else {
                Glide.with(getActivity()).load(CodeUtils.createImage(url_code, 200, 200, null))
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .into(new CustomTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                pb_circle_loading.setVisibility(View.GONE);
                                iv_code.setVisibility(View.VISIBLE);
                                iv_code.setImageDrawable(resource);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {

                            }
                        });
            }

        }

        if (subTitle != null) {
            tv_subtitle.setText(subTitle);
        }

        countDownTimer = new CountDownTimer(cdDuration * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int time = (int) (millisUntilFinished / 1000);
                tv_content.setText(String.valueOf(time).concat("s"));
            }

            @Override
            public void onFinish() {
                hideDialog();
            }
        };
        countDownTimer.start();

    }

    @Override
    protected void initView(View rootView) {

        iv_close = rootView.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideDialog();
            }
        });

        tv_title = rootView.findViewById(R.id.tv_title);
        iv_icon_title = rootView.findViewById(R.id.iv_icon_title);
        tv_subtitle = rootView.findViewById(R.id.tv_subtitle);
        iv_code = rootView.findViewById(R.id.iv_code);
        pb_circle_loading = rootView.findViewById(R.id.pb_circle_loading);
        tv_content = rootView.findViewById(R.id.tv_content);

        pb_circle_loading.setVisibility(View.VISIBLE);
        iv_code.setVisibility(View.INVISIBLE);

        tv_title.setText(title);

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_code;
    }

    @Override
    protected int getLayoutWidth() {
        return getResources().getDimensionPixelSize(R.dimen.width_dialog_code);
    }

    @Override
    protected int getLayoutHeight() {
        return getResources().getDimensionPixelSize(R.dimen.height_dialog_code);
    }

    @Override
    protected int getSystemUiVisibility() {

        //隐藏通知栏和导航栏
        int systemUiVisibility = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }
        return systemUiVisibility;
    }

    @Override
    protected int getAnimation() {
        return 0;
    }

    @Override
    protected boolean shouldHideBackground() {
        return false;
    }

    @Override
    protected boolean canCanceledOnTouchOutside() {
        return false;
    }

    @Override
    protected boolean isWindowWidthMatchParent() {
        return false;
    }

    @Override
    protected boolean isHideNavigationBar() {
        return false;
    }

    public void setTitle(String title){
        this.title = title;
    }
}
