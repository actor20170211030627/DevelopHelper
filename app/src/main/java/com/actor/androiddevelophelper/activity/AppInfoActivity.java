package com.actor.androiddevelophelper.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import com.actor.androiddevelophelper.R;
import com.actor.androiddevelophelper.adapter.MyAppsAdapter;
import com.actor.androiddevelophelper.bean.AppInfo;
import com.actor.androiddevelophelper.utils.AppInfoProvider;
import com.actor.myandroidframework.widget.ItemTextInputLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Description: 查看app信息(包括Md5&Sha1&Sha256签名)
 * Author     : 李大发
 * Date       : 2019/8/28 on 22:10
 */
public class AppInfoActivity extends BaseActivity {

    @BindView(R.id.itil_content)
    ItemTextInputLayout itilContent;
    @BindView(R.id.check_box)
    CheckBox            checkBox;
    @BindView(R.id.rv_apps)
    RecyclerView rvApps;
    private              List<AppInfo> allApps          = new ArrayList<>();//全部app
    private              List<AppInfo> searchApps       = new ArrayList<>();//搜索出的app
    private              List<AppInfo> userApps         = new ArrayList<>();//用户app
    private List<AppInfo> systemApps       = new ArrayList<>();//系统app
    private MyAppsAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
        ButterKnife.bind(this);

        setTitle("查看app信息(包括Md5&Sha1&Sha256签名)");
        List<AppInfo> appInfos = AppInfoProvider.getAppInfos();
        if (appInfos != null && appInfos.size() > 0) {
            for (AppInfo appInfo : appInfos) {
                if (appInfo.isSystemApp) {
                    systemApps.add(appInfo);
                } else userApps.add(appInfo);
            }
            allApps.addAll(userApps);
            searchApps.addAll(userApps);//搜索结果
            rvApps.setAdapter(myAdapter = new MyAppsAdapter(allApps));
        }
    }

    private void search(String text) {
        searchApps.clear();
        if (TextUtils.isEmpty(text)) {
            searchApps.addAll(allApps);
        } else {
            text = text.toLowerCase();
            for (AppInfo appInfo : allApps) {
                String appName = appInfo.appName;
                if (appName != null && appName.toLowerCase().contains(text)) {
                    searchApps.add(appInfo);
                }
            }
        }
        myAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btn_search)
    public void onViewClicked() {
        search(getText(itilContent));
    }

    @OnCheckedChanged(R.id.check_box)
    public void onCheckedChanged(CompoundButton buttonView, boolean checked) {
        if (checked) {
            allApps.addAll(systemApps);
        } else allApps.removeAll(systemApps);
        onViewClicked();
    }
}
