package com.actor.develop_helper.service;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityRecord;
import android.widget.TextView;

import com.actor.develop_helper.R;
import com.actor.myandroidframework.utils.LogUtils;
import com.actor.myandroidframework.utils.TextUtils2;
import com.actor.myandroidframework.utils.toaster.ToasterUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.GsonUtils;

import java.util.List;
import java.util.TreeMap;

/**
 * Description: 查看"包名&类名"服务, 辅助功能
 * Company    : 公司名称
 * Date       : 2017/2/23 on 21:31.
 */
public class ViewPackageAndClassNameService extends AccessibilityService {

    protected String channelId = toString();
    protected int id = 1;
    protected TreeMap<String, Object> treeSet = new TreeMap<>();
    protected final String infoSelf = TextUtils2.getStringFormat("包名: %s, 类名: %s", AppUtils.getAppPackageName(), TextView.class.getName());

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
        //{"mEventType":32,"mSealed":true,"mSourceNodeId":-4294967244}
//        String json = GsonUtils.toJson(event);
//        LogUtils.error(json);

        CharSequence packageName = event.getPackageName();
        CharSequence className = event.getClassName();
        String info = TextUtils2.getStringFormat("包名: %s, 类名: %s", packageName, className);

        //屏蔽掉自己 (主要是屏蔽掉下方的Toast)
        if (TextUtils.equals(info, infoSelf)) {
            return;
        }
//        List<CharSequence> text1 = event.getText();
//        if (!text1.isEmpty()) {
//            CharSequence charSequence = text1.get(0);
//            //if就是下方的Toast, 就不要再toast了
//            if (!TextUtils.isEmpty(charSequence) && charSequence.toString().contains(infoSelf)) {
//                return;
//            }
//        }

//        LogUtils.error(info);
//        ToastUtils.showShort(info);
        ToasterUtils.show(info);    //也会打印

        int describeContents = event.describeContents();
        boolean checked = event.isChecked();
        boolean enabled = event.isEnabled();
        boolean password = event.isPassword();
        boolean fullScreen = event.isFullScreen();
        boolean scrollable = event.isScrollable();
        LogUtils.errorFormat("describeContents=%d, checked=%b, enabled=%b, password=%b, fullScreen=%b, scrollable=%b",
                describeContents, checked, enabled, password, fullScreen, scrollable);

        treeSet.clear();
        int action = event.getAction();
        CharSequence beforeText = event.getBeforeText();
        int contentChangeTypes = event.getContentChangeTypes();
        CharSequence contentDescription = event.getContentDescription();
        int currentItemIndex = event.getCurrentItemIndex();
        long eventTime = event.getEventTime();
        int eventType = event.getEventType();
        int fromIndex = event.getFromIndex();
        int itemCount = event.getItemCount();
        int movementGranularity = event.getMovementGranularity();
        int maxScrollX = event.getMaxScrollX();
        int maxScrollY = event.getMaxScrollY();
        Parcelable parcelableData = event.getParcelableData();
        int recordCount = event.getRecordCount();
        treeSet.put("action", action);
        treeSet.put("beforeText", beforeText);
        treeSet.put("contentChangeTypes", contentChangeTypes);
        treeSet.put("contentDescription", contentDescription);
        treeSet.put("currentItemIndex", currentItemIndex);
        treeSet.put("eventTime", eventTime);
        treeSet.put("eventType", eventType);    //TYPE_WINDOW_STATE_CHANGED, TYPE_VIEW_CLICKED
        treeSet.put("fromIndex", fromIndex);
        treeSet.put("itemCount", itemCount);
        treeSet.put("movementGranularity", movementGranularity);
        treeSet.put("maxScrollX", maxScrollX);
        treeSet.put("maxScrollY", maxScrollY);
        treeSet.put("parcelableData", parcelableData);
        treeSet.put("recordCount", recordCount);

        for (int i = 0; i < recordCount; i++) {
            AccessibilityRecord record = event.getRecord(i);
            treeSet.put("record" + i, record);
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            int scrollDeltaX = event.getScrollDeltaX();
            int scrollDeltaY = event.getScrollDeltaY();
            treeSet.put("scrollDeltaX", scrollDeltaX);
            treeSet.put("scrollDeltaY", scrollDeltaY);
        }
        AccessibilityNodeInfo source = event.getSource();
        int scrollX = event.getScrollX();
        int scrollY = event.getScrollY();
        List<CharSequence> text = event.getText();  //View的文字
        int toIndex = event.getToIndex();
        treeSet.put("source", source);
        treeSet.put("scrollX", scrollX);
        treeSet.put("scrollY", scrollY);
        treeSet.put("text", GsonUtils.toJson(text));
        treeSet.put("toIndex", toIndex);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            int windowChanges = event.getWindowChanges();
            treeSet.put("windowChanges", windowChanges);
        }
        int windowId = event.getWindowId();
        treeSet.put("windowId", windowId);

//        event.recycle();
        String toString = event.toString();
        treeSet.put("toString", toString);
        com.blankj.utilcode.util.LogUtils.json(treeSet);
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
