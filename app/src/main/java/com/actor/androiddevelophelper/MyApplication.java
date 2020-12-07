package com.actor.androiddevelophelper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.actor.androiddevelophelper.utils.AppInfoProvider;
import com.actor.myandroidframework.application.ActorApplication;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Description: 类的描述
 * Author     : 李大发
 * Date       : 2019/8/29 on 00:17
 *
 * @version 1.0
 */
public class MyApplication extends ActorApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        AppInfoProvider.init(this);
    }

    @Nullable
    @Override
    protected OkHttpClient.Builder configOkHttpClientBuilder(OkHttpClient.Builder builder) {
        return builder.connectTimeout(30_000L, TimeUnit.MILLISECONDS)
                .readTimeout(30_000L, TimeUnit.MILLISECONDS)
                .writeTimeout(30_000L, TimeUnit.MILLISECONDS);
    }

    @NonNull
    @Override
    protected String getBaseUrl() {
        return "https://gitee.com";
    }

    @Override
    protected void onUncaughtException(Thread thread, Throwable e) {
//        System.exit(-1);
    }
}
