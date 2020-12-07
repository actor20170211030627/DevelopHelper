package com.actor.androiddevelophelper.service;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

import com.actor.androiddevelophelper.R;
import com.actor.androiddevelophelper.utils.AccessibilityUtils;
import com.actor.myandroidframework.utils.LogUtils;
import com.actor.myandroidframework.utils.TextUtils2;
import com.blankj.utilcode.util.ToastUtils;

/**
 * Description: 查看"包名&类名"服务, 辅助功能
 * Company    : 公司名称
 * Date       : 2017/2/23 on 21:31.
 */
public class ViewPackageAndClassNameService extends AccessibilityService {

    private String name;
    protected String channelId = toString();
    protected int id = 1;

    @Override
    public void onCreate() {
        super.onCreate();

        //适配8.0
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            name = getResources().getString(R.string.app_name);
            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(channelId, name, NotificationManager.IMPORTANCE_HIGH);
            nm.createNotificationChannel(channel);
            Notification notification = new Notification.Builder(getApplicationContext(), channelId).build();
            startForeground(id, notification);
        }
        AccessibilityUtils.onCreate(this);
    }

    /**
     * 连接服务后,一般是在授权成功后会接收到
     * 系统会在成功连接上你的服务的时候调用这个方法，在这个方法里你可以做一下初始化工作，
     * 例如设备的声音震动管理，也可以调用setServiceInfo()进行配置工作。
     */
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        // 通过代码可以动态配置，但是可配置项少一点
//        AccessibilityServiceInfo accessibilityServiceInfo = new AccessibilityServiceInfo();
//        accessibilityServiceInfo.eventTypes = AccessibilityEvent.TYPE_WINDOWS_CHANGED
//                | AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
//                | AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED
//                | AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;
//        accessibilityServiceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;
//        accessibilityServiceInfo.notificationTimeout = 0;
//        accessibilityServiceInfo.flags = AccessibilityServiceInfo.DEFAULT;
//        setServiceInfo(accessibilityServiceInfo);
    }

    /**
     * 辅助功能相关事件发生后的回调,如触发了通知栏变化、界面变化等
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event == null) {
            return;
        }
        String packageName = String.valueOf(event.getPackageName());//当前页面包名
        String className = String.valueOf(event.getClassName());
        String info = TextUtils2.getStringFormat("包名: %s%n类名: %s", packageName, className);
        LogUtils.error(info, true);
        ToastUtils.showShort(info);
    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        //接收按键事件
        return super.onKeyEvent(event);
    }

    @Override
    public void onInterrupt() {
        //辅助功能服务中断，如授权关闭或者将服务杀死
    }

    @Override
    public void onDestroy() {
        AccessibilityUtils.onDestroy();
        super.onDestroy();
    }
}
