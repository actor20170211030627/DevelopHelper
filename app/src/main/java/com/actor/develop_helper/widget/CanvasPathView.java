package com.actor.develop_helper.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.actor.develop_helper.Global;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 画path
 * company    :
 * @author    : ldf
 * date       : 2021/8/5 on 17:49
 */
public class CanvasPathView extends View {

    protected Paint paint;
    protected Path  path;
    protected List<Point> pointList;

    public CanvasPathView(Context context) {
        super(context);
        init(context, null);
    }

    public CanvasPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CanvasPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public CanvasPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    protected void init(Context context, @Nullable AttributeSet attrs) {
        paint = new Paint();
        paint.setAntiAlias(true);
        path = new Path();
        //用pointList记录不同的path的各处的连接点
        pointList = new ArrayList<>();
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
        int quarter = canvasWidth / 4;
        int deltaY = (int) (quarter * 0.75F);
//        float deltaY = quarter * 0.75F;

        paint.setColor(Color.RED);
        paint.setStrokeWidth(Global.DP1 * 2);
        paint.setStyle(Paint.Style.FILL);

        //1.向Path中加入Arc
        path.addArc(0F, 0F, quarter, deltaY, 0, 135);
        path.arcTo(0F, 0F, quarter, deltaY, 155, 90, true);

        //2.向Path中加入Oval
        path.addOval(quarter, 0F, quarter * 2F, deltaY, Path.Direction.CCW);

        //3.向Path中添加Circle
        path.addCircle(quarter * 2.5F, deltaY / 2F, deltaY / 2F, Path.Direction.CCW);

        //4.向Path中添加Rect
        path.addRect(quarter * 3F, 0F, quarter * 4F, deltaY, Path.Direction.CCW);
        canvas.drawPath(path, paint);


        //用Path画线
        paint.setStyle(Paint.Style.STROKE);
        canvas.translate(0F, deltaY * 1.2F);
        canvas.drawPath(path, paint);


        //使用lineTo、arcTo、quadTo、cubicTo画线
        canvas.translate(0F, deltaY * 1.2F);
        path.reset();

        //1.绘制线段
        path.moveTo(0F, 0F);
        path.lineTo(quarter / 2F, 0F);
        pointList.add(new Point(0, 0));
        pointList.add(new Point(quarter / 2, 0));

        //2.绘制椭圆右上角的四分之一的弧线
        path.arcTo(0F, 0F, quarter, deltaY, 270F, 90F, false);
        pointList.add(new Point(quarter, deltaY / 2));

        //3.绘制椭圆左下角的四分之一的弧线
        //将画笔移动到我们下一处要绘制arc的起点上, 第3个蓝点
        path.moveTo(quarter * 1.5F, deltaY);
        path.arcTo(quarter, 0F, quarter * 2F, deltaY, 90, 90, false);
        pointList.add(new Point((int)(quarter * 1.5), deltaY));

        //4.绘制二阶贝塞尔曲线
        //再次通过调用path的moveTo方法，移动画笔
        path.moveTo(quarter * 1.5F, deltaY);
        //绘制二阶贝塞尔曲线, 添加一个控制点，以及一个终点
        path.quadTo(quarter * 2F, 0F, quarter * 2.5F, deltaY / 2F);
        pointList.add(new Point(quarter * 2, 0));
        pointList.add(new Point((int)(quarter * 2.5), deltaY / 2));

        //5.绘制三阶贝塞尔曲线, 需要两个控制点，最后也需要一个终点
        path.cubicTo(quarter * 3F, 0F, quarter * 3.5F, deltaY / 2F, quarter * 4F, deltaY);
        pointList.add(new Point(quarter * 3, 0));
        pointList.add(new Point((int) (quarter * 3.5), deltaY / 2));
        pointList.add(new Point(quarter * 4, deltaY));

        //将Path绘制到Canvas上
        canvas.drawPath(path, paint);

        //6.最后绘制Path的连接点，方便我们大家对比观察
        paint.setStrokeWidth(Global.DP1 * 5);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(Color.BLUE);
        for(Point p : pointList) {
            canvas.drawPoint(p.x, p.y, paint);
        }

        //7.沿着一条 Path 来绘制文字
        paint.setStrokeWidth(Global.DP1 * 2);
        path.reset();
        path.moveTo(0, deltaY);//设置起点
        path.cubicTo(0F, deltaY, quarter, 0F, quarter * 3.5F, deltaY);//三次贝塞尔
        canvas.translate(0F, deltaY * 1.2F);
        canvas.drawPath(path, paint);
        //打点
        pointList.clear();
        pointList.add(new Point(0, deltaY));
        pointList.add(new Point(quarter, 0));
        pointList.add(new Point((int) (quarter * 3.5), deltaY));
        paint.setStrokeWidth(Global.DP1 * 5);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(Color.BLACK);
        for(Point p : pointList) {
            canvas.drawPoint(p.x, p.y, paint);
        }
        //画文字(逆时针画圆, 会让路径上的文字顶部朝内!)
        paint.setStrokeWidth(Global.DP1 * 2);
        paint.setColor(Color.RED);
        paint.setTextSize(35);
        paint.setStrokeWidth(0.0F);
        //沿着一条 Path 来绘制文字. 如果线段不是连续画的, 那么画的文字有可能在某段线段的结尾处重叠, 见Demo
        canvas.drawTextOnPath("这是canvas.drawTextOnPath()方法", path, 30, 5, paint);
    }
}
