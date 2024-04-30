package com.actor.develop_helper.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.actor.develop_helper.bean.AppInfo;
import com.actor.myandroidframework.utils.LogUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin. <br />
 * 提供应用相关信息的工具类,返回: 包名,图标,应用名,uid,安装包大小,是否安装在SD卡,用户app/系统app <br />
 * <br />
 * Android系统安装app流程:
 * <ol>
 *     <li>默认第三方app安装路径: data/app(手机内存)</li>
 *     <li>安装过程其实是将apk拷贝到data/app目录下, 拷贝过去之后,安装成功!</li>
 *     <li>系统apk安装路径: system/app</li>
 *     <li>Root权限: 管理员权限, 能够访问和修改所有文件, 包括系统文件-->Root大师, 刷机大师, 一键Root, 越狱</li>
 * </ol>
 *
 * App移动到sdcard: <br />
 * <pre>
 *     &lt;manifest xmlns:android="http://schemas.android.com/apk/res/android"
 *         package="cn.example.mobilesafe12"
 *         android:installLocation="internalOnly|auto|preferExternal" /&gt;
 * </pre>
 * internalOnly: 默认值, 只能安装在手机内部存储中 <br />
 * auto: 优先安装在手机内存, 但可以移动到sdcard <br />
 * preferExternal: 优先安装在sdcard, 但可以移动到手机内存 <br />
 * <br />
 *
 * {@link null 注意:} 使用前, 需要添加权限:
 * <pre>
 *     &lt;uses-permission
 *         android:name="android.permission.QUERY_ALL_PACKAGES"
 *         tools:ignore="QueryAllPackagesPermission" /&gt;
 *     &lt;uses-permission android:name="com.android.permission.GET_INSTALLED_APPS" /&gt;
 * </pre>
 *
 * @version 1.0
 */
public class AppsInfoUtils {

    /**
     * 获取已安装应用
     * @param appType App类型: 0用户安装的App, 1系统App, 2全部
     */
    public static void getInstalledPackages(Context context, @IntRange(from = 0, to = 2) int appType,
                                            @Nullable OnGetAppsListener listener) {
        if (XXPermissions.isGranted(context, Permission.GET_INSTALLED_APPS)) {
            go2GetInstalledPackages(context, appType, listener);
        } else {
            XXPermissions.with(context).permission(Permission.GET_INSTALLED_APPS).request(new OnPermissionCallback() {
                @Override
                public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                    if (allGranted) {
                        go2GetInstalledPackages(context, appType, listener);
                    } else {
                        ToastUtils.showShort("请同意读取应用列表权限!");
                        XXPermissions.startPermissionActivity(context, Permission.GET_INSTALLED_APPS);
                    }
                }

                @Override
                public void onDenied(@NonNull List<String> permissions, boolean doNotAskAgain) {
                    ToastUtils.showShort("请同意读取应用列表权限!");
                    XXPermissions.startPermissionActivity(context, Permission.GET_INSTALLED_APPS);
                }
            });
        }
    }

    /**
     * 真正的获取列表
     * vivo X27(V1829T) (Android 10, API 29), 仅能读取到2~3个用户安装的应用, 需要卸载然后重新安装就可以了, 害我调试了好久...
     *
     * @param appType App类型: 0用户安装的App, 1系统App, 2全部
     */
    private static void go2GetInstalledPackages(Context context,
                                                @IntRange(from = 0, to = 2) int appType,
                                                @Nullable OnGetAppsListener listener) {
        ThreadUtils.executeBySingle(new ThreadUtils.SimpleTask<List<AppInfo>>() {
            @Override
            public List<AppInfo> doInBackground() throws Throwable {
                List<AppInfo> appInfos = new ArrayList<>();
                final PackageManager packageManager = context.getPackageManager();
                if (packageManager == null) return appInfos;
//                List<AppUtils.AppInfo> appsInfo = AppUtils.getAppsInfo();
//                if (appsInfo == null) return appInfos;
//                LogUtils.errorFormat("appsInfo: %d", appsInfo.size());
                //获取已安装的所有app
                List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
                if (installedPackages == null) return appInfos;
//                LogUtils.errorFormat("installedPackages: %d", installedPackages.size());

                for (PackageInfo packageInfo : installedPackages) {
                    ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                    int flags = applicationInfo.flags;  //当前应用的标记, 用于表示当前app是否具有某种特性
                    if (appType < 2 && appType != (flags & ApplicationInfo.FLAG_SYSTEM)) {
                        continue;
                    }

                    int uid = applicationInfo.uid;  //同一个app装在不同的手机上的uid不一样,手机里每个app的uid也不一样

                    AppInfo appInfo = new AppInfo();
                    inflateSignature(appInfo, packageManager, packageInfo.packageName);     //填充签名
                    appInfo.apkSourceDir = applicationInfo.sourceDir;                       //apk安装路径
                    appInfo.appName = applicationInfo.loadLabel(packageManager).toString(); //应用名称
                    appInfo.icon = applicationInfo.loadIcon(packageManager);                //图标
                    appInfo.isSdcard = (flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0;//手机内存/sdcard
                    appInfo.isSystemApp = (flags & ApplicationInfo.FLAG_SYSTEM) != 0;       //应用类型(用户/系统)
                    appInfo.packageName = packageInfo.packageName;                          //包名
                    appInfo.size = new File(appInfo.apkSourceDir).length();                 //安装包大小
                    appInfo.uid = uid;
                    appInfo.versionCode = packageInfo.versionCode;                          //版本号
                    appInfo.versionName = packageInfo.versionName;                          //版本名称
                    appInfos.add(appInfo);
                }
                return appInfos;
            }
            @Override
            public void onSuccess(List<AppInfo> result) {
                if (listener != null) listener.onGetApps(result);
            }
        });
    }

    /**
     * 获取签名
     */
    private static void inflateSignature(AppInfo appInfo, PackageManager packageManager, String packageName) {
        try {
//            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
//            return packageInfo.signatures[0].toCharsString();//获取几百~几千位的签名字符串
            List<String> appSignaturesMD5 = AppUtils.getAppSignaturesMD5(packageName);
            List<String> appSignaturesSHA1 = AppUtils.getAppSignaturesSHA1(packageName);
            List<String> appSignaturesSHA256 = AppUtils.getAppSignaturesSHA256(packageName);
            if (appSignaturesMD5.size() > 0) appInfo.apkMd5Sign = appSignaturesMD5.get(0);
            if (appSignaturesSHA1.size() > 0) appInfo.apkSha1Sign = appSignaturesSHA1.get(0);
            if (appSignaturesSHA256.size() > 0) appInfo.apkSha256Sign = appSignaturesSHA256.get(0);
        } catch (Exception e) {
            LogUtils.error(packageName);
            e.printStackTrace();
        }
    }

    public interface OnGetAppsListener {
        void onGetApps(@NonNull List<AppInfo> appInfos);
    }
}
