package com.niubility.library.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.niubility.library.utils.ScreenUtils;

import java.util.LinkedList;

public abstract class BaseDialog extends DialogFragment {

    private int x;
    private int y;
    private int offsetX;
    private int offsetY;
    private int mGravity;
    private int mAnimationResId;
    private boolean mIsDropdown;
    private boolean mIsCenter;
    private boolean mIsClosing = false;
    private boolean mIsShowDialog = false;
    private boolean mIsBottom;
    private int mDialogHeight;
    private boolean mCanCanceledOnTouchOutside;
    private DialogInterface.OnDismissListener mOnDismissListener;
    private LinkedList<Runnable> mTaskLinkedLists = new LinkedList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAnimationResId = getAnimation();
        mCanCanceledOnTouchOutside = canCanceledOnTouchOutside();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View rootView = inflater.inflate(getLayoutId(), container, true);
        if (rootView == null) {
            return null;
        }
//        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);
//        rootView.setLayoutParams(layoutParams);
        initView(rootView);
        final Window window = getDialog().getWindow();
        if (window != null) {
            if(isHideNavigationBar()) {
                window.addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
            }
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams params = window.getAttributes();

            params.systemUiVisibility = getSystemUiVisibility();

            if (getLayoutWidth() == 0 || getLayoutHeight() == 0) {
                params.width = isWindowWidthMatchParent() ?
                        WindowManager.LayoutParams.MATCH_PARENT :
                        WindowManager.LayoutParams.WRAP_CONTENT;
                params.height = shouldMatchHeight() ?
                        mDialogHeight : WindowManager.LayoutParams.WRAP_CONTENT;
            } else {
                window.setLayout(getLayoutWidth(), getLayoutHeight());
            }
            if (!mIsCenter) {
                params.gravity = Gravity.TOP | Gravity.LEFT;
                computePosition(rootView);
                params.x = x;
                params.y = y;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    params.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                } else {
                    params.y -= ScreenUtils.getStatusHeight(getContext());
                }
            } else {
                params.gravity = Gravity.CENTER;
            }
            if (mIsBottom) {
                params.gravity = Gravity.BOTTOM;
            }
            if (shouldHideBackground()) {
                params.dimAmount = 0.0f;
            }
            window.setAttributes(params);
            window.setWindowAnimations(mAnimationResId);
        }
        return rootView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCanceledOnTouchOutside(mCanCanceledOnTouchOutside);
        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mIsClosing) {
            dismiss();
        }
        //执行任务
        while (mTaskLinkedLists.size() > 0) {
            mTaskLinkedLists.remove(0).run();
        }
        if(isHideNavigationBar()) {
            final Window window = getDialog().getWindow();
            if(window != null) {
                int ui_options = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                window.getDecorView().setSystemUiVisibility(ui_options);

                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
            }
        }
    }

    /**
     * 添加任务，dialog显示时直接执行，否则回调onResume()时执行
     */
    public void addTask(Runnable runnable) {
        if(runnable != null) {
            if (isShowing()) {
                runnable.run();
            } else {
                mTaskLinkedLists.add(runnable);
            }
        }
    }

    /**
     * 计算布局左上角坐标
     *
     * @param view 根布局
     */
    private void computePosition(View view) {
        view.measure(0, 0);
        if (!mIsDropdown) {
            return;
        }
        switch (mGravity) {
            case Gravity.CENTER:
                x = offsetX - view.getMeasuredWidth() / 2;
                y = offsetY;
                break;
            case Gravity.END:
            case Gravity.RIGHT:
                x = offsetX - view.getMeasuredWidth();
                y = offsetY;
                break;
            case Gravity.START:
            case Gravity.LEFT:
            default:
                x = offsetX;
                y = offsetY;
                break;
        }
    }

    public void showCenter(FragmentActivity activity, String tag) {
        mIsCenter = true;
        showDialog(activity, tag);
    }

    public void showBottom(FragmentActivity activity, String tag) {
        mIsBottom = true;
        showDialog(activity, tag);
    }

    public void showAtLocation(FragmentActivity activity, String tag, int offsetX, int offsetY) {
        mIsDropdown = false;
        if (activity == null || isShowing()) {
            return;
        }
        x = offsetX;
        y = offsetY;
        showDialog(activity, tag);
    }

    public void showAsDropDown(FragmentActivity activity, View anchorView, String tag, int gravity, int offsetX, int offsetY) {
        mGravity = gravity;
        mIsDropdown = true;
        if (activity == null || isShowing()) {
            return;
        }
        computeOffset(anchorView, offsetX, offsetY);
        showDialog(activity, tag);
    }

    public void showAsDropDown(FragmentActivity activity, View anchorView, String tag) {
        showAsDropDown(activity, anchorView, tag, Gravity.LEFT, 0, 0);
    }

    public void showDialog(FragmentActivity fragmentActivity, String tag) {
        mIsClosing = false;
        if (mIsShowDialog) {
            return;
        }
        FragmentTransaction transaction = prepareFragmentTransaction(fragmentActivity, tag);
        show(transaction, tag);
        mIsShowDialog = true;
    }

    public void hideDialog() {
        mIsClosing = true;
        mIsShowDialog = false;
        if (isShowing()) {
            dismissAllowingStateLoss();
        }
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {
        try {
            return super.show(transaction, tag);
        } catch (IllegalStateException e) {
            return -1;
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        mIsShowDialog = false;
        if(mOnDismissListener != null) {
            mOnDismissListener.onDismiss(dialog);
        }
    }

    private FragmentTransaction prepareFragmentTransaction(FragmentActivity activity, String tag) {
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction mTransaction = manager.beginTransaction();
        Fragment mFragment = manager.findFragmentByTag(tag);
        if (mFragment != null) {
            //为了不重复显示dialog，在显示对话框之前移除正在显示的对话框
            mTransaction.remove(mFragment);
        }
        return mTransaction;
    }

    /**
     * 计算偏移量
     *
     * @param anchorView 弹出窗口锁定的视图
     */
    private void computeOffset(View anchorView, int offsetX, int offsetY) {
        Rect rect = new Rect();
        anchorView.getGlobalVisibleRect(rect);
        mDialogHeight = ScreenUtils.getScreenHeight(anchorView.getContext()) - rect.bottom;
        switch (mGravity) {
            case Gravity.CENTER:
                this.offsetX = rect.left + anchorView.getWidth() / 2 + offsetX;
                this.offsetY = rect.bottom + offsetY;
                break;
            case Gravity.END:
            case Gravity.RIGHT:
                this.offsetX = rect.right + offsetX;
                this.offsetY = rect.bottom + offsetY;
                break;
            case Gravity.START:
            case Gravity.LEFT:
            default:
                this.offsetX = rect.left + offsetX;
                this.offsetY = rect.bottom + offsetY;
                break;
        }
    }

    @Override
    public void onDestroyView() {
        Dialog dialog = getDialog();
        // handles https://code.google.com/p/android/issues/detail?id=17423
        if (dialog != null && getRetainInstance()) {
            dialog.setDismissMessage(null);
        }
        super.onDestroyView();
    }

    private int px2dip(int px) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    private int dip2px(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }

    protected boolean shouldMatchHeight() {
        return false;
    }

    /**
     * 是否显示
     *
     * @return false:isHidden  true:isShowing
     */
    public boolean isShowing() {
        return this.getDialog() != null && this.getDialog().isShowing();
    }

    /**
     * 对话框取消监听
     *
     */
    public void setOnDismissListener(DialogInterface.OnDismissListener listener) {
        this.mOnDismissListener = listener;
    }

    /**
     * 初始化布局
     *
     * @param rootView
     */
    protected abstract void initView(View rootView);

    /**
     * 获取根布局Id
     *
     * @return
     */
    abstract protected int getLayoutId();


    /**
     * 获取布局宽度
     *
     * @return
     */
    abstract protected int getLayoutWidth();


    /**
     * 获取布局高度
     *
     * @return
     */
    abstract protected int getLayoutHeight();


    /**
     * 获取窗口设定值
     *
     * @return
     */
    abstract protected int getSystemUiVisibility();

    /**
     * 获取动画资源
     *
     * @return
     */
    abstract protected int getAnimation();

    /**
     * 是否隐藏背景
     *
     * @return
     */
    abstract protected boolean shouldHideBackground();


    /**
     * 是否点击空白处取消dialog
     *
     * @return
     */
    abstract protected boolean canCanceledOnTouchOutside();

    /**
     * 是否宽度填充屏幕
     *
     * @return
     */
    abstract protected boolean isWindowWidthMatchParent();

    /**
     * 是否隐藏导航栏
     *
     * @return
     */
    abstract protected boolean isHideNavigationBar();

}
