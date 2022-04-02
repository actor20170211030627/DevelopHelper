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
 * description: 画椭圆
 * company    :
 *
 * @author : ldf
 * date       : 2021/8/5 on 12:40
 */
public class CanvasOvalView extends View {

    protected Paint paint;
    protected RectF rectF;

    public CanvasOvalView(Context context) {
        super(context);
        init(context, null);
    }

    public CanvasOvalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CanvasOvalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public CanvasOvalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        float padding = Global.DP1 * 2F;
        float right = canvasWidth / 3F - padding * 2;
        float bottom = right / 2F;
        rectF.set(padding, padding, right, bottom);

        //1.绘制椭圆形轮廓线
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Global.DP1 * 10 / 3F);
        paint.setColor(Color.RED);
        canvas.drawOval(rectF, paint);

        //2.绘制椭圆形填充面
        paint.setStyle(Paint.Style.FILL);
        canvas.translate(right + padding, 0);
        canvas.drawOval(rectF, paint);

        //3.1.画两个椭圆，形成轮廓线和填充色不同的效果
        paint.setStyle(Paint.Style.FILL);
        canvas.translate(right + padding, 0);
        canvas.drawOval(rectF, paint);

        //3.2.将线条颜色设置为蓝色，绘制轮廓线
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        canvas.drawOval(rectF, paint);
    }
}
