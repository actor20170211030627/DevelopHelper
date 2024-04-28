package com.actor.develop_helper.utils;

import android.accessibilityservice.AccessibilityService;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;

import com.actor.myandroidframework.utils.ConfigUtils;

import java.io.File;

/**
 * Created by popfisher on 2017/7/11.
 */
public class AccessibilityUtils {

    private static final Application app = ConfigUtils.APPLICATION;

    private AccessibilityUtils() {
        throw new RuntimeException(getClass().getName() + " can not be new!");
    }

    /**
     * 判断是否有辅助功能权限
     */
    public static boolean isAccessibilitySettingsOn(Class<? extends AccessibilityService> service) {
        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(app.getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        if (accessibilityEnabled == 1) {
            String packageName = app.getPackageName();
            //原来是这么写的:getClass().getCanonicalName()
            String serviceStr = packageName + File.separator + service.getName();
            System.out.println("service:" + serviceStr);
            TextUtils.SimpleStringSplitter stringSplitter = new TextUtils.SimpleStringSplitter(':');

            String settingValue = Settings.Secure.getString(app.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                stringSplitter.setString(settingValue);
                while (stringSplitter.hasNext()) {
                    String accessabilityService = stringSplitter.next();
                    System.out.println("accessabilityService: " + accessabilityService);
                    if (accessabilityService.equalsIgnoreCase(serviceStr)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 跳转到系统设置页面开启辅助功能
     */
    public static void openAccessibility(Context context) {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        context.startActivity(intent);
    }
}
