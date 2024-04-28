package com.actor.develop_helper.service;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.view.accessibility.AccessibilityEvent;

import com.actor.develop_helper.R;
import com.actor.myandroidframework.utils.LogUtils;
import com.actor.myandroidframework.utils.TextUtils2;
import com.blankj.utilcode.util.ToastUtils;

/**
 * Description: 查看"包名&类名"服务, 辅助功能
 * Company    : 公司名称
 * Date       : 2017/2/23 on 21:31.
 */
public class ViewPackageAndClassNameService extends AccessibilityService {

    protected String channelId = toString();
    protected int id = 1;

    @Override
    public void onCreate() {
        super.onCreate();

        //适配8.0
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String name = getResources().getString(R.string.app_name);
            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(channelId, name, NotificationManager.IMPORTANCE_HIGH);
            nm.createNotificationChannel(channel);
            Notification notification = new Notification.Builder(getApplicationContext(), channelId).build();
            startForeground(id, notification);
        }
    }


    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
    }

    /**
     * 辅助功能相关事件发生后的回调,如触发了通知栏变化、界面变化等
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event == null) return;
        String packageName = String.valueOf(event.getPackageName());
        String className = String.valueOf(event.getClassName());
        String info = TextUtils2.getStringFormat("包名: %s, 类名: %s", packageName, className);
        LogUtils.error(info);
        ToastUtils.showShort(info);
    }

    @Override
    public void onInterrupt() {
        //辅助功能服务中断，如授权关闭或者将服务杀死
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
