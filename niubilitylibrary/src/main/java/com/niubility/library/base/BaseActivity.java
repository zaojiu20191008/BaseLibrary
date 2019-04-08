package com.niubility.library.base;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.niubility.library.BuildConfig;
import com.niubility.library.mvp.BaseView;
import com.niubility.library.utils.ToastUtils;

public class BaseActivity extends AppCompatActivity implements BaseView {


    public String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLog("=============> onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        showLog("=============> onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        showLog("=============> onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showLog("=============> onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        showLog("=============> onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        showLog("=============> onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showLog("=============> onDestroy");
    }


    /**
     * 可选函数，打印日志
     *
     * @param logTitle
     * @param logContent
     */
    public void showLog (String logTitle, String logContent) {
        if (BuildConfig.DEBUG) {
            Log.i(logTitle, logContent);
        }
    }

    /**
     * 可选函数，打印日志，Tag 为当前类的简名
     *
     * @param logContent
     */
    public void showLog (String logContent) {
        showLog(TAG, logContent);
    }
    

    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(this, msg);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!isFocus()) {
            return super.dispatchTouchEvent(ev);
        }
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                v.clearFocus();

            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public boolean isFocus() {
       return true;
    }
    

    /* 最后一次点击的时间 */
    private long lastClick = 0;

    /**
     * 可选函数，防止短时间内快速点击按钮而引发了多次事件响应
     */
    public boolean fastClick() {
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return true;
        }
        lastClick = System.currentTimeMillis();
        return false;
    }


    /* 用于缓存记录 持续点击的每一次时间值 */
    protected long[] hitTimeArray = null;

    /**
     * 可选函数，在设定的有效时间内，连续点击达到设定的次数才返回 true
     * 常用于 onClick 点击事件中
     *
     * @param clickCount 连续点击的次数
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

    public int hitCounts = 10;//默认的持续点击次数
    public long hitEffectiveTime = 15 * 1000;//默认的持续点击有效时间

    /**
     * 使用默认生效次数和有效时间
     * 默认次数：10次
     * 默认有效时间：15秒
     *
     * @return
     */
    public boolean continuousClick () {
        return continuousClick(hitCounts, hitEffectiveTime);
    }





//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        StringBuffer sb = new StringBuffer();
//        sb.append("监听到按键 ==========================================\n");
//        sb.append("按键动作Action ===========>> ").append(String.valueOf(event.getAction()) + "\n");
//        sb.append("按键代码KeyCode ==========>> ").append(String.valueOf(event.getKeyCode()) + "\n");
//        sb.append("按键字符UnicodeChar ======>> ").append((char)event.getUnicodeChar() + "\n");
//        sb.append("按键UnicodeChar ==========>> ").append(String.valueOf(event.getUnicodeChar()) + "\n");
//        sb.append("按键重复次数RepeatCount ===>> ").append(String.valueOf(event.getRepeatCount()) + "\n");
//        sb.append("按键功能键状态MetaState ===>> ").append(String.valueOf(event.getMetaState()) + "\n");
//        sb.append("按键硬件编码ScanCode ======>> ").append(String.valueOf(event.getScanCode()) + "\n");
//        sb.append("按键标志Flags ============>> ").append(String.valueOf(event.getFlags()) + "\n");
//        sb.append("按键getNumber ============>> ").append(String.valueOf(event.getNumber()) + "\n");
//        sb.append("按键getCharacters ========>> ").append(String.valueOf(event.getCharacters()) + "\n");
////        event.getKeyCharacterMap();
//        sb.append("\n");
//
//        Log.i("KeyEvent", sb.toString());
//
//        return super.dispatchKeyEvent(event);
//    }


}
