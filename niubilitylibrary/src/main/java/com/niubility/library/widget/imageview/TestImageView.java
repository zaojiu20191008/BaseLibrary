package com.niubility.library.widget.imageview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;

public class TestImageView extends androidx.appcompat.widget.AppCompatImageView {


    public TestImageView(Context context) {
        super(context);
    }

    public TestImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width_mode = MeasureSpec.getMode(widthMeasureSpec);
        int width_size = MeasureSpec.getSize(widthMeasureSpec);

        int height_mode = MeasureSpec.getMode(heightMeasureSpec);
        int height_size = MeasureSpec.getSize(heightMeasureSpec);

        Log.i("test", "onMeasure: width_mode -> " + width_mode);
        Log.i("test", "onMeasure: width_size -> " + width_size);
        Log.i("test", "onMeasure: height_mode -> " + height_mode);
        Log.i("test", "onMeasure: height_size -> " + height_size);

        Log.i("test", "onMeasure: width -> " + getMeasuredWidth());
        Log.i("test", "onMeasure: height -> " + getMeasuredHeight());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);


        Log.i("test", "onLayout: width -> " + getWidth());
        Log.i("test", "onLayout: height -> " + getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.i("test", "onDraw: width -> " + getWidth());
        Log.i("test", "onDraw: height -> " + getHeight());
        Log.i("test", "onDraw: Measuredwidth -> " + getMeasuredWidth());
        Log.i("test", "onDraw: Measuredheight -> " + getMeasuredHeight());
    }
}
