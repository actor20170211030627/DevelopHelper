package com.actor.develop_helper.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.actor.develop_helper.Global;

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
        //https://www.jianshu.com/p/a38bb7bbb74f
        //https://www.jianshu.com/p/2491150926e6

        /**
         * 设置画笔样式
         * Paint.Style.FILL             ：填充内部
         * Paint.Style.FILL_AND_STROKE  ：填充内部和描边
         * Paint.Style.STROKE           ：描边
         */
        mPaint.setStyle(Paint.Style.STROKE);
        /**
         * 设置线冒样式
         * Paint.Cap.BUTT   直角, 无线帽 (会短一点儿)
         * Paint.Cap.ROUND  圆形线帽
         * Paint.Cap.SQUARE 直角线帽
         */
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        /**
         * 设置线段连接处样式
         * Paint.Join.MITER（结合处为锐角）；
         * Paint.Join.ROUND (结合处为圆弧)；
         * Paint.Join.BEVEL (结合处为直线)
         */
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        /**
         * 设置笔画的倾斜度, 必须>=0
         */
        mPaint.setStrokeMiter(0f);
        /**
         * 设置路径样式; 取值类型是所有PathEffect的子类
         * @see android.graphics.ComposePathEffect(PathEffect outerpe, PathEffect innerpe)  用来合并两个特效的:
         *          // 有先后顺序的，将第二个参数的innerpe的特效作用于路径上
         *          // 然后再在此加了特效的路径上,再加第一个参数dashEffect特效。
         * @see android.graphics.CornerPathEffect(float radius)      圆形拐角效果:
         *          将原来Path生硬的直线拐角，变成圆形拐角(传入半径radius画圆, 替代锐角)
         * @see android.graphics.DashPathEffect(float intervals[], float phase) 虚线效果:
         *          intervals: 组成虚线的各个线段的长度；整条虚线就是由intervals[]中这些基本线段循环组成.
         *                      // 虚线效果，参数分别是：“虚线的长度”、“虚线之间的空白长度”、“ offset”
         *                      float[] dashes = new float[]{10f, 10f};
         *          phase：开始绘制的偏移值
         * @see android.graphics.DiscretePathEffect(float segmentLength, float deviation) 离散路径效果:
         *          segmentLength：表示将原来的路径切成多长的线段。如果值为2，那么这个路径就会被切成一段段由长度为2的小线段。所以这个值越小，所切成的小线段越多；这个值越大，所切成的小线段越少。
         *          deviation：表示被切成的每个小线段的可偏移距离。值越大，就表示每个线段的可偏移距离就越大，就显得越凌乱，值越小，每个线段的可偏移原位置的距离就越小。
         * @see android.graphics.PathDashPathEffect(Path shape, float advance, float phase,Style style) 印章路径效果
         *          用另一个路径图案做为印章，沿着指定路径一个个盖上去
         *          Path shape:表示印章路径；
         *          float advance：表示两个印章路径间的距离，间隙；
         *          float phase：路径绘制偏移距离，与DashPathEffect中的phase参数意义相同;
         *          Style style：表示在遇到转角时，如何操作印章以使转角平滑过渡，取值有：
         *              Style.ROTATE: 表示通过旋转印章来过渡转角
         *              Style.MORPH: 表示通过变形印章来过渡转角
         *              Style.TRANSLATE: 表示通过位移来过渡转角
         * @see android.graphics.SumPathEffect(PathEffect first, PathEffect second) 用来合并两个特效的:
         *          // 对原始路径分别作用第一个特效和第二个特效。然后再将这两条路径合并，做为最终结果。
         */
        mPaint.setPathEffect(new CornerPathEffect(5f));
        mPaint.setStrokeWidth(Global.DP1 * 6);  //设置画笔宽度
        mPaint.setColor(Color.RED);
//        mPaint.setARGB();     //同样是设置颜色，但是利用ARGB分开设置
//        mPaint.setAlpha();    //设置画笔透明度


        /**
         * 常用文字设置相关函数
         */
//        mPaint.setTextSize(float textSize);               //设置文字大小
//        mPaint.setFakeBoldText(boolean fakeBoldText);     //设置是否为粗体文字
//        mPaint.setStrikeThruText(boolean strikeThruText); //设置带有删除线效果
//        mPaint.setUnderlineText(boolean underlineText);   //设置下划线
//        mPaint.setTextAlign(Paint.Align align);           //设置开始绘图点位置
//        mPaint.setTextScaleX(float scaleX);               //水平拉伸设置
//        mPaint.setTextSkewX(float skewX);                 //设置字体水平倾斜度，普通斜体字是-0.25，可见往右斜
//        mPaint.setTypeface(Typeface typeface);            //设置字体


//        mPaint.reset();       //重置画笔
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
        /**
         * startX: 线左侧的x坐标
         * startY: 线宽度的中间位置的坐标!!! (不是线的▓↖角那个点, 而是线宽度的▓←这个位置)
         * endY  : 同上
         */
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
        canvas.rotate(30);//基于当前绘图坐标系的 "原点" 旋转坐标系
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


        /**
         * 渐变着色
         */
        //设置着色
        mPaint.setShader(linearGradient);
        canvas.translate(- quarter, quarter);
        canvas.drawLines(lines, mPaint);
    }


    // 渐变颜色
    private final int[] colors = {Color.RED, Color.GREEN, Color.BLUE};
    // 渐变位置（可以为null，表示均匀分布）
    private final float[] positions = null;
    /**
     * 创建LinearGradient对象
     * Create a shader that draws a linear gradient along a line.
     *
     * @param x0        渐变起始点x坐标
     * @param y0        渐变起始点y坐标
     * @param x1        渐变结束点x坐标
     * @param y1        渐变结束点y坐标
     * @param colors    渐变颜色
     * @param positions 位置数组，position的取值范围[0,1],作用是指定某个位置的颜色值，如果传null，渐变就线性变化。
     * @param tile      用于指定控件区域大于指定的渐变区域时，空白区域的颜色填充方法。
     *                      Shader.TileMode.CLAMP: 边缘拉伸，为被shader覆盖区域，使用shader边界颜色进行填充
     *                      Shader.TileMode.REPEAT: 在水平和垂直两个方向上重复，相邻图像没有间隙
     *                      Shader.TileMode.MIRROR: 以镜像的方式在水平和垂直两个方向上重复，相邻图像有间隙
     *                      Shader.TileMode.DECAL: 仅在其原始边界内渲染着色器的图像像素。如果着色器绘制超出其原始边界，则绘制透明黑色。
     */
    //从左上角→右下角的颜色渐变
    private final LinearGradient linearGradient = new LinearGradient(0, 0, 500, 600, colors, positions, Shader.TileMode.CLAMP);
}
