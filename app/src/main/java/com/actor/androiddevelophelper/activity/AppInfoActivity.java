package com.actor.androiddevelophelper.activity;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.recyclerview.widget.RecyclerView;

import com.actor.androiddevelophelper.R;
import com.actor.androiddevelophelper.adapter.MyAppsAdapter;
import com.actor.androiddevelophelper.bean.AppInfo;
import com.actor.androiddevelophelper.utils.AppInfoProvider;
import com.actor.myandroidframework.widget.BaseSpinner;
import com.actor.myandroidframework.widget.ItemTextInputLayout;
import com.blankj.utilcode.util.KeyboardUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description: 查看app信息(包括Md5&Sha1&Sha256签名)
 * Author     : ldf
 * Date       : 2019/8/28 on 22:10
 */
public class AppInfoActivity extends BaseActivity {

    @BindView(R.id.itil_content)
    ItemTextInputLayout itilContent;
    @BindView(R.id.bs_app_type)
    BaseSpinner<String> bsAppType;
    @BindView(R.id.rv_apps)
    RecyclerView        rvApps;

    private final List<AppInfo> userApps   = new ArrayList<>();//用户app
    private final List<AppInfo> systemApps = new ArrayList<>();//系统app
    private final List<AppInfo> nowAllApps = new ArrayList<>();//搜索前,目前应该显示的全部app
    private final List<AppInfo> searchApps = new ArrayList<>();//搜索后的app
    private       MyAppsAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
        ButterKnife.bind(this);

        setTitle("查看app信息(包括Md5&Sha1&Sha256签名)");
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

    @OnClick(R.id.btn_search)
    public void onViewClicked() {
        search();
    }
}
