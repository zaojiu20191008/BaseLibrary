package com.niubility.demo.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;
import com.niubility.library.base.BaseMvpActivity;
import com.niubility.library.utils.ScreenUtils;

//public abstract class LocalBaseMvpActivity<LV extends LocalBaseContract.ILocalBaseView, LP extends LocalBasePresenter> extends BaseMvpActivity {
public abstract class LocalBaseMvpActivity<V extends LocalBaseContract.ILocalBaseView, P extends LocalBasePresenter<V>> extends BaseMvpActivity<V,P> {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        ScreenUtils.hideNavigationBar(this);

        super.onCreate(savedInstanceState);
    }



    public void toast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show();
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
