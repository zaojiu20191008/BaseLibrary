package com.niubility.library.utils;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import com.niubility.library.R;

/**
 * @Describe：悬浮窗
 * @Date：2019-03-22
 */
public class FloatingWindowUtils {
    //记录Down事件的点击位置
    private static int startX, startY = 0;
    //记录屏幕的宽度 高度 状态栏高度
    private static int screenWidth, screenHeight, statusHeight, navigationHeight;
    //上下滑的限制高度
    private static int transHeight = 80;
    //是否开启点击效果
    private boolean isClickEffect = false;

    private Animation animation;

    private static volatile FloatingWindowUtils instance;

    public static FloatingWindowUtils getInstance() {
        if (instance == null) {
            synchronized (FloatingWindowUtils.class) {
                if (instance == null) {
                    instance = new FloatingWindowUtils();
                }
            }
        }
        return instance;
    }

    public void setIsClickEffect(boolean isClickEffect) {
        this.isClickEffect = isClickEffect;
    }

    public void setView(final Activity context, final View view, boolean isStatusBar, boolean isNavigationBar) {
        screenWidth = ScreenUtils.getScreenWidth(context);
        screenHeight = ScreenUtils.getScreenHeight(context);
        if (isStatusBar) {
            statusHeight = ScreenUtils.getStatusHeight(context);
        }
        if (isNavigationBar) {
            navigationHeight = -ScreenUtils.getNavigationBarHeight(context);
        }

        final int height = screenHeight - statusHeight - navigationHeight;
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();

                        if (isClickEffect) {
                            animation = AnimationUtils.loadAnimation(context, R.anim.down_animation);
                            v.startAnimation(animation);
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //获取移动后的坐标
                        int moveX = (int) event.getRawX();
                        int moveY = (int) event.getRawY();
                        //拿到手指移动距离的大小
                        int move_bigX = moveX - startX;
                        int move_bigY = moveY - startY;
                        //拿到当前控件未移动的坐标
                        int left = view.getLeft();
                        int top = view.getTop();
                        left += move_bigX;
                        top += move_bigY;
                        if (left <= 0) {
                            left = 0;
                        }
                        if (top <= 0) {
                            top = 0;
                        }
                        int right = left + view.getWidth();
                        int bottom = top + view.getHeight();
                        if (right >= screenWidth) {
                            right = screenWidth;
                            left = right - view.getWidth();
                        }
                        if (bottom >= height) {
                            bottom = height;
                            top = bottom - view.getHeight();
                        }
                        view.layout(left, top, right, bottom);
                        startX = moveX;
                        startY = moveY;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (isClickEffect) {
                            animation = AnimationUtils.loadAnimation(context, R.anim.up_animation);
                            v.startAnimation(animation);
                        }
                        final int imageLeft = view.getLeft();
                        final int imageRight = view.getRight();
                        final int imageTop = view.getTop();
                        final int imageBottom = view.getBottom();
                        if (imageBottom <= transHeight + view.getHeight()) {
                            Animation animation = new TranslateAnimation(0, 0, 0, -imageTop);
                            setAnimations(view, "top", animation);
                            return true;
                        }
                        if (imageTop >= height - transHeight - view.getHeight()) {
                            Animation animation = new TranslateAnimation(0, 0, 0, height - imageBottom);
                            setAnimations(view, "bottom", animation);
                            return true;
                        }
                        if (imageLeft + (view.getWidth() * 0.5f) < screenWidth * 0.5f) {
                            Animation animation = new TranslateAnimation(0, -imageLeft, 0, 0);
                            setAnimations(view, "left", animation);
                        } else {
                            Animation animation = new TranslateAnimation(0, screenWidth - imageRight, 0, 0);
                            setAnimations(view, "right", animation);
                        }
                        break;
                }
                return true;
            }
        });
    }

    private static void setAnimations(final View view, final String type, final Animation animation) {
        final int imageRight = view.getRight();
        final int imageLeft = view.getLeft();
        final int imageTop = view.getTop();
        final int imageBottom = view.getBottom();
        animation.setDuration(300);
        animation.setFillEnabled(true);//使其可以填充效果从而不回到原地
        animation.setFillAfter(true);//不回到起始位置
        animation.setInterpolator(new AccelerateInterpolator());

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
                int height = screenHeight - statusHeight - navigationHeight;
                switch (type) {
                    case "top":
                        view.layout(imageLeft, 0, imageRight, view.getHeight());
                        break;
                    case "left":
                        view.layout(0, imageTop, view.getWidth(), imageBottom);
                        break;
                    case "right":
                        view.layout(screenWidth - view.getWidth(), imageTop, screenWidth, imageBottom);
                        break;
                    case "bottom":
                        view.layout(imageLeft, height - view.getHeight(), imageRight, height);
                        break;
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animation);
    }
}
