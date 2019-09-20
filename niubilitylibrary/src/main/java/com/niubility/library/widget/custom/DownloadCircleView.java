package com.niubility.library.widget.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class DownloadCircleView extends View {

    Paint mBgPaint;
    Paint mStepPaint;
    Paint mTxtCirclePaint;
    Paint mTxtPaint;
    int outsideRadius=160;
    int progressWidth =8;
    float progresTtextSize  = 24;
    float progressCircleRadius;
    Context context;
    public DownloadCircleView(Context context) {
        super(context);
    }
    public DownloadCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public DownloadCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width;
        int height;
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);


        if (mode == MeasureSpec.EXACTLY) {
            width = size;
        } else {
            width = (int) ((2 * outsideRadius) + progressWidth);
        }
        size = MeasureSpec.getSize(heightMeasureSpec);
        mode = MeasureSpec.getMode(heightMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            height = size;
        } else {
            height = (int) ((2 * outsideRadius) + progressWidth);
        }

        progressCircleRadius = progresTtextSize / 2 + 12;
        outsideRadius = width / 2 - (int) progressCircleRadius;

        setMeasuredDimension(width, height);
    }



    private void init(Context context) {
        this.context = context;
        mBgPaint = new Paint();
        mBgPaint.setStrokeWidth(8);
        mBgPaint.setColor(Color.GRAY);
        this.mBgPaint.setAntiAlias(true); //消除锯齿
        this.mBgPaint.setStyle(Paint.Style.STROKE); //绘制空心圆

        mStepPaint = new Paint();
        mStepPaint.setStrokeWidth(8);
        mStepPaint.setColor(Color.parseColor("#238838"));
        this.mStepPaint.setAntiAlias(true); //消除锯齿
        this.mStepPaint.setStyle(Paint.Style.STROKE); //绘制空心圆

        mTxtCirclePaint = new Paint();
//        mTxtCirclePaint.setColor(Color.parseColor("#3B4463"));
        mTxtCirclePaint.setColor(Color.parseColor("#238838"));
        this.mTxtCirclePaint.setAntiAlias(true); //消除锯齿
        this.mTxtCirclePaint.setStyle(Paint.Style.FILL); //绘制实心圆

        mTxtPaint = new Paint();
        mTxtPaint.setTextSize(progresTtextSize);
        mTxtPaint.setColor(Color.WHITE);
        this.mTxtPaint.setAntiAlias(true); //消除锯齿
        this.mTxtPaint.setStyle(Paint.Style.FILL); //绘制实心圆
    }
    float maxProgress=100f;
    float progress  =0f;

    public void setProgress(float progress) {
        this.progress = progress;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //灰色圆圈
        int circlePoint = getWidth() / 2;
        canvas.drawCircle(circlePoint, circlePoint, outsideRadius, mBgPaint); //画出圆
        //进度
        RectF oval = new RectF();
        oval.left=circlePoint - outsideRadius;
        oval.top=circlePoint - outsideRadius;
        oval.right=circlePoint + outsideRadius;
        oval.bottom=circlePoint + outsideRadius;
        float range = 360 * (progress / maxProgress);
        canvas.drawArc(oval, -90,  range, false, mStepPaint);  //根据进度画圆弧
        //轨道圆和文字
        double x1 = circlePoint + outsideRadius * Math.cos((range-90) * 3.14 / 180);
        double y1 = circlePoint + outsideRadius * Math.sin((range-90) * 3.14 / 180);
//        canvas.drawCircle((float) x1, (float) y1, outsideRadius/4, mTxtCirclePaint);
        canvas.drawCircle((float) x1, (float) y1, progressCircleRadius, mTxtCirclePaint);
        String txt = (int) progress + "%";
        float strwid  = mTxtPaint.measureText(txt);//直接返回参数字符串所占用的宽度
//        canvas.drawText(txt,(float) x1-strwid/2, (float) y1+progresTtextSize/2-progressWidth/2, mTxtPaint);//y值可能是圆环内圆

        Rect tempRect = new Rect();
        mTxtPaint.getTextBounds(txt, 0, txt.length(), tempRect);
        canvas.drawText(txt,(float) x1-strwid/2, (float) y1 + (float) (tempRect.height() / 2), mTxtPaint);

    }

}
