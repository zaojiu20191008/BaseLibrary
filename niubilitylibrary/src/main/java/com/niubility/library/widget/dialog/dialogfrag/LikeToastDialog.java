package com.niubility.library.widget.dialog.dialogfrag;

import android.os.Build;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.niubility.library.R;
import com.niubility.library.base.BaseDialog;

public class LikeToastDialog extends BaseDialog {

    private TextView tv_error_code;
    private TextView tv_error_message;

    private CountDownTimer countDownTimer;


    private long error_code;
    private String error_message;

    public void setError_code(long error_code) {
        this.error_code = error_code;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    @Override
    protected void initView(View rootView) {

        tv_error_code = rootView.findViewById(R.id.tv_error_code);
        tv_error_message = rootView.findViewById(R.id.tv_error_message);

        countDownTimer = new CountDownTimer(4 * 1000, 1000) {
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

                hideDialog();

            }
        };

    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_like_toast;
    }

    @Override
    protected int getLayoutWidth() {
        return getResources().getDimensionPixelSize(R.dimen.width_dialog_like_toast);
    }

    @Override
    protected int getLayoutHeight() {
        return getResources().getDimensionPixelSize(R.dimen.height_dialog_like_toast);
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
        return R.anim.scale_y_0_1;
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


    @Override
    public void onStart() {
        super.onStart();

        tv_error_code.setText(String.valueOf(error_code));

        if (error_message != null) {
            tv_error_message.setText(error_message);
        } else {
            error_message = "";
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        countDownTimer.start();

    }
}
