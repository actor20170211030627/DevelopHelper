package com.actor.androiddevelophelper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.actor.androiddevelophelper.R;
import com.actor.androiddevelophelper.databinding.ActivityAnimationsBinding;

/**
 * description: 帧动画 & 补间动画 & 属性动画
 * company    :
 * @author    : ldf
 * date       : 2021/8/28 on 21:27
 *
 * TODO: 2021/8/28 https://blog.csdn.net/hust_twj/article/details/78587989
 *      View动画中 android:pivotX 和 android:pivotY 属性的含义
 *      android:transformPivotX="5dp"???
 */
public class AnimationsActivity extends BaseActivity<ActivityAnimationsBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("帧动画 & 补间动画 & 属性动画");
    }

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_animation_drawable:
                startActivity(new Intent(this, AnimationDrawableActivity.class));
                break;
            case R.id.btn_animation:
                startActivity(new Intent(this, AnimationActivity.class));
                break;
            case R.id.btn_object_animator:
                startActivity(new Intent(this, AnimatorActivity.class));
                break;
            case R.id.btn_object_animator_path:
                startActivity(new Intent(this, AnimatorPathActivity.class));
                break;
            default:
                break;
        }
    }
}