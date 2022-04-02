package com.actor.develop_helper.widget;

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

import com.actor.develop_helper.Global;
import com.blankj.utilcode.util.SizeUtils;

/**
 * description: 画文字
 * company    :
 *
 * @author : ldf
 * date       : 2021/8/4 on 18
 * @version 1.0
 */
public class CanvasTextView extends View {

    protected static final int textHeight = 50;
    protected Paint     mPaint;
    protected TextPaint textPaint;//TextPaint extends Paint

    public CanvasTextView(Context context) {
        super(context);
        init(context, null);
    }

    public CanvasTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CanvasTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public CanvasTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    protected void init(Context context, @Nullable AttributeSet attrs) {
        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);

        textPaint = new TextPaint();
        textPaint.setColor(Color.BLUE);
        textPaint.setTextSize(SizeUtils.sp2px(16));
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
        //一半宽度
        int halfWidth = canvasWidth / 2;

        //1.绘制描边文本
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(30);
        mPaint.setStyle(Paint.Style.STROKE);//字体描边
        mPaint.setStrokeWidth(1.1F);        //要设置厚度, 否则没有描边
        canvas.save();    //保存Canvas当前状态
        canvas.translate(0, textHeight);
        canvas.drawText("1.绘制描边文本(setStrokeWidth,否则没有描边)", 0, 0, mPaint);

        //2.绘制绿色文本
        mPaint.setTextSize(40);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.GREEN);
        canvas.restore(); //恢复Canvas保存时的状态
        canvas.save();    //保存刚取出来的状态
        canvas.translate(0, textHeight * 2);//将画笔向下移动
        canvas.drawText("2.绘制绿色文本", 0, 0, mPaint);

        //3.设置左对齐
        mPaint.setColor(Color.BLACK);//重新设置为黑色
        mPaint.setTextAlign(Paint.Align.LEFT);//设置左对齐
        canvas.restore();
        canvas.save();
        canvas.translate(halfWidth, textHeight * 3);
        canvas.drawText("3.左对齐文本", 0, 0, mPaint);

        //4.设置居中对齐
        mPaint.setTextAlign(Paint.Align.CENTER);//设置居中对齐
        mPaint.setTextSize(30);
        canvas.restore();
        canvas.save();
        canvas.translate(halfWidth, textHeight * 4);
        canvas.drawText("4.居中对齐文本(相对开始画的位置的y轴)", 0, 0, mPaint);

        //5.设置右对齐
        mPaint.setTextAlign(Paint.Align.RIGHT);//设置右对齐
        mPaint.setTextSize(40);
        canvas.restore();
        canvas.save();
        canvas.translate(halfWidth, textHeight * 5);
        canvas.drawText("5.右对齐文本", 0, 0, mPaint);

        //6.设置下划线
        mPaint.setTextAlign(Paint.Align.LEFT);//重新设置为左对齐
        mPaint.setUnderlineText(true);        //设置具有下划线
        canvas.restore();
        canvas.save();
        canvas.translate(0, textHeight * 6);
        canvas.drawText("6.下划线文本", 0, 0, mPaint);

        //7.绘制加粗文字
        mPaint.setUnderlineText(false);//重新设置为没有下划线
        mPaint.setFakeBoldText(true);//将画笔设置为粗体
        canvas.restore();
        canvas.save();
        canvas.translate(0, textHeight * 7);
        canvas.drawText("7.粗体文本", 0, 0, mPaint);

        //8.文本绕绘制起点顺时针旋转
        mPaint.setFakeBoldText(false);//重新将画笔设置为非粗体状态
        canvas.restore();
        canvas.save();
        canvas.translate(0, textHeight * 8);
        canvas.rotate(20);
        canvas.drawText("8.文本绕绘制起点旋转20°", 0, 0, mPaint);

        //9.换行文字
        canvas.restore();
//        canvas.save();
        canvas.translate(0, textHeight * 11);
        StaticLayout layout = getStaticLayout("9.在Android开发中，Canvas.drawText不会换行，即使\\n也不会换行, 即使一个很长的字符串也只会显示一行，超出部分会隐藏在屏幕之外.StaticLayout是android中处理文字的一个工具类，StaticLayout 处理了文字换行的问题", canvasWidth);
        layout.draw(canvas);

        //10.drawPosText
//        canvas.restore();
        canvas.translate(0, textHeight * 5);
        String str = "10.canvas.drawPosText()";
        canvas.drawPosText(str, getPos(str), mPaint);

        //测量文字宽度
        float width = mPaint.measureText(str);
    }

    /**
     *
     * @param source 文字
     * @param width 画文字区域的宽度
     * @return
     */
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

    //根据传入的字符串, 给每一个字符生成一个坐标
    protected float[] getPos(String str) {
        float[] pos = new float[str.length() * 2];
        for (int i = 0; i < str.length(); i++) {
            pos[i * 2] = i * 25;
            pos[i * 2 + 1] = i * 15;
        }
        return pos;
    }
}
