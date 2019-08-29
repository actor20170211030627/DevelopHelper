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
import android.widget.ImageView;
import android.widget.TextView;

import com.actor.androiddevelophelper.R;
import com.actor.androiddevelophelper.utils.AppInfo;
import com.actor.androiddevelophelper.utils.AppInfoProvider;
import com.blankj.utilcode.util.AppUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description: 查看app信息
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/8/28 on 22:10
 */
public class AppInfoActivity extends BaseActivity {

    @BindView(R.id.rv_apps)
    RecyclerView rvApps;
    private              List<AppInfo> items                  = new ArrayList<>();
    private static final String        toBecomeGodPackageName = "com.shijing.tobecomegod";
    public static String               selfPackageName = "";
    private MyAppsAdapter              myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
        ButterKnife.bind(this);

        setTitle("获取应用信息");
        selfPackageName = getPackageName();
        myAdapter = new MyAppsAdapter();
        rvApps.setAdapter(myAdapter);
        List<AppInfo> appInfos = AppInfoProvider.getAppInfos();
        if (appInfos != null && appInfos.size() > 0) {
            items.addAll(appInfos);
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
            AppInfo appInfo = items.get(position);
            viewHolder.ivIcon.setImageDrawable(appInfo.icon);
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
                    AppInfo info = items.get(pos);
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
                    String packageName = items.get(pos).packageName;
                    if (TextUtils.equals(selfPackageName, packageName)) {
                        toast("自己不能打开自己哟(＾Ｕ＾)ノ~ＹＯ");
                        return true;
                    }
                    if(toBecomeGodPackageName.equals(packageName)) {
                        startActivity(new Intent(toBecomeGodPackageName + ".startup", Uri.parse("tobecomegod://起来嗨~")));
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
            return items.size();
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
}
