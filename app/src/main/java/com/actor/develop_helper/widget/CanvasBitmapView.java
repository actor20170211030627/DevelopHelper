package com.actor.develop_helper.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.actor.develop_helper.Global;
import com.actor.develop_helper.R;

/**
 * description: 画Bitmap
 * company    :
 * @author    : ldf
 * date       : 2021/8/5 on 15:38
 */
public class CanvasBitmapView extends View {

    protected Paint  paint;
    protected Bitmap bitmap;
    protected Rect   rect;
    protected RectF  dstRecF;

    public CanvasBitmapView(Context context) {
        super(context);
        init(context, null);
    }

    public CanvasBitmapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CanvasBitmapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public CanvasBitmapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    protected void init(Context context, @Nullable AttributeSet attrs) {
        paint = new Paint();
        paint.setAntiAlias(true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        rect = new Rect();
        dstRecF = new RectF();
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

        //1.直接完全绘制Bitmap
        canvas.drawBitmap(bitmap, 0, 0, paint);

        //2.绘制Bitmap的一部分，并对其拉伸
        // rect定义了要绘制Bitmap的哪一部分. left, top, right, bottom
        rect.set(0, 0, bitmap.getWidth(), (int)(bitmap.getHeight() * 0.33));

        // dstRecF定义了要将绘制的Bitmap拉伸到哪里. left, top, right, bottom
        dstRecF.set(0, bitmap.getHeight(), getWidth(), -1);
        //ratio = x轴放大比例
        float radio = dstRecF.width() / this.rect.width();
        //根据比例算出放大后的高度
        float dstHeight = this.rect.height() * radio;
        dstRecF.bottom = dstRecF.top + dstHeight;
        canvas.drawBitmap(bitmap, this.rect, dstRecF, paint);


        /**
         * 矢量图Picture   https://blog.csdn.net/u013135085/article/details/81216663
         * @param picture 可以把Picture看作是一个录制Canvas操作的录像机, 使用Picture相比于再次调用绘图API，开销是比较小的.
         */
//        Picture picture = new Picture();
//        canvas.drawPicture(picture);
    }
}
