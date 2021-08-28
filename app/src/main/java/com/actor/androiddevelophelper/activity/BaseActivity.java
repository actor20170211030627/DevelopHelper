package com.actor.androiddevelophelper.activity;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.actor.myandroidframework.activity.ActorBaseActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Description: 类的描述
 * Author     : ldf
 * Date       : 2019-8-28 on 09:48
 *
 * @version 1.0
 */
public class BaseActivity<VB extends ViewBinding> extends ActorBaseActivity {

    /**
     * 如果不传入泛型, viewBinding = null;
     */
    protected VB viewBinding;

    //硬盘缓存
//    protected CacheDiskUtils aCache = MyApplication.instance.aCache;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Class<VB> cls = (Class<VB>) ((ParameterizedType) type).getActualTypeArguments()[0];
            try {
                Method inflate = cls.getDeclaredMethod("inflate", LayoutInflater.class);
                viewBinding = (VB) inflate.invoke(null, getLayoutInflater());
                setContentView(viewBinding.getRoot());
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
