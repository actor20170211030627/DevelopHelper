package com.actor.develop_helper.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.actor.develop_helper.adapter.MyAppsAdapter;
import com.actor.develop_helper.bean.AppInfo;
import com.actor.develop_helper.databinding.ActivityAppInfoBinding;
import com.actor.develop_helper.utils.AppInfoProvider;
import com.actor.myandroidframework.widget.BaseSpinner;
import com.actor.myandroidframework.widget.ItemTextInputLayout;
import com.blankj.utilcode.util.KeyboardUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 查看app信息(包括Md5&Sha1&Sha256签名)
 * Author     : ldf
 * Date       : 2019/8/28 on 22:10
 */
public class AppInfoActivity extends BaseActivity<ActivityAppInfoBinding> {

    private ItemTextInputLayout itilContent;
    private BaseSpinner<String> bsAppType;
    private RecyclerView        rvApps;

    private final List<AppInfo> userApps   = new ArrayList<>();//用户app
    private final List<AppInfo> systemApps = new ArrayList<>();//系统app
    private final List<AppInfo> nowAllApps = new ArrayList<>();//搜索前,目前应该显示的全部app
    private final List<AppInfo> searchApps = new ArrayList<>();//搜索后的app
    private       MyAppsAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("查看app信息(包括Md5&Sha1&Sha256签名)");

        itilContent = viewBinding.itilContent;
        bsAppType = viewBinding.bsAppType;
        rvApps = viewBinding.rvApps;
        List<AppInfo> appInfos = AppInfoProvider.getAppInfos();
        if (appInfos != null && !appInfos.isEmpty()) {
            for (AppInfo appInfo : appInfos) {
                if (appInfo.isSystemApp) {
                    systemApps.add(appInfo);
                } else userApps.add(appInfo);
            }
            nowAllApps.addAll(userApps);
            //搜索结果, 默认是已安装app
            searchApps.addAll(userApps);
            rvApps.setAdapter(myAdapter = new MyAppsAdapter(searchApps));
        }
        //
        bsAppType.setOnItemSelectedListener((parent, view, position, id) -> search());
    }

    private void search() {
        int position = bsAppType.getSelectedItemPosition();
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
        String text = getText(itilContent);
        searchApps.clear();
        if (TextUtils.isEmpty(text)) {
            searchApps.addAll(nowAllApps);
        } else {
            text = text.toLowerCase();
            for (AppInfo appInfo : nowAllApps) {
                String appName = appInfo.appName;
                if (appName != null && appName.toLowerCase().contains(text)) {
                    searchApps.add(appInfo);
                }
            }
        }
        myAdapter.notifyDataSetChanged();
    }

    public void onViewClicked(View view) {
        search();
    }
}
