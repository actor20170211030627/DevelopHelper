package com.actor.develop_helper.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.actor.develop_helper.Global;
import com.actor.develop_helper.R;
import com.actor.develop_helper.databinding.ActivityPoint1ApkInstallBinding;
import com.actor.myandroidframework.widget.ItemTextInputLayout;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.GsonUtils;

import java.util.List;
import java.util.Set;

/**
 * description: 安装微信的安装包:
 *              xxx.apk.1
 *              xxx.apk.1.1
 *              xxx.apk.1(1).1
 *              xxx.apk.1(1)(1).1
 *              xxx.apk.1(2)(1).1
 *              xxx.apk.1.1(5)(1).1
 *              xxx.apk.12
 * company    :
 * @author    : ldf
 * date       : 2022/3/24 on 11:25
 */
public class Point1ApkInstallActivity extends BaseActivity<ActivityPoint1ApkInstallBinding> {

    private ItemTextInputLayout itilFileName;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("微信apk.1安装");
        itilFileName = viewBinding.itilFileName;
        setOnClickListeners(R.id.btn_select_file, R.id.btn_install);

        Intent intent = getIntent();
        parseIntent(intent);
        install();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        parseIntent(intent);
        install();
    }

    //解析Intent
    private void parseIntent(Intent intent) {
        uri = null;
        itilFileName.setText("");

        //Intent { act=android.intent.action.VIEW
        //         dat=content://com.tencent.mm.external.fileprovider/external/Android/data/com.tencent.mm/MicroMsg/Download/新建文本文档.apk.1
        //         typ=application/apk.1
        //         flg=0x10000001
        //         pkg=com.actor.develop_helper
        //         cmp=com.actor.develop_helper/.activity.Point1ApkInstallActivity (has extras) }
        logError("intent = " + intent);
        if (intent != null) {
            String action = intent.getAction();
            String type = intent.getType();
            Set<String> categories = intent.getCategories();
            String scheme = intent.getScheme();
            //android.intent.action.VIEW
            logError("action = " + action);
            /**
             * application/apk.1    文件名是: xxx.apk.1
             * application/1        文件名是: xxx.apk.1.1
             * application/1(1)     文件名是: xxx.apk.1.1(1)
             */
            logError("type = " + type);
            //null
            logError("categories=" + GsonUtils.toJson(categories));
            //content
            logError("scheme = " + scheme);

            uri = intent.getData();
            String dataString = intent.getDataString();
            //content://com.tencent.mm.external.fileprovider/external/Android/data/com.tencent.mm/MicroMsg/Download/%E6%96%B0%E5%BB%BA%E6%96%87%E6%9C%AC%E6%96%87%E6%A1%A3.apk.1
            logError("dataString = " + dataString);

            if (uri != null) {
                String host = uri.getHost();
                int port = uri.getPort();
                String scheme1 = uri.getScheme();
                //com.tencent.mm.external.fileprovider
                logError("host = " + host);
                //-1
                logError("port = " + port);
                //content
                logError("scheme1 = " + scheme1);

                String path = uri.getPath();
                List<String> pathSegments = uri.getPathSegments();
                if (pathSegments != null && !pathSegments.isEmpty()) {
                    itilFileName.setText(pathSegments.get(pathSegments.size() - 1));
                }
                String encodedPath = uri.getEncodedPath();
                ///external/Android/data/com.tencent.mm/MicroMsg/Download/新建文本文档.apk.1
                logError("path = " + path);
                //["external","Android","data","com.tencent.mm","MicroMsg","Download","新建文本文档.apk.1"]
                logError("pathSegments = " + GsonUtils.toJson(pathSegments));
                ///external/Android/data/com.tencent.mm/MicroMsg/Download/%E6%96%B0%E5%BB%BA%E6%96%87%E6%9C%AC%E6%96%87%E6%A1%A3.apk.1
                logError("encodedPath = " + encodedPath);
            }
        }
    }

    @Override
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_select_file:
                //选择文件
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                this.startActivityForResult(intent, Global.DP1);
                break;
            case R.id.btn_install:
                //安装
                install();
                break;
            default:
                break;
        }

    }

    private void install() {
        if (uri != null) {
            AppUtils.installApp(uri);
//            onBackPressed();
        } else {
            toast("未收到安装包信息!");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Global.DP1 && resultCode == RESULT_OK && data != null) {
            parseIntent(data);
        }
    }

    @Override
    protected void logError(Object msg) {
        super.logError(msg);
    }
}