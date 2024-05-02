package com.actor.develop_helper.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;

import com.actor.develop_helper.R;
import com.actor.develop_helper.adapter.MyAppsAdapter;
import com.actor.develop_helper.bean.AppInfo;
import com.actor.develop_helper.databinding.ActivityAppInfoBinding;
import com.actor.develop_helper.utils.AppsInfoUtils;
import com.actor.myandroidframework.utils.LogUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.lxj.xpopup.XPopup;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 查看app信息(包括Md5&Sha1&Sha256签名)
 * Author     : ldf
 * Date       : 2019/8/28 on 22:10
 */
public class AppInfoActivity extends BaseActivity<ActivityAppInfoBinding> {

    private final List<AppInfo> userApps   = new ArrayList<>();//用户app
    private final List<AppInfo> systemApps = new ArrayList<>();//系统app
    private final List<AppInfo> nowAllApps = new ArrayList<>();//搜索前,目前应该显示的全部app
    private final List<AppInfo> searchApps = new ArrayList<>();//搜索后的app
    private       MyAppsAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("查看app信息(包括Md5&Sha1&Sha256签名)");

        viewBinding.swipeRefreshLayout.setRefreshing(true);
        viewBinding.swipeRefreshLayout.setOnRefreshListener(() -> {
            viewBinding.swipeRefreshLayout.setRefreshing(false);
        });
        //
        viewBinding.bsAppType.setOnItemSelectedListener((parent, view, position, id) -> search());
        viewBinding.rvApps.setAdapter(myAdapter = new MyAppsAdapter());
//        AppInfoProvider.getInstalledPackages(this, 2, true, appInfos -> {
//            if (!appInfos.isEmpty()) {
//                LogUtils.error("已安装: " + appInfos.size());
//                for (AppInfo appInfo : appInfos) {
//                    if (appInfo.isSystemApp) {
//                        systemApps.add(appInfo);
//                    } else userApps.add(appInfo);
//                }
//                nowAllApps.addAll(userApps);
//                //搜索结果, 默认是已安装app
//                searchApps.addAll(userApps);
//
//                LogUtils.error("systemApps: " + systemApps.size());
//                LogUtils.error("userApps: " + userApps.size());
//                viewBinding.bsAppType.setDatas(new String[] {"已安装应用: " + userApps.size(),
//                        "系统应用: " + systemApps.size(),
//                        "全部应用: " + appInfos.size()}
//                );
//                myAdapter.setList(searchApps);
//                viewBinding.swipeRefreshLayout.setRefreshing(false);
//            }
//        });
        AppsInfoUtils.getInstalledPackages(this, 0, appInfos -> {
            if (!appInfos.isEmpty()) {
                LogUtils.error("已安装应用: " + appInfos.size());
                userApps.addAll(appInfos);
                nowAllApps.addAll(userApps);
                //搜索结果, 默认是已安装app
                searchApps.addAll(userApps);

                viewBinding.bsAppType.setDatas(new String[] {"已安装应用: " + userApps.size(),
                        "系统应用: " + systemApps.size(),
                        "全部应用: " + (userApps.size() + systemApps.size())}
                );
                myAdapter.setList(searchApps);
                viewBinding.swipeRefreshLayout.setRefreshing(false);
            }
        });
        AppsInfoUtils.getInstalledPackages(this, 1, appInfos -> {
            if (!appInfos.isEmpty()) {
                LogUtils.error("系统应用: " + appInfos.size());
                systemApps.addAll(appInfos);

                viewBinding.bsAppType.setDatas(new String[] {"已安装应用: " + userApps.size(),
                        "系统应用: " + systemApps.size(),
                        "全部应用: " + (userApps.size() + systemApps.size())}
                );
            }
        });


        new XPopup.Builder(this)
                .asConfirm("提示", "点击Item复制信息, 长按Item打开应用", null)
                .show();
    }

    private void search() {
        int position = viewBinding.bsAppType.getSelectedItemPosition();
        switch (position) {
            case 0:
                //显示已安装应用
                nowAllApps.clear();
                nowAllApps.addAll(userApps);
                break;
            case 1:
                //显示系统应用
                nowAllApps.clear();
                nowAllApps.addAll(systemApps);
                break;
            case 2:
                //显示全部应用
                nowAllApps.clear();
                nowAllApps.addAll(userApps);
                nowAllApps.addAll(systemApps);
                break;
            default:
                return;
        }
        //隐藏键盘
        KeyboardUtils.hideSoftInput(this);
        //显示搜索结果
        String text = getText(viewBinding.itilContent);
        searchApps.clear();
        if (TextUtils.isEmpty(text)) {
            searchApps.addAll(nowAllApps);
        } else {
            text = text.toLowerCase();
            for (AppInfo appInfo : nowAllApps) {
                String appName = appInfo.appName;
                String packageName = appInfo.packageName;
                if (
                        (appName != null && appName.toLowerCase().contains(text)) ||
                        (packageName != null && packageName.toLowerCase().contains(text))
                ) {
                    searchApps.add(appInfo);
                }
            }
        }
        myAdapter.setList(searchApps);
    }

    public void onViewClicked(@NonNull View view) {
        switch (view.getId()) {
            case R.id.btn_search:   //搜索
                search();
                break;
            case R.id.btn_permission_setting:   //读取应用权限设置
                AppsInfoUtils.go2Setting(this);
                break;
            default:
                break;
        }
    }
}
