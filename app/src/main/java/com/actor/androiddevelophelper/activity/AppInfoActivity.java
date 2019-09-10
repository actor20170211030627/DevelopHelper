package com.actor.androiddevelophelper.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.actor.androiddevelophelper.R;
import com.actor.androiddevelophelper.utils.AppInfo;
import com.actor.androiddevelophelper.utils.AppInfoProvider;
import com.blankj.utilcode.util.AppUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Description: 查看app信息
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/8/28 on 22:10
 */
public class AppInfoActivity extends BaseActivity {

    @BindView(R.id.et_content)
    EditText     etContent;
    @BindView(R.id.check_box)
    CheckBox checkBox;
    @BindView(R.id.rv_apps)
    RecyclerView rvApps;
    private              List<AppInfo> allApps          = new ArrayList<>();//全部app
    private              List<AppInfo> searchApps       = new ArrayList<>();//搜索出的app
    private              List<AppInfo> userApps         = new ArrayList<>();//用户app
    private              List<AppInfo> systemApps       = new ArrayList<>();//系统app
    private static final String        forced2SleepName = "com.actor.forced2sleep";
    public static        String        selfPackageName  = "";
    private              MyAppsAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
        ButterKnife.bind(this);

        selfPackageName = getPackageName();
        myAdapter = new MyAppsAdapter();
        rvApps.setAdapter(myAdapter);
        List<AppInfo> appInfos = AppInfoProvider.getAppInfos();
        if (appInfos != null && appInfos.size() > 0) {
            for (AppInfo appInfo : appInfos) {
                if (appInfo.isSystemApp) {
                    systemApps.add(appInfo);
                } else userApps.add(appInfo);
            }
            allApps.addAll(userApps);
            searchApps.addAll(userApps);//搜索结果
            if (rvApps != null) myAdapter.notifyDataSetChanged();
        }
    }

    //adapter
    private class MyAppsAdapter extends RecyclerView.Adapter<MyAppsViewHolder> {
        @Override
        public MyAppsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_app, parent, false);
            return new MyAppsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAppsViewHolder viewHolder, int position) {
            AppInfo appInfo = searchApps.get(position);
            Glide.with(activity).load(appInfo.icon).into(viewHolder.ivIcon);
            viewHolder.tvAppname.setText(appInfo.appName);
            viewHolder.tvPackagename.setText(appInfo.packageName);
            viewHolder.tvVersionname.setText(appInfo.versionName);
            viewHolder.tvVersioncode.setText(appInfo.versionCode + "");
            viewHolder.tvSize.setText(Formatter.formatFileSize(activity, appInfo.size));
            viewHolder.tvSignMd5.setText("Md5: " + appInfo.apkMd5Sign);
            viewHolder.tvSignSha1.setText("Sha1: " + appInfo.apkSha1Sign);
            viewHolder.tvSignSha256.setText("Sha256: " + appInfo.apkSha256Sign);
            viewHolder.itemView.setTag(position);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    AppInfo info = searchApps.get(pos);
                    String clip = getStringFormat("应用名:%s\n包名:%s\n" +
                                    "MD5签名(去掉':'就是证书签名):%s\n版本名称:%s\n版本号:%d\n" +
                                    "大小:%d\n安装路径:%s\nuid:%d\nisSystemApp:%b\nisSdcard:%b\n" +
                                    "Sha1签名:%s\nSha256签名:%s\n",
                            info.appName, info.packageName, info.apkMd5Sign, info.versionName,
                            info.versionCode, info.size, info.apkSourceDir, info.uid,
                            info.isSystemApp, info.isSdcard, info.apkSha1Sign, info.apkSha256Sign);
                    logError(clip);
                    copy2Clipboard(clip);
                    toast("已复制到剪贴板");
                }
            });
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = (int) v.getTag();
                    String packageName = searchApps.get(pos).packageName;
                    if (TextUtils.equals(selfPackageName, packageName)) {
                        toast("自己不能打开自己哟(＾Ｕ＾)ノ~ＹＯ");
                        return true;
                    }
                    if (forced2SleepName.equals(packageName)) {
                        startActivity(new Intent(forced2SleepName + ".startup", Uri.parse(
                                "tobecomegod://起来嗨~")));
                    } else {
                        try {
                            AppUtils.launchApp(packageName);
                        } catch (Exception e) {
                            e.printStackTrace();
                            toast("打开失败:" + e.getMessage());//可能是设置了scheme 或者 其它原因
                        }
                    }
                    return true;
                }
            });
        }

        @Override
        public int getItemCount() {
            return searchApps.size();
        }
    }

    static class MyAppsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_appname)
        TextView  tvAppname;
        @BindView(R.id.tv_packagename)
        TextView  tvPackagename;
        @BindView(R.id.tv_versionname)
        TextView  tvVersionname;
        @BindView(R.id.tv_versioncode)
        TextView  tvVersioncode;
        @BindView(R.id.tv_size)
        TextView  tvSize;
        @BindView(R.id.tv_sign_md5)
        TextView  tvSignMd5;
        @BindView(R.id.tv_sign_sha1)
        TextView  tvSignSha1;
        @BindView(R.id.tv_sign_sha256)
        TextView  tvSignSha256;

        MyAppsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
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
        String trim = etContent.getText().toString().trim();
        search(trim);
    }

    @OnCheckedChanged(R.id.check_box)
    public void onCheckedChanged(CompoundButton buttonView, boolean checked) {
        if (checked) {
            allApps.addAll(systemApps);
        } else allApps.removeAll(systemApps);
        onViewClicked();
    }
}
