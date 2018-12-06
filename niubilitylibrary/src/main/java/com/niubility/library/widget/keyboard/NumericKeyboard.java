package com.niubility.library.widget.keyboard;

import android.content.Context;
import android.graphics.*;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import com.niubility.library.R;

import java.lang.reflect.Field;
import java.util.List;

public class NumericKeyboard extends KeyboardView {

    private static final int CODE_DOUBLE_ZERO = -20; //双零按钮
    private static final int CODE_SURE = -21; //确定按钮

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
                case CODE_DOUBLE_ZERO:
                    editable.append("00");
                    break;
                case CODE_SURE:
                    if (onClickSureKeyListener != null) {
                        onClickSureKeyListener.onClickSureKey(editText);
                    }
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


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        List<Keyboard.Key> keys = null;
        if (keyboardXml != null) {
            keys = keyboardXml.getKeys();
        }

        if (keys != null) {
            for (Keyboard.Key key : keys) {
                // 数字键盘的处理
                if (key.codes[0] == CODE_DOUBLE_ZERO) {
                    //drawKeyBackground(R.drawable.bg_keyboardview_yes, canvas, key);
                    //drawText(canvas, key);
                    drawText(canvas, key, "00");
                }
                if (key.codes[0] == CODE_SURE) {
                    drawSureText(canvas, key);
                }
            }
        }
    }

    private void drawText(Canvas canvas, Keyboard.Key key, String showStr) {
        Rect bounds = new Rect();
        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);

        Field field;
        int labelTextSize = 0;
        try {
            field = KeyboardView.class.getDeclaredField("mLabelTextSize");
            field.setAccessible(true);
            labelTextSize = (int) field.get(this);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        paint.setTextSize(labelTextSize);
        //paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setTypeface(Typeface.DEFAULT);

        paint.getTextBounds(showStr, 0, showStr.length(), bounds);
        canvas.drawText(showStr, key.x + (key.width / 2) + 16,
                (key.y + key.height / 2) + bounds.height() / 2 + 16, paint);

    }

    private void drawSureText(Canvas canvas, Keyboard.Key key) {
        Rect bounds = new Rect();
        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);

        String showStrFirst = "确";
        String showStrSecond = "定";
        Field field;
        int labelTextSize = 0;
        try {
            field = KeyboardView.class.getDeclaredField("mLabelTextSize");
            field.setAccessible(true);
            labelTextSize = (int) field.get(this);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        paint.setTextSize(labelTextSize);
        //paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setTypeface(Typeface.DEFAULT);

        paint.getTextBounds(showStrFirst, 0, showStrFirst.length(), bounds);
        canvas.drawText(showStrFirst, key.x + (key.width / 2) + 16,
                (key.y + key.height / 2) + bounds.height() / 2 + 16 - 29, paint);

        paint.getTextBounds(showStrSecond, 0, showStrSecond.length(), bounds);
        canvas.drawText(showStrSecond, key.x + (key.width / 2) + 16,
                (key.y + key.height / 2) + bounds.height() / 2 + 16 + 29, paint);

    }


    private void drawText(Canvas canvas, Keyboard.Key key) {
        Rect bounds = new Rect();
        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);


        paint.setAntiAlias(true);

        paint.setColor(Color.WHITE);
        if (key.label != null) {
            String label = key.label.toString();

            Field field;

            if (label.length() > 1 && key.codes.length < 2) {
                int labelTextSize = 0;
                try {
                    field = KeyboardView.class.getDeclaredField("mLabelTextSize");
                    field.setAccessible(true);
                    labelTextSize = (int) field.get(this);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                paint.setTextSize(labelTextSize);
                //paint.setTypeface(Typeface.DEFAULT_BOLD);
                paint.setTypeface(Typeface.DEFAULT);
            } else {
                int keyTextSize = 0;
                try {
                    field = KeyboardView.class.getDeclaredField("mLabelTextSize");
                    field.setAccessible(true);
                    keyTextSize = (int) field.get(this);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                paint.setTextSize(keyTextSize);
                paint.setTypeface(Typeface.DEFAULT);
            }

            paint.getTextBounds(key.label.toString(), 0, key.label.toString()
                    .length(), bounds);
            canvas.drawText(key.label.toString(), key.x + (key.width / 2),
                    (key.y + key.height / 2) + bounds.height() / 2, paint);
        } else if (key.icon != null) {
            key.icon.setBounds(key.x + (key.width - key.icon.getIntrinsicWidth()) / 2, key.y + (key.height - key.icon.getIntrinsicHeight()) / 2,
                    key.x + (key.width - key.icon.getIntrinsicWidth()) / 2 + key.icon.getIntrinsicWidth(), key.y + (key.height - key.icon.getIntrinsicHeight()) / 2 + key.icon.getIntrinsicHeight());
            key.icon.draw(canvas);
        }

    }


    private OnClickSureKeyListener onClickSureKeyListener;

    public void setOnClickSureKeyListener(OnClickSureKeyListener onClickSureKeyListener) {
        this.onClickSureKeyListener = onClickSureKeyListener;
    }

    public interface OnClickSureKeyListener {
        void onClickSureKey (EditText editText);
    }



}
