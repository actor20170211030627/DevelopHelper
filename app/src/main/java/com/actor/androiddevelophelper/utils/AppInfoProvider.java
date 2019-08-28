package com.actor.androiddevelophelper.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.blankj.utilcode.util.EncryptUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin.
 * 提供应用相关信息的工具类,返回: 包名,图标,应用名,uid,安装包大小,是否安装在SD卡,用户app/系统app
 *     1.获取已安装的app的是所有应用的信息(packageName,icon,name,size,isSdcard,isUserApp)
 *
 * Android系统安装app流程:
 * 1. 默认第三方app安装路径: data/app(手机内存)
 * 2. 安装过程其实是将apk拷贝到data/app目录下, 拷贝过去之后,安装成功!
 * 3. 系统apk安装路径: system/app
 * 4. Root权限: 管理员权限, 能够访问和修改所有文件, 包括系统文件-->Root大师, 刷机大师, 一键Root, 越狱
 *
 * App移动到sdcard:
 * <manifest xmlns:android="http://schemas.android.com/apk/res/android"
 * package="cn.itcast.mobilesafe12" android:installLocation="internalOnly | auto | preferExternal">
 *
 * internalOnly: 默认值, 只能安装在手机内部存储中
 * auto: 优先安装在手机内存, 但可以移动到sdcard
 * preferExternal: 优先安装在sdcard, 但可以移动到手机内存
 *
 * 用法:
 * 1.在第一个activity初始化(不能在application): {@link #init(Context)}
 * 2.获取App信息: {@link #getAppInfos()}, 如果获取的是空的list, 应该是还没初始化完成, 自己处理
 *
 * @version 1.0
 */
public class AppInfoProvider {

    private static final List<AppInfo> appInfos = new ArrayList<>();

    public static void init(Context context) {
        appInfos.clear();
        final PackageManager packageMamager = context.getPackageManager();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //获取已安装的所有app
                List<PackageInfo> installedPackages = packageMamager.getInstalledPackages(0);
                for (PackageInfo packageInfo : installedPackages) {
                    ApplicationInfo applicationInfo = packageInfo.applicationInfo;//当前app的应用信息
                    int flags = applicationInfo.flags;//当前应用的标记, 用于表示当前app是否具有某种特性
                    int uid = applicationInfo.uid;//同一个app装在不同的手机上的uid不一样,手机里每个app的uid也不一样

                    AppInfo appInfo = new AppInfo();
                    appInfo.apkMd5Sign = getSignature(packageMamager, packageInfo.packageName);//获取签名
                    appInfo.apkSourceDir = applicationInfo.sourceDir;//apk安装路径
                    appInfo.appName = applicationInfo.loadLabel(packageMamager).toString();//应用名称
                    appInfo.icon = applicationInfo.loadIcon(packageMamager);//图标
                    appInfo.isSdcard = (flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0;//手机内存/sdcard
                    appInfo.isSystemApp = (flags & ApplicationInfo.FLAG_SYSTEM) != 0;//应用类型(用户/系统)
                    appInfo.packageName = packageInfo.packageName;//包名
                    appInfo.size = new File(appInfo.apkSourceDir).length();//安装包大小
                    appInfo.uid = uid;
                    appInfo.versionCode = packageInfo.versionCode;//版本号
                    appInfo.versionName = packageInfo.versionName;//版本名称
                    appInfos.add(appInfo);
                }
            }
        }).start();
    }

    public static List<AppInfo> getAppInfos() {
        return appInfos;
    }

    /**
     * 获取签名
     */
    public static String getSignature(PackageManager packageMamager, String packageName) {
        try {
            PackageInfo packageInfo = packageMamager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
//            return packageInfo.signatures[0].toCharsString();//获取几百位的签名字符串
            /**
            Signature[] sign = packageInfo.signatures;
            if (sign != null) {
                for (Signature tmp : sign) {
                    //signature += getMessageDigest(tmp.toByteArray());//微信demo用法
                    signature += getMessageDigest(tmp.toCharsString().getBytes());//获取签名的MD5值tmp.toByteArray()
                }
            }
             */
            return EncryptUtils.encryptMD5ToString(packageInfo.signatures[0].toCharsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
