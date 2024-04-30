package com.actor.develop_helper.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.actor.develop_helper.R;
import com.actor.develop_helper.databinding.ActivityAnimatorBinding;

/**
 * description: 属性动画
 * company    :
 *
 * @author : ldf
 * date       : 2021/8/28 on 21:51
 */
public class AnimatorActivity extends BaseActivity<ActivityAnimatorBinding> {

    private int              maxHeight = -1;
    private ConstraintLayout itemAppRoot;
    private ValueAnimator    valueAnimator;
    private AnimatorSet animatorSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("3.属性动画");

        /**
         * 1.ValueAnimator
         */
        viewBinding.itemApp.ivIcon.setImageResource(R.drawable.logo);
        viewBinding.itemApp.tvAppName.setText("应用名");
        viewBinding.itemApp.tvPackageName.setText("包名");
        viewBinding.itemApp.tvVersionName.setText("版本名称");
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
        TextView tvContent = viewBinding.tvContent;
        ObjectAnimator animatorTvContent = ObjectAnimator.ofInt(tvContent, "height", tvContent.getMaxHeight(), 0);
        animatorTvContent.setDuration(1000L);
        animatorTvContent.setRepeatMode(ValueAnimator.REVERSE);
        animatorTvContent.setRepeatCount(ValueAnimator.INFINITE);
        animatorTvContent.start();

        /**
         * 2.2.translationX,translationY平移动画
         */
        TextView tvTranslateHorizontal = viewBinding.tvTranslateHorizontal;
        tvTranslateHorizontal.post(() -> {
            ObjectAnimator animatorHorizontal = ObjectAnimator.ofFloat(tvTranslateHorizontal, "translationX", 0, -tvTranslateHorizontal.getWidth());
            animatorHorizontal.setDuration(1000L);
            animatorHorizontal.setRepeatMode(ValueAnimator.REVERSE);
            animatorHorizontal.setRepeatCount(ValueAnimator.INFINITE);
            animatorHorizontal.start();
        });
        TextView tvTranslateVertical = viewBinding.tvTranslateVertical;
        tvTranslateVertical.post(() -> {
            ObjectAnimator animatorVertical = ObjectAnimator.ofFloat(tvTranslateVertical, "translationY", 0, -tvTranslateVertical.getHeight());
            animatorVertical.setDuration(1000L);
            animatorVertical.setRepeatMode(ValueAnimator.REVERSE);
            animatorVertical.setRepeatCount(ValueAnimator.INFINITE);
            animatorVertical.start();
        });
        //抖动动画
        TextView tvTranslateCycle = viewBinding.tvTranslateCycle;
        tvTranslateCycle.post(() -> {
            ObjectAnimator animatorCycle = ObjectAnimator.ofFloat(tvTranslateCycle, "translationX", 0, -10);
            animatorCycle.setDuration(1000L);
            animatorCycle.setRepeatMode(ValueAnimator.REVERSE);
            animatorCycle.setRepeatCount(ValueAnimator.INFINITE);
            animatorCycle.setInterpolator(new CycleInterpolator(7));
            animatorCycle.start();
        });

        /**
         * 2.3.rotationX,rotationY旋转动画
         */
        ObjectAnimator rotationX1 = ObjectAnimator.ofFloat(viewBinding.ivRotationY1, "rotationX", 0, 360);
        rotationX1.setDuration(2000L);
        rotationX1.setRepeatMode(ValueAnimator.REVERSE);
        rotationX1.setRepeatCount(ValueAnimator.INFINITE);
        rotationX1.start();
        ObjectAnimator rotationY2 = ObjectAnimator.ofFloat(viewBinding.btnRotationY2, "rotationY", 0, 360);
        rotationY2.setDuration(2000L);
        rotationY2.setRepeatMode(ValueAnimator.REVERSE);
        rotationY2.setRepeatCount(ValueAnimator.INFINITE);
        rotationY2.start();
        ObjectAnimator rotationX3 = ObjectAnimator.ofFloat(viewBinding.btnRotationY3, "rotationX", 0, 360);
        rotationX3.setDuration(2000L);
        rotationX3.setRepeatMode(ValueAnimator.REVERSE);
        rotationX3.setRepeatCount(ValueAnimator.INFINITE);
        rotationX3.start();

