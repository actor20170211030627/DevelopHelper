package com.actor.develop_helper.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.actor.develop_helper.Global;

/**
 * description: 圆弧
 * company    :
 *
 * @author : ldf
 * date       : 2021/8/5 on 15:17
 */
public class CanvasArcView extends View {

    protected Paint paint;
    protected RectF rectF;

    public CanvasArcView(Context context) {
        super(context);
        init(context, null);
    }

    public CanvasArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CanvasArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public CanvasArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    protected void init(Context context, @Nullable AttributeSet attrs) {
        paint = new Paint();
        paint.setAntiAlias(true);
        rectF = new RectF();
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
        int canvasWidth = getWidth();
        int canvasHeight = getHeight();
        int count = 5;
        float ovalHeight = canvasHeight / (count + 1F);
        float padding = Global.DP1 * 2;
        float right = canvasWidth - padding;
        rectF.set(padding, padding, right, ovalHeight);

        paint.setStrokeWidth(Global.DP1 * 3);//线宽
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);//填充模式

        //1.绘制用drawArc绘制完整的椭圆
        canvas.drawArc(rectF, 0, 290, false, paint);

        //2.绘制椭圆的四分之一, 从3点绘制到6点的位置
        canvas.translate(0, ovalHeight + padding);
        canvas.drawArc(rectF, 0, 90, true, paint);

        //3.绘制椭圆的四分之一, 不经过圆心
        canvas.translate(0, ovalHeight + padding);
        canvas.drawArc(rectF, 0, 90, false, paint);

        //4.绘制椭圆的四分之一，经过圆心, 只绘制轮廓线
        paint.setStyle(Paint.Style.STROKE);
        canvas.translate(0, ovalHeight + padding);
        canvas.drawArc(rectF, 0, 90, true, paint);

        //5.绘制带有轮廓线的椭圆的四分之一
        //5.1. 先绘制椭圆的填充部分
        paint.setStyle(Paint.Style.FILL);
        canvas.translate(0, ovalHeight + padding);
        canvas.drawArc(rectF, 0, 90, true, paint);
        //5.2. 再绘制椭圆的轮廓线部分
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        canvas.drawArc(rectF, 0, 90, true, paint);
    }
}
