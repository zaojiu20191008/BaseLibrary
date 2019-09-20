package com.niubility.library.widget.dialog.dialogfrag;

import android.os.Build;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.niubility.library.R;
import com.niubility.library.base.BaseDialog;

public class InputDialog extends BaseDialog implements View.OnClickListener {
    /**
     * 提示标题
     */
    private TextView tv_title;
    /**
     * 提示内容
     */
    private TextView tv_content;

    private TextView tv_tip_et;

    private TextInputLayout til_input;
    /**
     * 输入内容
     */
    private TextInputEditText tiet_input;
    /**
     * 取消
     */
    private Button bt_negative;
    /**
     * 确定
     */
    private Button bt_positive;

    private OnClickButtonListener onClickButtonListener;

    private String text_title;
    private String text_content;
    private String text_tip_et;
    private String text_input;
    private String text_input_hint;


    /**
     * 文字显示方式，默认为0
     * 0为明文显示，1为密文显示
     */
    private int displayMode = 0;

    @Override
    protected void initView(View rootView) {

        tv_title = rootView.findViewById(R.id.tv_title);
        tv_content = rootView.findViewById(R.id.tv_content);
        tv_tip_et = rootView.findViewById(R.id.tv_tip_et);
        til_input = rootView.findViewById(R.id.til_input);
        tiet_input = rootView.findViewById(R.id.tiet_input);
        bt_negative = rootView.findViewById(R.id.bt_negative);
        bt_negative.setOnClickListener(this);
        bt_positive = rootView.findViewById(R.id.bt_positive);
        bt_positive.setOnClickListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_input;
    }

    @Override
    protected int getLayoutWidth() {
        return getResources().getDimensionPixelSize(R.dimen.width_dialog_input);
    }

    @Override
    protected int getLayoutHeight() {
        return getResources().getDimensionPixelSize(R.dimen.height_dialog_input);
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


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.bt_negative) {
            if (onClickButtonListener != null) {
                onClickButtonListener.onClickNegativeButton(this);
            }
        } else if (i == R.id.bt_positive) {
            if (onClickButtonListener != null) {
                onClickButtonListener.onClickPositiveButton(this);
            }
        } else {

        }

    }


    @Override
    public void onStart() {
        super.onStart();

        if (text_title != null) {
            tv_title.setText(text_title);
        }

        if (text_content != null) {
            tv_content.setText(text_content);
        }

        if (text_tip_et != null) {
            tv_tip_et.setText(text_tip_et);
        }

        if (text_input != null) {
            tiet_input.setText(text_input);
        }

        if (text_input_hint != null) {
            tiet_input.setHint(text_input_hint);
        }

        if (tiet_input != null) {
            switch (displayMode) {
                case 1://密文显示
                    tiet_input.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    break;
                default:
                    tiet_input.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    break;
            }
        }


    }


    public void setOnClickButtonListener(OnClickButtonListener onClickButtonListener) {
        this.onClickButtonListener = onClickButtonListener;
    }

    public void setText_title(String text_title) {
        this.text_title = text_title;
    }

    public void setText_content(String text_content) {
        this.text_content = text_content;
    }

    public void setText_tip_et(String text_tip_et) {
        this.text_tip_et = text_tip_et;
    }

    public void setText_input(String text_input) {
        this.text_input = text_input;
    }

    public void setText_input_hint(String text_input_hint) {
        this.text_input_hint = text_input_hint;
    }

    public String getInputText() {
        return tiet_input.getText().toString();
    }

    /**
     * 双按钮监听器
     */
    public interface OnClickButtonListener {
        /**
         * 取消按钮点击回调
         *
         * @param currentDialog
         */
        void onClickNegativeButton(InputDialog currentDialog);

        /**
         * 确定按钮点击回调
         *
         * @param currentDialog
         */
        void onClickPositiveButton(InputDialog currentDialog);
    }

    /**
     * 显示提示信息
     * @param hint
     */
    public void showTextInputLayoutHint(String hint){
        if (til_input != null) {
            til_input.setHint(hint);
            tiet_input.setFocusable(true);
            tiet_input.setFocusableInTouchMode(true);
            tiet_input.requestFocus();
        }

    }

    /**
     * 显示错误提示，并获取焦点
     * @param error
     */
    public void showError(String error){
        if (til_input != null) {
            til_input.setError(error);
            tiet_input.setFocusable(true);
            tiet_input.setFocusableInTouchMode(true);
            tiet_input.requestFocus();
        }

    }


    public void showEtTip(String etTip) {
        if (tv_tip_et != null) {
            text_tip_et = etTip;
            tv_tip_et.setText(etTip);
        }
    }


    public void setDisplayMode(int displayMode) {
        this.displayMode = displayMode;
    }


}
