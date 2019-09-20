package com.niubility.library.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.niubility.library.R;


public class TipDialog extends Dialog implements View.OnClickListener, DialogInterface.OnCancelListener {

    private Context context;

    private ImageView imageView;
    private TextView titleView;
    private TextView contentView;
    private TextView button_sure;

    private String titleText;
    private String contentText;
    private boolean isShowImage = false;

    private CountDownTimer countDownTimer;

    public TipDialog(@NonNull Context context) {
        super(context, R.style.CustomDialogStyle);
        this.context = context;
    }

    public TipDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    protected TipDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
//        window.setWindowAnimations(R.style.bottom_menu_animation); // 添加动画效果
        setContentView(R.layout.layout_dialog_tip);

        /*WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth()*3/5; // 设置dialog宽度为屏幕的3/5
        getWindow().setAttributes(lp);*/

        imageView = (ImageView) findViewById(R.id.imageView_tip);
        titleView = (TextView) findViewById(R.id.textview_titile);
        contentView = (TextView) findViewById(R.id.textview_content);
        button_sure = (TextView) findViewById(R.id.button_sure);

        if (isShowImage) {
            imageView.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(titleText)) {
            titleView.setText(titleText);
        }
        if (!TextUtils.isEmpty(contentText)) {
            contentView.setText(contentText);
            contentView.setVisibility(View.VISIBLE);
        }

        button_sure.setOnClickListener(this);

        setOnCancelListener(this);

        countDownTimer = new CountDownTimer(30*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                /**
                 * 超链接 URLSpan
                 * 文字背景颜色 BackgroundColorSpan
                 * 文字颜色 ForegroundColorSpan
                 * 字体大小 AbsoluteSizeSpan
                 * 粗体、斜体 StyleSpan
                 * 删除线 StrikethroughSpan
                 * 下划线 UnderlineSpan
                 * 图片 ImageSpan
                 * http://blog.csdn.net/ah200614435/article/details/7914459
                 */
                //SpannableString spannableString = new SpannableString(button_sure.getText().toString());  //获取按钮上的文字
                //ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
                /**
                 * public void setSpan(Object what, int start, int end, int flags) {
                 * 主要是start跟end，start是起始位置,无论中英文，都算一个。
                 * 从0开始计算起。end是结束位置，所以处理的文字，包含开始位置，但不包含结束位置。
                 */
                //spannableString.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//将倒计时的时间设置为红色

                button_sure.setText("确 定（" + (millisUntilFinished / 1000) + "s）");
            }

            @Override
            public void onFinish() {
                dismiss();

            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();

        countDownTimer.start();
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        dismiss();
    }



    @Override
    public void dismiss() {
        super.dismiss();
        if (countDownTimer != null){
            countDownTimer.cancel();
        }
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public void setShowImage(boolean showImage) {
        isShowImage = showImage;
    }


}