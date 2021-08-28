package com.actor.androiddevelophelper.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import com.actor.androiddevelophelper.databinding.ActivityAnimationDrawableBinding;

/**
 * description: 帧动画
 * company    :
 * @author    : ldf
 * date       : 2021/8/28 on 21:29
 */
public class AnimationDrawableActivity extends BaseActivity<ActivityAnimationDrawableBinding> {

    //1.帧动画
    private AnimationDrawable animationDrawableGirl, ivAnimationDrawableAudioWave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("帧动画");

        /**
         * 1.帧动画
         */
        animationDrawableGirl = (AnimationDrawable) viewBinding.ivAnimationDrawableGirl.getDrawable();
        ivAnimationDrawableAudioWave = (AnimationDrawable) viewBinding.viewAnimationDrawableAudioWave.getBackground();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //1.开始播放帧动画
        animationDrawableGirl.start();
        ivAnimationDrawableAudioWave.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //1.停止播放帧动画
        animationDrawableGirl.stop();
        ivAnimationDrawableAudioWave.stop();
    }
}