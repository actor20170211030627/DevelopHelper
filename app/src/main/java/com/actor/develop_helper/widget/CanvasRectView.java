package com.actor.develop_helper.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.actor.develop_helper.Global;

/**
 * description: 画矩形
 * company    :
 *
 * @author : ldf
 * date       : 2021/8/4 on 22:51
 */
public class CanvasRectView extends View {

    protected Paint paint;

    public CanvasRectView(Context context) {
        super(context);
        init(context, null);
    }

    public CanvasRectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CanvasRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public CanvasRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        float px10 = 10;
        int canvasWidth = getWidth();
        int canvasHeight = getHeight();
        float rectX = canvasWidth / 3F;
        float rectY = canvasHeight / 3F;

        //1.画矩形1, 默认画笔的填充色是黑色
        canvas.drawRect(px10, px10, rectX, rectY, paint);

        //2.画矩形2
        paint.setColor(Color.GREEN);
        canvas.save();
        canvas.translate(rectX + px10, 0F);
        canvas.drawRect(0, px10, rectX, rectY, paint);

        //3.圆角矩形, 好像有圆角比较厚的问题?: https://blog.csdn.net/kuaiguixs/article/details/78753149
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Global.DP1 * 8);
        paint.setColor(Color.BLUE);
        canvas.restore();
        canvas.translate(0, rectY + px10);
//        canvas.save();
        canvas.drawRoundRect(px10, px10, rectX, rectY, 50, 80, paint);

        //4.双层圆角
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            canvas.translate(rectX + px10 * 3F, 0F);
            RectF outer = new RectF(0F, px10, rectX, rectY);
            RectF inter = new RectF(0F, px10, rectX, rectY);
            canvas.drawDoubleRoundRect(outer, 80F, 100F, inter, 20F, 30F, paint);
        }
    }
}
