package com.actor.androiddevelophelper;

import android.support.annotation.NonNull;

import com.actor.androiddevelophelper.utils.AppInfoProvider;
import com.actor.myandroidframework.application.ActorApplication;
import com.zhouyou.http.EasyHttp;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Description: 类的描述
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
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

    @Override
    protected void configEasyHttp(EasyHttp easyHttp) {
        easyHttp.setConnectTimeout(60_000L)
                .setReadTimeOut(60_000L)
                .setWriteTimeOut(60_000L);
        //配置张鸿洋的OkHttpUtils
        OkHttpUtils.initClient(EasyHttp.getOkHttpClient());
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
