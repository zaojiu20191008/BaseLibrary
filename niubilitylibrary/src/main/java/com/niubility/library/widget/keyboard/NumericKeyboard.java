package com.niubility.library.widget.keyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import com.niubility.library.R;

public class NumericKeyboard extends KeyboardView {

    private NumericKeyboard myselfView = this;

    private Keyboard keyboardXml;
    private EditText editText;

    public NumericKeyboard(Context context, AttributeSet attrs) {
        super(context, attrs);

        keyboardXml = new Keyboard(context, R.xml.layout_keyboard_numeric);
        this.setKeyboard(keyboardXml);
        this.setEnabled(true);
        this.setPreviewEnabled(false);
        this.setOnKeyboardActionListener(keyboardActionListener);

    }

    public NumericKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setEditText(EditText editText) {
        this.editText = editText;

        //不会显示光标
        editText.setInputType(InputType.TYPE_NULL);
        //作用和 setInputType(InputType.TYPE_NULL) 一样
//        editText.setKeyListener(null);

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //以下屏蔽系统键盘的方法，EditText会显示出光标，且有版本限制
            editText.setShowSoftInputOnFocus(false);
        }else {
            editText.setInputType(InputType.TYPE_NULL);
        }*/

    }

    private OnKeyboardActionListener keyboardActionListener = new OnKeyboardActionListener() {

        /**
         * 当用户按下一个键时调用。 这是在调用onKey之前。
         * 对于重复的键，此键仅调用一次。
         * @param primaryCode 被按下的键的unicode。如果触摸不在有效范围内，值将为零。
         */
        @Override
        public void onPress(int primaryCode) {

        }

        /**
         * 当用户释放键时调用。 这是在调用onKey之后。
         * 对于重复的键，此键仅调用一次。
         * @param primaryCode 被释放的键的unicode
         */
        @Override
        public void onRelease(int primaryCode) {

        }

        /**
         * 发送一个按键到监听器
         * @ param primaryCode 这是按下的键
         * @ param keyCodes 所有可能的替代键的代码，
         */
        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            if (editText == null) {
                return;
            }

            Editable editable = editText.getText();
            int start = editText.getSelectionStart();
            switch (primaryCode) {
                case Keyboard.KEYCODE_DELETE:
                    if (editable != null && editable.length() > 0) {
                        if (start > 0) {
                            editable.delete(start - 1, start);
                        }
                    }
                    break;
                case Keyboard.KEYCODE_CANCEL:
                    myselfView.setVisibility(View.GONE);
                    break;
                default:
                    editable.insert(start, Character.toString((char) primaryCode));
                    break;
            }

        }

        /**
         * 向侦听器发送一系列字符。
         * @param text 要显示的字符序列。
         */
        @Override
        public void onText(CharSequence text) {

        }

        /**
         * 当用户从右向左快速移动手指时调用。
         */
        @Override
        public void swipeLeft() {

        }

        /**
         * 当用户从左向右快速移动手指时调用。
         */
        @Override
        public void swipeRight() {

        }

        /**
         * 当用户从上到下快速移动手指时调用。
         */
        @Override
        public void swipeDown() {

        }

        /**
         * 当用户快速将手指从下向上移动时调用。
         */
        @Override
        public void swipeUp() {

        }
    };

}
