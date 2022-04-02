package com.actor.develop_helper.activity;

import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.actor.develop_helper.Global;
import com.actor.develop_helper.R;
import com.actor.develop_helper.animation.RotateYAnimation;
import com.actor.develop_helper.databinding.ActivityAnimationBinding;

/**
 * description: Animation动画
 * company    :
 * @author    : ldf
 * date       : 2021/8/28 on 16:18
 */
public class AnimationActivity extends BaseActivity<ActivityAnimationBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("2.补间动画(平移,旋转,缩放,渐变)");
        /**
         * 2.补间动画
         * 3.旋转动画
         * 4.缩放动画
         * 5.渐变动画
         * 5.AnimationSet
         * 6.自定义动画
         */
        startAnimation();
    }

    /**
     * 2.补间动画
     */
    private void startAnimation() {
        /**
         * 2.1.1.平移动画(水平左移动画): x轴方向从0平移到-1(宽度1倍)
         * @param int fromXType     相对谁平移
         * @param float fromXValue  x轴开始位置比例, 0 ~ 1
         * @param int toXType       终点相对谁平移
         * @param float toXValue    x轴终点位置比例, 0 ~ -1
         * @param int fromYType     y轴
         * @param float fromYValue
         * @param int toYType
         * @param float toYValue
         */
        TranslateAnimation translateAnimationLeft = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0F,
                Animation.RELATIVE_TO_SELF, -1F, Animation.RELATIVE_TO_SELF, 0F,
                Animation.RELATIVE_TO_SELF, 0F);
        //动画持续时间
        translateAnimationLeft.setDuration(500L);
        //插值器
        translateAnimationLeft.setInterpolator(new AccelerateInterpolator());
        //动画结束后保持原位
        translateAnimationLeft.setFillAfter(true);
        //延时多长时间之后再启动动画
        translateAnimationLeft.setStartOffset(200L);
        //播放完成后, 从结束开始向前重新运行
        translateAnimationLeft.setRepeatMode(Animation.REVERSE);
        //无限播放
        translateAnimationLeft.setRepeatCount(Animation.INFINITE);
        //开始动画
        viewBinding.tvTranslateHorizontal.startAnimation(translateAnimationLeft);

        /**
         * 2.1.2.平移动画,从anim目录加载xml(垂直上移): y轴方向从1平移到0
         * 从xml中读取
         */
        Animation translateAnimationUp = AnimationUtils.loadAnimation(this, R.anim.translate_up);
        viewBinding.tvTranslateVertical.startAnimation(translateAnimationUp);

        /**
         * 2.1.3.平移动画,抖动效果
         */
        TranslateAnimation cycleAnimation = new TranslateAnimation(0F, Global.DP1 * 5F, 0F, 0F);
        cycleAnimation.setDuration(1000L);
        cycleAnimation.setInterpolator(new CycleInterpolator(7F));
        cycleAnimation.setFillAfter(true);
        cycleAnimation.setStartOffset(400L);
        cycleAnimation.setRepeatCount(Animation.INFINITE);
        viewBinding.tvTranslateCycle.startAnimation(cycleAnimation);


        /**
         * 2.2.旋转动画
         */
        RotateAnimation rotateAnimation1 = new RotateAnimation(0F, 180F);
        rotateAnimation1.setDuration(500L);
        rotateAnimation1.setStartOffset(200L);
        rotateAnimation1.setFillAfter(true);
        rotateAnimation1.setRepeatMode(Animation.REVERSE);
        rotateAnimation1.setRepeatCount(Animation.INFINITE);
        viewBinding.ivRotate1.startAnimation(rotateAnimation1);

        RotateAnimation rotateAnimation2 = new RotateAnimation(0F, 180F, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
        rotateAnimation2.setDuration(500L);
        rotateAnimation2.setFillAfter(true);
        rotateAnimation2.setStartOffset(200L);
        rotateAnimation2.setRepeatMode(Animation.REVERSE);
        rotateAnimation2.setRepeatCount(Animation.INFINITE);
        viewBinding.ivRotate2.startAnimation(rotateAnimation2);


        /**
         * 2.3.缩放动画
         */
        ScaleAnimation scaleAnimation1 = new ScaleAnimation(0.5F, 1.2F, 1F, 0.5F);
        scaleAnimation1.setDuration(500L);
        scaleAnimation1.setFillAfter(true);
        scaleAnimation1.setStartOffset(200L);
        scaleAnimation1.setRepeatMode(Animation.REVERSE);
        scaleAnimation1.setRepeatCount(Animation.INFINITE);
        viewBinding.ivScale1.startAnimation(scaleAnimation1);

        ScaleAnimation scaleAnimation2 = new ScaleAnimation(1.2F, 0.5F, 0.5F, 1F, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
        scaleAnimation2.setDuration(500L);
        scaleAnimation2.setFillAfter(true);
        scaleAnimation2.setStartOffset(200L);
        scaleAnimation2.setRepeatMode(Animation.REVERSE);
        scaleAnimation2.setRepeatCount(Animation.INFINITE);
        viewBinding.ivScale2.startAnimation(scaleAnimation2);


        /**
         * 2.4.渐变动画 & 2.5.动画集合AnimationSet
         */
        AlphaAnimation alphaAnimation = new AlphaAnimation(1F, 0.2F);
        alphaAnimation.setDuration(500L);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setStartOffset(200L);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        viewBinding.ivAlpha1.startAnimation(alphaAnimation);

        AnimationSet animationSet = new AnimationSet(false);
        //加上后, 会影响上方的水平移动效果
//        animationSet.addAnimation(translateAnimationLeft);
        animationSet.addAnimation(rotateAnimation2);
        animationSet.addAnimation(scaleAnimation2);
        animationSet.addAnimation(alphaAnimation);
        viewBinding.ivAlpha2.startAnimation(animationSet);


        /**
         * 2.6.自定义Animation
         */
        RotateYAnimation rotateYAnimation = new RotateYAnimation();
        rotateYAnimation.setFillAfter(true);
        rotateYAnimation.setFillAfter(true);
        rotateYAnimation.setDuration(2000L);
        rotateYAnimation.setRepeatMode(Animation.REVERSE);
        rotateYAnimation.setRepeatCount(Animation.INFINITE);
        viewBinding.ivAnimationCustom.startAnimation(rotateYAnimation);
        viewBinding.btnAnimationCustom.startAnimation(rotateYAnimation);
    }
}