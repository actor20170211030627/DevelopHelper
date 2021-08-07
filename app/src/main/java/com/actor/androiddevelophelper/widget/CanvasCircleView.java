package com.actor.androiddevelophelper.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.actor.androiddevelophelper.Global;
import com.blankj.utilcode.util.SizeUtils;

/**
 * description: 画圆
 * company    :
 * @author    : ldf
 * date       : 2021/8/5 on 10:12
 */
public class CanvasCircleView extends View {

    protected Paint paint;
    protected TextPaint textPaint;//TextPaint extends Paint

    public CanvasCircleView(Context context) {
        super(context);
        init(context, null);
    }

    public CanvasCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CanvasCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public CanvasCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    protected void init(Context context, @Nullable AttributeSet attrs) {
        paint = new Paint();
        paint.setAntiAlias(true);

        textPaint = new TextPaint();
        textPaint.setColor(Color.BLUE);
        textPaint.setTextSize(SizeUtils.sp2px(12));
        textPaint.setAntiAlias(true);
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
        //半径
        float radius = canvasWidth / 7F;
        //直径
        float diameter = radius * 2;
        int dp10 = Global.DP1 * 10;

        //1.绘制圆
        paint.setColor(Color.GREEN);//设置颜色
        paint.setStyle(Paint.Style.FILL);//默认绘图为填充模式
        canvas.save();
        canvas.translate(radius + dp10, radius + dp10);
        //cx,cy: 圆心坐标
        canvas.drawCircle(0F, 0F, radius, paint);

        //2.绘制两个圆形成圆环
        //2.1.首先绘制大圆
        canvas.translate(diameter + dp10, 0);
        canvas.drawCircle(0, 0, radius, paint);
        //2.2.绘制小圆，让小圆覆盖大圆，形成圆环效果
        paint.setColor(Color.RED);//将画笔设置为白色，画小圆
        canvas.drawCircle(0, 0, radius * 0.75F, paint);

        //3.通过线条绘图模式绘制圆环
        paint.setStyle(Paint.Style.STROKE);//绘图为线条模式
        //
        paint.setStrokeWidth(radius * 0.25F);
        canvas.translate(diameter + dp10, 0);
        canvas.drawCircle(0, 0, radius, paint);

        //4.画自动换行的文字
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(0.0F);
        paint.setTextSize(SizeUtils.sp2px(12));
        canvas.translate(-radius, -radius);
        StaticLayout layout = getStaticLayout("setStrokeWidth=radius * 0.25", (int) diameter);
        layout.draw(canvas);

        //5.画一条线
        canvas.restore();
        canvas.drawLine(0, dp10, canvasWidth, dp10, paint);
    }

    protected StaticLayout getStaticLayout(@NonNull CharSequence source, int width) {
        StaticLayout layout;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            layout = StaticLayout.Builder.obtain(source, 0, source.length(), textPaint, width).build();
        } else {
            layout = new StaticLayout(source, textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
        }
//        CharSequence text = layout.getText();
        return layout;
    }
}
