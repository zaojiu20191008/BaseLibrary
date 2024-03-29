package com.niubility.library.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import androidx.annotation.NonNull;

import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.niubility.library.R;

public class LikeToastDialog extends Dialog {

    private Button bt_close;
    private TextView tv_error_code;
    private TextView tv_error_message;

    private CountDownTimer countDownTimer;


    private boolean cdEnable = true;
    private long error_code;
    private String error_message;

    private int xLocation = 0;
    private int yLocation = 0;

    public void setCdEnable(boolean cdEnable) {
        this.cdEnable = cdEnable;
    }

    public void setError_code(long error_code) {
        this.error_code = error_code;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public void setxLocation(int xLocation) {
        this.xLocation = xLocation;
    }

    public void setyLocation(int yLocation) {
        this.yLocation = yLocation;
    }

    public LikeToastDialog(@NonNull Context context) {
        super(context, R.style.CustomDialogStyle);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中

        if (xLocation != 0 || yLocation != 0) {
            WindowManager.LayoutParams params = window.getAttributes();
            //params.gravity = Gravity.TOP;
            params.x = xLocation;
            params.y = yLocation;
        }

        View decorView = window.getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.dialog_like_toast);

        bt_close = findViewById(R.id.bt_close);
        tv_error_code = findViewById(R.id.tv_error_code);
        tv_error_message = findViewById(R.id.tv_error_message);

        bt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (continuousClick(5, 5000)){
                    dismiss();
                }
            }
        });

        setCanceledOnTouchOutside(false);

        countDownTimer = new CountDownTimer(6 * 1000, 1000) {
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

//                String temp = "请点击屏幕或" + (millisUntilFinished / 1000) + "秒后重新操作";
//                SpannableString spannableString = new SpannableString(temp);  //获取按钮上的文字
//                ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
                /**
                 * public void setSpan(Object what, int start, int end, int flags) {
                 * 主要是start跟end，start是起始位置,无论中英文，都算一个。
                 * 从0开始计算起。end是结束位置，所以处理的文字，包含开始位置，但不包含结束位置。
                 */
//                spannableString.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//将倒计时的时间设置为红色


//                spannableString.setSpan(new UnderlineSpan(), 1, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//                spannableString.setSpan(new UnderlineSpan(), 6, 9, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//
//                tv_tip.setText(spannableString);

                tv_error_message.setText(error_message + "（" + millisUntilFinished / 1000 + "s）");


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

        tv_error_code.setText(String.valueOf(error_code));

        if (error_message != null) {
            tv_error_message.setText(error_message);
        } else {
            error_message = "";
        }

        if (cdEnable) {
            countDownTimer.start();
        }

    }


    /* 用于缓存记录 持续点击的每一次时间值 */
    protected long[] hitTimeArray = null;

    /**
     * 可选函数，在设定的有效时间内，连续点击达到设定的次数才返回 true
     * 常用于 onClick 点击事件中
     *
     * @param clickCount    连续点击的次数
     * @param effectiveTime 连续点击的有效时间
     * @return
     */
    public boolean continuousClick(int clickCount, long effectiveTime) {

        if (hitTimeArray == null) {
            hitTimeArray = new long[clickCount];
        }

        /**
         * 实现双击方法
         * src 拷贝的源数组
         * srcPos 从源数组的那个位置开始拷贝.
         * dst 目标数组
         * dstPos 从目标数组的那个位子开始写数据
         * length 拷贝的元素的个数
         */
        System.arraycopy(hitTimeArray, 1, hitTimeArray, 0, hitTimeArray.length - 1);
        //实现左移，然后最后一个位置更新距离开机的时间，如果最后一个时间和最开始时间小于DURATION，即连续5次点击
        hitTimeArray[hitTimeArray.length - 1] = SystemClock.uptimeMillis();
        if (hitTimeArray[0] >= (SystemClock.uptimeMillis() - effectiveTime)) {
            hitTimeArray = null;    //这里说明一下，我们在进来以后需要还原状态，否则如果点击过快，超出设定次数的后续点击，都会不断进来触发该效果。重新开始计数即可

//            String tips = "您已在[" + effectiveTime + "]ms内连续点击【" + hitTimeArray.length + "】次了！！！";
//            Toast.makeText(this, tips, Toast.LENGTH_SHORT).show();

            return true;
        }
        return false;
    }

}
