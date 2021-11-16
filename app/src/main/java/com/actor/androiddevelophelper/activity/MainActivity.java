package com.actor.androiddevelophelper.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import com.actor.androiddevelophelper.R;
import com.actor.androiddevelophelper.databinding.ActivityMainBinding;
import com.actor.androiddevelophelper.service.ViewPackageAndClassNameService;
import com.actor.androiddevelophelper.utils.AccessibilityUtils;
import com.actor.androiddevelophelper.utils.CheckUpdateUtils;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ServiceUtils;
import com.develophelper.android5.activity.Android5MainActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewBinding.tvIpV4.setText(getStringFormat("IP(v4): %s", NetworkUtils.getIPAddress(true)));
        viewBinding.tvIpV6.setText(getStringFormat("IP(v6): %s", NetworkUtils.getIPAddress(false)));

        viewBinding.tvVersion.setText(getStringFormat("VersionName: %s(VersionCode: %d)",
                AppUtils.getAppVersionName(), AppUtils.getAppVersionCode()));
        new CheckUpdateUtils().check(this);
    }

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_open_development://打开开发者选项
                startDevelopMent();
                break;
            case R.id.btn_open_develop://打开"开发"
                boolean success = ActivityUtils
                        .startActivity(new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS));
                if (!success) {
                    toast("打开失败...");
                }
                break;
            case R.id.btn_open_develop1://部分小米手机采用这种方式跳转
                boolean b = ActivityUtils
                        .startActivity(new Intent("com." + Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS));
                if (!b) {
                    toast("打开失败...");
                }
                break;
            case R.id.btn_calculate_constraintlayout://计算约束布局偏移率
                startActivity(new Intent(this, CalculateConstrailtLayoutActivity.class));
                break;
            case R.id.btn_view_system_icon://查看系统资源图标
                startActivity(new Intent(this, ViewSystemIconActivity.class));
                break;
            case R.id.btn_app_info://查看App信息(包括Md5&Sha1&Sha256签名)
                startActivity(new Intent(this, AppInfoActivity.class));
                break;
            case R.id.btn_view_page_info://查看当前页面包名&类名(需打开辅助功能)
                if (!isAccessibilitySettingsOn()) {
                    AccessibilityUtils.openAccessibility(activity);
                    toast("请开启辅助功能");
                } else {
                    toast("辅助功能已开启");
                }
                boolean appLockServiceIsRunning = ServiceUtils.isServiceRunning(ViewPackageAndClassNameService.class);
                if (!appLockServiceIsRunning) {
                    startForegroundService(new Intent(activity, ViewPackageAndClassNameService.class));
                }
                break;
            case R.id.btn_stop_service://停止服务
                boolean b1 = stopService(new Intent(activity, ViewPackageAndClassNameService.class));
                toast("辅助功能停止失败, 请自己杀后台...");
                break;
            case R.id.btn_glide://Glide使用
                startActivity(new Intent(this, GlideExampleActivity.class));
                break;
            case R.id.btn_canvas://Canvas绘制
                startActivity(new Intent(this, CanvasDrawActivity.class));
                break;
            case R.id.btn_encrypt://Encrypt加密解密
                startActivity(new Intent(this, EncryptActivity.class));
                break;
            case R.id.btn_animation://Animation动画
                startActivity(new Intent(this, AnimationsActivity.class));
                break;
            case R.id.btn_android5://android 5.0新特性
                startActivity(new Intent(this, Android5MainActivity.class));
                break;
            case R.id.btn_expandable_item://分组的伸缩栏(ExpandableItemAdapter)
                startActivity(new Intent(this, ExpandableItemActivity.class));
                break;
            case R.id.btn_github_host://获取 Github Host(用于配置电脑host,解决Github图片等无法访问等问题.)
                startActivity(new Intent(this, GithubHostActivity.class));
                break;
            default:
                break;
        }
    }

    /**
     * 打开开发者模式界面
     */
    private void startDevelopMent() {
        ComponentName componentName = new ComponentName("com.android.settings", "com.android.settings.DevelopmentSettings");
        boolean success = ActivityUtils.startActivity(new Intent("android.intent.action.View")
                .setComponent(componentName));
        if (!success) {
            toast("打开失败...");
        }
    }

    private boolean isAccessibilitySettingsOn() {
        return AccessibilityUtils.isAccessibilitySettingsOn(ViewPackageAndClassNameService.class);
    }
}
