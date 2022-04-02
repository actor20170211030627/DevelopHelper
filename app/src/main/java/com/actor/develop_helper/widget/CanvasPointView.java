package com.actor.develop_helper.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.actor.develop_helper.Global;

/**
 * description: 画点
 * company    :
 * @author    : ldf
 * date       : 2021/8/4 on 22:08
 */
public class CanvasPointView extends View {

    protected Paint mPaint;

    public CanvasPointView(Context context) {
        super(context);
        init(context, null);
    }

    public CanvasPointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CanvasPointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public CanvasPointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    protected void init(Context context, @Nullable AttributeSet attrs) {
        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //match_parent, wrap_content
        boolean b = MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED;
        if (b) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(Global.APP_SCREEN_HEIGHT, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        //设置线宽，如果不设置线宽，无法绘制点
        mPaint.setStrokeWidth(Global.DP1 * 20);
        //设置线段两端圆角效果.   BUTT：无圆角(默认) ROUND：圆角  SQUARE：方角
        mPaint.setStrokeCap(Paint.Cap.BUTT);

        //1.从点的中心开始画
        canvas.drawPoint(Global.DP1 * 20, Global.DP1 * 20, mPaint);

        //绘制Cap为ROUND的点
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(Color.GREEN);
        canvas.translate(Global.DP1 * 50, Global.DP1 * 20);
        canvas.drawPoint(0, 0, mPaint);

        //绘制Cap为SQUARE的点
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        mPaint.setColor(Color.RED);
        canvas.translate(Global.DP1 * 30, 0);
        canvas.drawPoint(0, 0, mPaint);

        //文字说明
        mPaint.setStrokeWidth(0.0F);
        mPaint.setTextSize(35);
        canvas.translate(- Global.DP1 * 80, Global.DP1 * 50);
        canvas.drawText("下方是canvas.drawPoints()", 0,0, mPaint);

        //drawPoints
        mPaint.setStrokeWidth(Global.DP1 * 5);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.translate(0, Global.DP1 * 15);
        float[] points = {20, 0, 40, 20, 60, 40, 80, 35, 100, 32};
        canvas.drawPoints(points, mPaint);
    }
}
