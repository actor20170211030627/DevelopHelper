package com.actor.develop_helper.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.actor.develop_helper.databinding.ActivityCanvasDrawBinding;
import com.actor.develop_helper.widget.CanvasArcView;
import com.actor.develop_helper.widget.CanvasArgbColorView;
import com.actor.develop_helper.widget.CanvasBitmapView;
import com.actor.develop_helper.widget.CanvasCircleView;
import com.actor.develop_helper.widget.CanvasLineView;
import com.actor.develop_helper.widget.CanvasOvalView;
import com.actor.develop_helper.widget.CanvasPathView;
import com.actor.develop_helper.widget.CanvasPointView;
import com.actor.develop_helper.widget.CanvasRectView;
import com.actor.develop_helper.widget.CanvasTextView;
import com.actor.myandroidframework.widget.BaseSpinner;

/**
 * Path绘制测试
 *
 *  TODO: 2021/5/8 待完成
 * PathMeasure:
 * https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&tn=baidu&wd=PathMeasure&oq=PathMeasure
 * https://blog.csdn.net/weiwozhiyi/article/details/70149147
 *
 * //26+
 * PathUtils.flatten();
 *
 * android 根据点计算贝塞尔曲线:
 * https://www.baidu.com/s?ie=UTF-8&wd=android%20%E6%A0%B9%E6%8D%AE%E7%82%B9%E8%AE%A1%E7%AE%97%E8%B4%9D%E5%A1%9E%E5%B0%94%E6%9B%B2%E7%BA%BF
 *
 *
 *
 * doodleview: 画画板
 * https://www.baidu.com/s?ie=UTF-8&wd=doodleview
 *
 * F:\AndroidProjects\AndroidNote\Animation动画\3.2.Path动画(用属性动画实现)
 *
 *
 *
 *
 * //绘制顶点数据, 一般用于绘制特效图片: https://blog.csdn.net/ghcghc123/article/details/84159216
 * canvas.drawVertices();
 * canvas.drawBitmapMesh();
 *
 * //绘制文字，但加入了两项额外的设置——上下文和文字方向(阿拉伯文字上下文不同,显示的文字也不同...)
 * https://blog.csdn.net/qqqq245425070/article/details/79027979
 * canvas.drawTextRun();
 *
 * //api29+, 绘制给定的 RenderNode。这仅在硬件渲染中受支持.
 * canvas.drawRenderNode(new RenderNode("name"));
 */
public class CanvasDrawActivity extends BaseActivity<ActivityCanvasDrawBinding> {

    private BaseSpinner<String> bsPaintStyle;
    private FrameLayout         flContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Canvas绘制测试");
        bsPaintStyle = viewBinding.bsPaintStyle;
        flContainer = viewBinding.flContainer;

        bsPaintStyle.setOnItemSelectedListener((parent, view, position, id) -> {
            switch (position) {
                case 0://颜色drawARGB,RGB,Color,Paint
                    flContainer.removeAllViews();
                    flContainer.addView(new CanvasArgbColorView(mActivity));
                    break;
                case 1://线drawLine,drawLines
                    flContainer.removeAllViews();
                    flContainer.addView(new CanvasLineView(mActivity));
                    break;
                case 2://文字drawText,drawPosText
                    flContainer.removeAllViews();
                    flContainer.addView(new CanvasTextView(mActivity));
                    break;
                case 3://点drawPoint,drawPoints
                    flContainer.removeAllViews();
                    flContainer.addView(new CanvasPointView(mActivity));
                    break;
                case 4://矩形drawRect
                    flContainer.removeAllViews();
                    flContainer.addView(new CanvasRectView(mActivity));
                    break;
                case 5://圆drawCircle
                    flContainer.removeAllViews();
                    flContainer.addView(new CanvasCircleView(mActivity));
                    break;
                case 6://椭圆drawOval
                    flContainer.removeAllViews();
                    flContainer.addView(new CanvasOvalView(mActivity));
                    break;
                case 7://圆弧drawArc
                    flContainer.removeAllViews();
                    flContainer.addView(new CanvasArcView(mActivity));
                    break;
                case 8://位图drawBitmap
                    flContainer.removeAllViews();
                    flContainer.addView(new CanvasBitmapView(mActivity));
                    break;
                case 9://路径drawPath,drawTextOnPath
                    flContainer.removeAllViews();
                    flContainer.addView(new CanvasPathView(mActivity));
                    break;
                default:
                    break;
            }
        });
        bsPaintStyle.getOnItemSelectedListener().onItemSelected(null, null, 0, 0L);
    }
}