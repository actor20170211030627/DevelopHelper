package com.actor.develop_helper.adapter;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.actor.develop_helper.Global;
import com.actor.develop_helper.R;
import com.actor.develop_helper.bean.AppInfo;
import com.actor.develop_helper.utils.ClipboardUtils;
import com.actor.myandroidframework.utils.LogUtils;
import com.actor.myandroidframework.utils.TextUtils2;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

/**
 * description: 查看app信息(包括Md5&Sha1&Sha256签名)
 *
 * @author : ldf
 * date       : 2021/4/14 on 22
 * @version 1.0
 */
public class MyAppsAdapter extends BaseQuickAdapter<AppInfo, BaseViewHolder> {

    private String        selfPackageName;

    public MyAppsAdapter(@Nullable List<AppInfo> data) {
        super(R.layout.item_app, data);
        selfPackageName = AppUtils.getAppPackageName();
        //item点击
        setOnItemClickListener((adapter, view, position) -> {
            AppInfo item = getItem(position);
            if (item != null) {
                String clip = TextUtils2.getStringFormat("应用名:%s\n" +
                                "包名:%s\n" +
                                "MD5签名(去掉':'就是证书签名):%s\n" +
                                "版本名称:%s\n" +
                                "版本号:%d\n" +
                                "大小:%d\n" +
                                "安装路径:%s\n" +
                                "uid:%d\n" +
                                "isSystemApp:%b\n" +
                                "isSdcard:%b\n" +
                                "Sha1签名:%s\n" +
                                "Sha256签名:%s\n",
                        item.appName, item.packageName, item.apkMd5Sign, item.versionName,
                        item.versionCode, item.size, item.apkSourceDir, item.uid,
                        item.isSystemApp, item.isSdcard, item.apkSha1Sign, item.apkSha256Sign);
                LogUtils.error(clip);
                ClipboardUtils.copy2Clipboard(clip);
                ToastUtils.showShort("已复制到剪贴板");
            }
        });
        //item长按
        setOnItemLongClickListener((adapter, view, position) -> {
            AppInfo item = getItem(position);
            if (item != null) {
                String packageName = item.packageName;
                if (TextUtils.equals(selfPackageName, packageName)) {
                    ToastUtils.showShort("自己不能打开自己哟(＾Ｕ＾)ノ~ＹＯ");
                } else if (Global.FORCED_2_SLEEP_NAME.equals(packageName)) {
                    getContext().startActivity(new Intent(Global.FORCED_2_SLEEP_NAME + ".startup",
                            Uri.parse("forced2sleep://来自'开发帮助'的消息: 起来嗨~")));
                } else {
                    try {
                        AppUtils.launchApp(packageName);
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastUtils.showShort("打开失败:" + e.getMessage());//可能是设置了scheme 或者 其它原因
                    }
                }
            }
            return true;
        });
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, AppInfo item) {
        ImageView ivIcon = helper.setText(R.id.tv_appname, item.appName)
                .setText(R.id.tv_packagename, item.packageName)
                .setText(R.id.tv_versionname, item.versionName)
                .setText(R.id.tv_versioncode, item.versionCode + "")
                .setText(R.id.tv_size, Formatter.formatFileSize(getContext(), item.size))
                .setText(R.id.tv_sign_md5, "Md5: " + item.apkMd5Sign)
                .setText(R.id.tv_sign_sha1, "Sha1: " + item.apkSha1Sign)
                .setText(R.id.tv_sign_sha256, "Sha256: " + item.apkSha256Sign)
                .getView(R.id.iv_icon);
        Glide.with(ivIcon).load(item.icon).into(ivIcon);
    }
}
