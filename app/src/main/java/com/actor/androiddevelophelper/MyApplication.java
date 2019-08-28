package com.actor.androiddevelophelper;

import android.app.Application;

import com.actor.androiddevelophelper.utils.AppInfoProvider;

/**
 * Description: 类的描述
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/8/29 on 00:17
 *
 * @version 1.0
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppInfoProvider.init(this);
    }
}
