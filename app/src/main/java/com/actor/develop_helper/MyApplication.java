package com.actor.develop_helper;

import androidx.annotation.Nullable;

import com.actor.cpptest.ConstUtils;
import com.actor.develop_helper.utils.AppInfoProvider;
import com.actor.myandroidframework.application.ActorApplication;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Description: 类的描述
 * Author     : ldf
 * Date       : 2019/8/29 on 00:17
 *
 * @version 1.0
 */
public class MyApplication extends ActorApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        AppInfoProvider.init(this);
        ConstUtils.jniInit(this, isAppDebug());
    }

    @Nullable
    @Override
    protected OkHttpClient.Builder configOkHttpClientBuilder(OkHttpClient.Builder builder) {
        return builder.connectTimeout(30_000L, TimeUnit.MILLISECONDS)
                .readTimeout(30_000L, TimeUnit.MILLISECONDS)
                .writeTimeout(30_000L, TimeUnit.MILLISECONDS);
    }

    @Override
    protected void onUncaughtException(Throwable e) {
//        System.exit(-1);
    }
}
