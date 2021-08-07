package com.actor.androiddevelophelper.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.actor.androiddevelophelper.Global;

/**
 * description: 画线
 * company    :
 *
 * @author : ldf
 * date       : 2021/8/4 on 14
 * @version 1.0
 */
public class CanvasLineView extends View {

    protected Paint mPaint;

    public CanvasLineView(Context context) {
        super(context);
        init(context, null);
    }

    public CanvasLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CanvasLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public CanvasLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    protected void init(Context context, @Nullable AttributeSet attrs) {
        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(Global.DP1 * 6);
        mPaint.setColor(Color.RED);
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
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        //1/4
        float quarter = canvasWidth / 4F;
        //间距
        int span = Global.DP1 * 10;

        //1.绘制坐标轴
        mPaint.setColor(Color.GREEN);
        canvas.drawLine(10, 10, canvasWidth - span, 10, mPaint);//绘制x轴
        mPaint.setColor(Color.BLUE);
        canvas.drawLine(10, 10, 10, canvasHeight - span, mPaint);//绘制y轴

        //2.画布平移后，第二次绘制坐标轴
        canvas.translate(quarter, quarter);//把坐标系向右下角平移
        mPaint.setColor(Color.GREEN);
        canvas.drawLine(0, 0, quarter * 3  - span, 0, mPaint);//绘制x轴
        mPaint.setColor(Color.BLUE);
        canvas.drawLine(0, 0, 0, canvasHeight - quarter - span, mPaint);//绘制y轴

//        //3.画布接着上次平移结果, 再次平移, 并旋转坐标系，第三次绘制坐标轴
        canvas.translate(quarter, quarter);//在上次平移的基础上再把坐标系向右下角平移
        canvas.rotate(30);//基于当前绘图坐标系的原点旋转坐标系
        mPaint.setColor(Color.GREEN);
        canvas.drawLine(0, 0, canvasWidth, 0, mPaint);//绘制x轴
        mPaint.setColor(Color.BLUE);
        canvas.drawLine(0, 0, 0, canvasHeight, mPaint);//绘制y轴

        canvas.translate(- quarter, - quarter);
        //旋转回去
        canvas.rotate(-30);
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(30);
        mPaint.setStrokeWidth(0.0F);
        canvas.drawText("红线部分是paint.drawLines()", 0, 0, mPaint);

        mPaint.setStrokeWidth(Global.DP1 * 2);
        float[] lines = {0, 0, quarter, 50,//第1段线
                quarter, 50, 400, 600,//第2段线
                400, 600, 50, 600,
                50, 600, 100, 500,
                100, 500, 50, 50,
                50, 50, 0, 0};
        canvas.drawLines(lines, mPaint);
    }
}
