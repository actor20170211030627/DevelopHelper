package com.actor.develop_helper.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.actor.develop_helper.R;
import com.actor.develop_helper.databinding.ActivityFileRenameBinding;
import com.actor.myandroidframework.utils.picture_selector.PictureSelectorUtils;
import com.actor.myandroidframework.widget.ItemTextInputLayout;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.UriUtils;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.io.File;
import java.util.List;

/**
 * description: 文件重命名
 * company    :
 * @author    : ldf
 * date       : 2022/5/6 on 19:41
 */
public class FileRenameActivity extends BaseActivity<ActivityFileRenameBinding> {

    private ItemTextInputLayout itilFileName, itilFileRename;

    private final OnResultCallbackListener<LocalMedia> listener    = new OnResultCallbackListener<LocalMedia>() {
        @Override
        public void onResult(List<LocalMedia> result) {
            LocalMedia localMedia = result.get(0);
            if (localMedia != null) {
                String fileName = localMedia.getFileName();
                if (!TextUtils.isEmpty(fileName)) {
                    String realPath = localMedia.getRealPath();
                    logError(realPath);
                    file = new File(realPath);
                    setFileName(fileName);
                }
            }
        }
        @Override
        public void onCancel() {
        }
    };
    private final int                                  REQUEST_CODE = 100;
    private       Intent                               intent, scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("File重命名");
        intent = new Intent(Intent.ACTION_GET_CONTENT)
                .setType("*/*")
                .addCategory(Intent.CATEGORY_OPENABLE);
        itilFileName = viewBinding.itilFileName;
        itilFileRename = viewBinding.itilFileRename;
    }

    @Override
    public void onViewClicked(View view) {
        super.onViewClicked(view);
        switch (view.getId()) {
            case R.id.btn_select_pic:
                //选择图片
                PictureSelectorUtils.selectImage(this, false, listener);
                break;
            case R.id.btn_select_video:
                //选择视频
                PictureSelectorUtils.selectVideo(this, false, listener);
                break;
            case R.id.btn_select_audio:
                //选择音频
                PictureSelectorUtils.selectAudio(this, 1, null, listener);
                break;
            case R.id.btn_select_others:
                //选择其它音频
//                IntentUtils.getCallIntent();
                XXPermissions.with(this).permission(Permission.Group.STORAGE).request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        startActivityForResult(intent, REQUEST_CODE);
                    }
                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        toast("您拒绝了权限!");
                    }
                });
                break;
            case R.id.btn_re_name:
                //改名
                if (isNoEmpty(itilFileRename)) {
                    String fileName = getText(itilFileName);
                    String rename = getText(itilFileRename);
                    if (rename.equals(fileName)) {
                        toast("您还未给文件改名!");
                        return;
                    }
                    boolean success = FileUtils.rename(file, rename);
                    if (success) {
                        toast("改名成功!");
                        itilFileName.setText(rename);
                        //还是原来的名字...
                        logFormat("改名后文件名称: %s", file.getName());
//                        FileUtils.notifySystemToScan(file);//如果文件不存在就返回false, 调了没用
                        //通知系统扫描删除的文件
                        scanIntent.setData(Uri.parse("file://" + file.getAbsolutePath()));
                        sendBroadcast(scanIntent);
                        //通知系统扫描增加的文件
                        FileUtils.notifySystemToScan(new File(file.getParent(), rename));
                        //扫描父文件夹, 经测试没啥用
//                        FileUtils.notifySystemToScan(file.getParent());

                    } else {
                        toast("改名失败!");
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data == null) {
                toast("data为空!");
                return;
            }
            Uri uri = data.getData();
            if (uri == null) {
                toast("Uri为空!");
                return;
            }
            file = UriUtils.uri2File(uri);
            if (file != null) {
                logError(file.getAbsolutePath());
                setFileName(file.getName());
            }
        }
    }

    private void setFileName(String fileName) {
        itilFileName.setText(fileName);
        itilFileRename.setText(fileName);
    }
}