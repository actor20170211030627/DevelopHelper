package com.actor.develop_helper.utils;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;

import com.actor.develop_helper.Global;
import com.actor.develop_helper.api.CheckUpdateInfo;
import com.actor.myandroidframework.utils.FileUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.OnDownloadListener;
import com.hjq.http.listener.OnHttpListener;
import com.hjq.http.listener.OnUpdateListener;

import java.io.File;
import java.util.List;

/**
 * Description: 检查更新
 * 1.修改请求地址
 * 2.使用: new CheckUpdateUtils().check(this);
 *
 * Author     : ldf
 * Date       : 2019/10/19 on 14:39
 *
 * @version 1.0
 */
public class CheckUpdateUtils {

    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;

    //check update检查更新
    @RequiresPermission(value = Manifest.permission.REQUEST_INSTALL_PACKAGES)
    public void check(AppCompatActivity activity) {
        EasyHttp.get(activity)
                .api(CheckUpdateInfo.class)
                .request(new OnHttpListener<CheckUpdateInfo>() {
                    @Override
                    public void onHttpSuccess(CheckUpdateInfo result) {
                        List<CheckUpdateInfo.ElementsBean> elements = result.elements;
                        if (elements != null && !elements.isEmpty()) {
                            CheckUpdateInfo.ElementsBean elementsBean = elements.get(0);
                            if (elementsBean != null) {
                                int versionCode = AppUtils.getAppVersionCode();
                                if (versionCode < elementsBean.versionCode) {
                                    showDialog(activity, elementsBean.versionName);
                                }
                            }
                        }
                    }
                    @Override
                    public void onHttpFail(Throwable throwable) {
                    }
                });
    }

    private void showDialog(AppCompatActivity activity, String newVersionName) {
        if (newVersionName == null) newVersionName = "";
        if (alertDialog == null) {
            alertDialog = new AlertDialog.Builder(activity)
                    .setTitle("Update: 有新版本")
                    .setMessage("有新版本: ".concat(newVersionName).concat(", 快更新吧!"))
                    .setPositiveButton("Ok", (dialog, which) -> downloadApk(activity))
                    .setNegativeButton("Cancel", null)
                    .create();
        }
        alertDialog.show();
    }

    private void downloadApk(AppCompatActivity topActivity) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(topActivity);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        }
        progressDialog.show();
        EasyHttp.download(topActivity)
                .file(PathUtils.getInternalAppFilesPath().concat(FileUtils.getFileNameFromUrl(Global.DOWNLOAD_URL)))
//                .api(Global.DOWNLOAD_URL)
                .url(Global.DOWNLOAD_URL)
                .listener(new OnDownloadListener() {
                    @Override
                    public void onDownloadProgressChange(File file, int progress) {
                        progressDialog.setProgress(progress);
                    }
                    @Override
                    public void onDownloadSuccess(File file) {
                        progressDialog.dismiss();
                        AppUtils.installApp(file);
                    }
                    @Override
                    public void onDownloadFail(File file, Throwable throwable) {
                        progressDialog.dismiss();
                        ToastUtils.showShort("下载失败, 请到Github下载.");
                    }
                }).start();
    }
}
