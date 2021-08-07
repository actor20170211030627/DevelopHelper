package com.actor.androiddevelophelper.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.actor.androiddevelophelper.Global;
import com.actor.androiddevelophelper.R;

/**
 * description: 画 ARGB, RGB, Color, drawPaint
 * company    :
 *
 * @author : ldf
 * date       : 2021/8/5 on 17
 * @version 1.0
 */
public class CanvasArgbColorView extends View {

    protected Paint  paint;

    public CanvasArgbColorView(Context context) {
        super(context);
        init(context, null);
    }

    public CanvasArgbColorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CanvasArgbColorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public CanvasArgbColorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    protected void init(Context context, @Nullable AttributeSet attrs) {
        paint = new Paint();
        paint.setAntiAlias(true);
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
        //canvas 画颜色, 整个Canvas的背景颜色
        canvas.drawRGB(139, 197, 186);
        canvas.drawARGB(255, 139, 197, 186);
        canvas.drawColor(Color.MAGENTA);

        paint.setColor(getResources().getColor(R.color.red_trans_CC99));
        canvas.drawPaint(paint);
    }
}
