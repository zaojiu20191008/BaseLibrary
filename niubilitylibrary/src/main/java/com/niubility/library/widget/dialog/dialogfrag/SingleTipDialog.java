package com.niubility.library.widget.dialog.dialogfrag;

import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.niubility.library.R;
import com.niubility.library.base.BaseDialog;

public class SingleTipDialog extends BaseDialog {

    private TextView tv_title;
    private TextView tv_content;


    private String title;
    private String content;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!TextUtils.isEmpty(title)) {
            tv_title.setText(title);
        }

        if (!TextUtils.isEmpty(content)) {
            tv_content.setText(content);
        }
    }

    @Override
    protected void initView(View rootView) {

        tv_title = rootView.findViewById(R.id.tv_title);
        tv_content = rootView.findViewById(R.id.tv_content);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_single_tip;
    }

    @Override
    protected int getLayoutWidth() {
        return getResources().getDimensionPixelSize(R.dimen.width_dialog_single_tip);
    }

    @Override
    protected int getLayoutHeight() {
        return getResources().getDimensionPixelSize(R.dimen.height_dialog_single_tip);
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
}
