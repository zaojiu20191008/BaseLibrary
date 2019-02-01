package com.niubility.library.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.niubility.library.R;
import com.niubility.library.utils.EventUtils;


public class RequireCodeDialog extends Dialog implements View.OnClickListener, DialogInterface.OnCancelListener,DialogInterface.OnDismissListener {

    private Context context;
    private OnScanCodeListener scanCodeListener;

    private ImageView imageView_close;
    private ImageView imageView;
    private TextView titleView;
    private TextView contentView;
    private TextView textView_time;
    private EditText editText_barcode;

    private boolean isScanCode = true;

    private String type = "NORMAL";
    private String titleText;
    private String contentText;
    private boolean isShowImage = false;

    private CountDownTimer countDownTimer;

    public RequireCodeDialog(@NonNull Context context) {
        super(context, R.style.CustomDialogStyle);
        this.context = context;
    }

    public RequireCodeDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    protected RequireCodeDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    public RequireCodeDialog(@NonNull Context context, @NonNull OnScanCodeListener scanCodeListener) {
        super(context, R.style.CustomDialogStyle);
        this.context = context;
        this.scanCodeListener = scanCodeListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
//        window.setWindowAnimations(R.style.bottom_menu_animation); // 添加动画效果
        setContentView(R.layout.layout_dialog_require_code);

        /*WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth()*3/5; // 设置dialog宽度为屏幕的3/5
        getWindow().setAttributes(lp);*/

        init();

    }

    /**
     * 初始化
     */
    private void init() {

        imageView_close = findViewById(R.id.imageView_close);
        imageView = (ImageView) findViewById(R.id.imageView_tip);
        titleView = (TextView) findViewById(R.id.textview_titile);
        contentView = (TextView) findViewById(R.id.textview_content);
        textView_time = (TextView) findViewById(R.id.textView_cdTime);
        editText_barcode = findViewById(R.id.editText_barcode);

        /*if (isShowImage) {
            imageView.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(titleText)) {
            titleView.setText(titleText);
        }
        if (!TextUtils.isEmpty(contentText)) {
            contentView.setText(contentText);
            contentView.setVisibility(View.VISIBLE);
        }*/

        imageView_close.setOnClickListener(this);
        if (!TextUtils.isEmpty(titleText)) {
            titleView.setText(titleText);
        }
        setOnCancelListener(this);
        setOnDismissListener(this);

        countDownTimer = new CountDownTimer(120*1000, 1000) {
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

                textView_time.setText("（" + (millisUntilFinished / 1000) + "s）");
            }

            @Override
            public void onFinish() {
                if (isShowing()) {
                    dismiss();
                }
            }
        };

        /*editText_barcode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Log.i("Jayce_G", "已获取到焦点");
                }else {
                    Log.i("Jayce_G", "未获取到焦点");
                }
            }
        });*/

        /*editText_barcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().contains("\n")) {
                    Log.i("Jayce_G", "扫描到条码 ===>> " + s.toString());
                } else {
                    Log.i("Jayce_G", "文本发生改变 ===>> " + s.toString());
                }
            }
        });*/

        editText_barcode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    //拿到条码值，进行后续处理

                    if (isScanCode && scanCodeListener != null) {
                        scanCodeListener.onScanCode(RequireCodeDialog.this, editText_barcode.getText().toString().trim());
                    }
                    editText_barcode.setText("");

                    /*Toast.makeText(context, "拿到的条码值为：" + editText_barcode.getText().toString().trim(), Toast.LENGTH_LONG).show();
                    editText_barcode.setText("");*/
                }

                return false;
            }
        });

        //editText_barcode.setTextIsSelectable(true);
        editText_barcode.setFocusable(true);
        editText_barcode.setFocusableInTouchMode(true);
        editText_barcode.requestFocus();

        /*InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText_barcode,InputMethodManager.SHOW_IMPLICIT);*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        countDownTimer.start();

    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.imageView_close) {
            dismiss();
        }

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

    public boolean isEnableScanCode() {
        return isScanCode;
    }

    public void setEnableScanCode(boolean scanCode) {
        isScanCode = scanCode;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if ("REQUEST_RELEASE_COUPONS".equals(type)){
            EventUtils.post(type);
        }
    }

    /*@Override
    public boolean dispatchKeyEvent(@NonNull KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

            Toast.makeText(context, "拿到的条码值为：" + editText_barcode.getText().toString().trim(), Toast.LENGTH_LONG).show();
            editText_barcode.setText("");
        }

        return super.dispatchKeyEvent(event);
    }*/

    /**
     * 扫描到条形码监听器
     */
    public interface OnScanCodeListener {

        /**
         * 扫描到完整条形码时会回调此函数
         *
         * @param codeValue
         */
        void onScanCode(RequireCodeDialog currentDialog, String codeValue);
    }
}