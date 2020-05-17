package com.actor.androiddevelophelper.activity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import com.actor.androiddevelophelper.R;
import com.actor.androiddevelophelper.service.CheckUpdateService;
import com.actor.androiddevelophelper.service.ViewPackageAndClassNameService;
import com.actor.androiddevelophelper.utils.AccessibilityUtils;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ServiceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_ip_v4)
    TextView tvIpV4;
    @BindView(R.id.tv_ip_v6)
    TextView tvIpV6;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        tvIpV4.setText("IP(v4): " + NetworkUtils.getIPAddress(true));
        tvIpV6.setText("IP(v6): " + NetworkUtils.getIPAddress(false));

        tvVersion.setText(getStringFormat("VersionName: %s(VersionCode: %d)",
                AppUtils.getAppVersionName(), AppUtils.getAppVersionCode()));
        startService(new Intent(this, CheckUpdateService.class));
    }

    @OnClick({R.id.btn_open_development, R.id.btn_open_develop, R.id.btn_open_develop1,
            R.id.btn_calculate_constraintlayout, R.id.btn_view_system_icon, R.id.btn_app_info,
            R.id.btn_view_page_info, R.id.btn_stop_service})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_open_development:
                //打开开发者选项
                startDevelopMent();
                break;
            case R.id.btn_open_develop:
                //打开"开发"
                boolean success = ActivityUtils
                        .startActivity(new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS));
                if (!success) {
                    toast("打开失败...");
                }
                break;
            case R.id.btn_open_develop1:
                //部分小米手机采用这种方式跳转
                boolean b = ActivityUtils
                        .startActivity(new Intent("com.android.settings.APPLICATION_DEVELOPMENT_SETTINGS"));
                if (!b) {
                    toast("打开失败...");
                }
                break;
            case R.id.btn_calculate_constraintlayout:
                //计算约束布局偏移量
                startActivity(new Intent(this, CalculateConstrailtLayoutActivity.class));
                break;
            case R.id.btn_view_system_icon:
                //查看系统资源图标
                startActivity(new Intent(this, ViewSystemIconActivity.class));
                break;
            case R.id.btn_app_info:
                //查看App信息(包括签名)
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
                stopService(new Intent(activity, ViewPackageAndClassNameService.class));
                toast("辅助功能停止失败, 请自己杀后台...");
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
        Intent intent = new Intent();
        intent.setComponent(componentName);
        intent.setAction("android.intent.action.View");
        boolean success = ActivityUtils.startActivity(intent);
        if (!success) {
            toast("打开失败...");
        }
    }

    private boolean isAccessibilitySettingsOn() {
        return AccessibilityUtils.isAccessibilitySettingsOn(ViewPackageAndClassNameService.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, CheckUpdateService.class));
    }
}
