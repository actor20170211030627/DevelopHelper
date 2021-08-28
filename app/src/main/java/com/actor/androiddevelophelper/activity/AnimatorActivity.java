package com.actor.androiddevelophelper.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.actor.androiddevelophelper.Global;
import com.actor.androiddevelophelper.R;
import com.actor.androiddevelophelper.databinding.ActivityAnimatorBinding;

/**
 * description: 属性动画
 * company    :
 * @author    : ldf
 * date       : 2021/8/28 on 21:51
 */
public class AnimatorActivity extends BaseActivity<ActivityAnimatorBinding> {

    private int maxHeight = -1;
    private ConstraintLayout itemAppRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("属性动画");

        /**
         * 1.差值器?
         */
        viewBinding.itemApp.ivIcon.setImageResource(R.drawable.logo);
        viewBinding.itemApp.tvAppname.setText("应用名");
        viewBinding.itemApp.tvPackagename.setText("包名");
        viewBinding.itemApp.tvVersionname.setText("版本名称");
        viewBinding.itemApp.tvVersioncode.setText("版本号");
        viewBinding.itemApp.tvSize.setText("大小");
        viewBinding.itemApp.tvSignMd5.setText("MD5签名");
        viewBinding.itemApp.tvSignSha1.setText("Sha1签名");
        viewBinding.itemApp.tvSignSha256.setText("Sha256签名");
        itemAppRoot = viewBinding.itemApp.getRoot();
        calculateMaxHeight();


        /**
         * 2.属性动画
         * 2.1.TextView 改变高度
         */
        ObjectAnimator animatorTvContent = ObjectAnimator.ofInt(viewBinding.tvContent, "height", Global.DP1 * 120, 0);
        animatorTvContent.setDuration(1000L);
        animatorTvContent.setRepeatMode(ValueAnimator.REVERSE);
        animatorTvContent.setRepeatCount(ValueAnimator.INFINITE);
        animatorTvContent.start();

        /**
         * 2.2.旋转动画
         */
        ObjectAnimator rotationX1 = ObjectAnimator.ofFloat(viewBinding.ivRotationY1, "rotationX", 0, 360);
        rotationX1.setDuration(2000);
        rotationX1.setRepeatMode(ValueAnimator.REVERSE);
        rotationX1.setRepeatCount(ValueAnimator.INFINITE);
        rotationX1.start();
        ObjectAnimator rotationY2 = ObjectAnimator.ofFloat(viewBinding.btnRotationY2, "rotationY", 0, 360);
        rotationY2.setDuration(2000);
        rotationY2.setRepeatMode(ValueAnimator.REVERSE);
        rotationY2.setRepeatCount(ValueAnimator.INFINITE);
        rotationY2.start();
    }

    //计算高度, 并开始动画
    private void calculateMaxHeight() {
        itemAppRoot.post(() -> {
            if (maxHeight == -1) {
                maxHeight = itemAppRoot.getMeasuredHeight();
                maxHeight = itemAppRoot.getHeight();
                close();
            }
        });
        //这样不行, 会返回0
//        if (maxHeight == -1) {
//            maxHeight = view.getMeasuredHeight();
//            maxHeight = view.getHeight();
//        }

//        //也可以对 view 进行手动测量
//        int size = ScreenUtils.getAppScreenWidth() - SizeUtils.dp2px(5);
//        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(size, View.MeasureSpec.AT_MOST);
//        view.measure(widthMeasureSpec, 0);
//        maxHeight = view.getMeasuredHeight();
//        //maxHeight = view.getHeight();
    }

    private void close() {
        //我们创建了一个值的产生器，这个值的产生器可以帮助我们产生 0~maxHeight 的中间值
        ValueAnimator valueAnimator = ValueAnimator.ofInt(maxHeight, 0);
        valueAnimator.setDuration(2000L);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        //当产生中间值的时候，会不断的调用此方法,并且此方法运行在主线程
        valueAnimator.addUpdateListener(animation -> {
            int temp = (int) animation.getAnimatedValue();
            //ConstraintLayout 的高度=0的时候, 效果和 WRAP_CONTENT 一致, 会导致高度闪一下...
            if (temp == 0) {
                temp = ViewGroup.LayoutParams.MATCH_PARENT + ViewGroup.LayoutParams.WRAP_CONTENT;
            }
            ViewGroup.LayoutParams layoutParams = itemAppRoot.getLayoutParams();
            layoutParams.height = temp;
            itemAppRoot.setLayoutParams(layoutParams);
        });
        //开始产生中间值
        valueAnimator.start();
    }
}