        /**
         * 2.4.scaleX,scaleY缩放动画
         */
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(viewBinding.ivScale1, "scaleX", 1F, 0.5F, 1F, 1.5F);
        scaleX.setDuration(2000L);
        scaleX.setRepeatMode(ValueAnimator.REVERSE);
        scaleX.setRepeatCount(ValueAnimator.INFINITE);
        scaleX.start();
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(viewBinding.ivScale2, "scaleY", 1F, 0.5F, 1F, 1.5F);
        scaleY.setDuration(2000L);
        scaleY.setRepeatMode(ValueAnimator.REVERSE);
        scaleY.setRepeatCount(ValueAnimator.INFINITE);
        scaleY.start();

        /**
         * 2.5.alpha渐变动画 & 动画集合AnimatorSet
         */
        ObjectAnimator alpha = ObjectAnimator.ofFloat(viewBinding.ivAlpha1, "alpha", 1F, 0.1F);
        alpha.setDuration(2000L);
        alpha.setRepeatMode(ValueAnimator.REVERSE);
        alpha.setRepeatCount(ValueAnimator.INFINITE);
        alpha.start();
        //AnimatorSet 动画集合
        ImageView ivAnimatorSet = viewBinding.ivAnimatorSet;
        ObjectAnimator animatorCycleClone = ObjectAnimator.ofFloat(tvTranslateCycle, "translationX", 0, -10);
        animatorCycleClone.setDuration(1000L);
        animatorCycleClone.setRepeatMode(ValueAnimator.REVERSE);
        animatorCycleClone.setRepeatCount(ValueAnimator.INFINITE);
        animatorCycleClone.setInterpolator(new CycleInterpolator(7));
        ObjectAnimator rotationX3Clone = ObjectAnimator.ofFloat(ivAnimatorSet, "rotationX", 0, 360);
        rotationX3Clone.setDuration(2000L);
        rotationX3Clone.setRepeatMode(ValueAnimator.REVERSE);
        rotationX3Clone.setRepeatCount(ValueAnimator.INFINITE);
        ObjectAnimator scaleYClone = ObjectAnimator.ofFloat(ivAnimatorSet, "scaleY", 1F, 0.1F, 1F, 2.5F);
        scaleYClone.setDuration(2000L);
        scaleYClone.setRepeatMode(ValueAnimator.REVERSE);
        scaleYClone.setRepeatCount(ValueAnimator.INFINITE);
        ObjectAnimator alphaClone = ObjectAnimator.ofFloat(ivAnimatorSet, "alpha", 1F, 0.1F);
        alphaClone.setDuration(2000L);
        alphaClone.setRepeatMode(ValueAnimator.REVERSE);
        alphaClone.setRepeatCount(ValueAnimator.INFINITE);
        ivAnimatorSet.post(() -> {
            animatorSet = new AnimatorSet();
            //clone() 只能播放第一个动画...
//            animatorSet.playTogether(animatorCycle.clone(), rotationX3.clone(), scaleY.clone(), alpha.clone());
            animatorSet.playTogether(animatorCycleClone, rotationX3Clone, scaleYClone, alphaClone );
            animatorSet.setTarget(ivAnimatorSet);
            animatorSet.setDuration(2000L);
            animatorSet.start();
        });

        /**
         * 2.6.ofArgb颜色渐变动画
         */
        int red = Color.RED;
        int green = Color.GREEN;
        int blue = Color.BLUE;
        ObjectAnimator background = ObjectAnimator.ofArgb(viewBinding.itil7.getChildAt(1), "backgroundColor", red, green, red, blue);
        background.setDuration(5000L);
        background.setRepeatMode(ValueAnimator.REVERSE);
        background.setRepeatCount(ValueAnimator.INFINITE);
        background.start();
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
        valueAnimator = ValueAnimator.ofInt(maxHeight, 0);
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

    @Override
    protected void onResume() {
        super.onResume();
        if (valueAnimator != null) {
            valueAnimator.resume();
        }
        if (animatorSet != null) {
            animatorSet.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        valueAnimator.pause();
        animatorSet.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        valueAnimator.end();
        animatorSet.end();
    }
}