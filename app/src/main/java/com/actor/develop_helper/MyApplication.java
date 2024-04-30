package com.actor.develop_helper;

import com.actor.cpptest.ConstUtils;
import com.actor.myandroidframework.application.ActorApplication;
import com.actor.myandroidframework.utils.easyhttp.EasyHttpConfigUtils;
import com.actor.myandroidframework.utils.okhttputils.OkHttpConfigUtils;

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
        ConstUtils.jniInit(this, isAppDebug());

        OkHttpClient.Builder builder = EasyHttpConfigUtils.initOkHttp(isAppDebug());
        OkHttpClient okHttpClient = OkHttpConfigUtils.addLogInterceptor(builder, isAppDebug());
        EasyHttpConfigUtils.init(false, "http://www.baidu.com", okHttpClient);
    }

    @Override
    protected void onUncaughtException(Throwable e) {
        super.onUncaughtException(e);
    }
}
