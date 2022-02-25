package com.actor.androiddevelophelper.adapter;

import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.actor.androiddevelophelper.Global;
import com.actor.androiddevelophelper.R;
import com.actor.myandroidframework.utils.LogUtils;
import com.actor.myandroidframework.utils.TextUtils2;
import com.actor.myandroidframework.utils.okhttputils.GetFileCallback;
import com.actor.myandroidframework.utils.okhttputils.MyOkHttpUtils;
import com.actor.myandroidframework.utils.picture_selector.PictureSelectorUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.io.File;
import java.util.List;

/**
 * description: 描述
 * company    :
 *
 * @author : ldf
 * date       : 2021/11/16 on 12
 * @version 1.0
 */
public class GlideExampleAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private static final String[] TYPES = {"url", "assets", "Resources", "File", "Uri", "byte[]",
            "raw", "raw", "ContentProvider(点击选择本都图片)"};
    private              String   contentProvider;
    private AppCompatActivity     activity;

    public GlideExampleAdapter(AppCompatActivity activity) {
        super(R.layout.item_view_system_icon);
        this.activity = activity;
        for (String type : TYPES) {
            addData(type);
        }
        setOnItemClickListener((adapter, view, position) -> {
            if (position == TYPES.length - 1) {
                PictureSelectorUtils.selectImage(activity, true, new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        //content://media/external/file/122414
                        contentProvider = result.get(0).getPath();
                        String path = TextUtils2.getStringFormat("选择图片, path = %s", contentProvider);
                        LogUtils.error(path);
                        ToastUtils.showShort(path);
                        //刷新最后一个itme
                        notifyItemChanged(position);
                    }
                    @Override
                    public void onCancel() {
                        ToastUtils.showShort("取消选择");
                    }
                });
            }
        });
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        int position = helper.getAdapterPosition();
        ImageView iv = helper.setText(R.id.tv_name, position + "." + item).getView(R.id.iv);
        switch (position) {
            case 0://url网络
                Glide.with(iv).load(Global.BAIDU_LOGO).into(iv);
                break;
            case 1://assets
                Glide.with(iv).load("file:///android_asset/logo.png").into(iv);
                break;
            case 2://Resources
                Glide.with(iv).load(R.mipmap.ic_launcher).into(iv);
                break;
            case 3://File
                MyOkHttpUtils.getFile(Global.BAIDU_LOGO, null, null, new GetFileCallback(activity, null) {
                    @Override
                    public void onOk(@NonNull File info, int requestId, boolean isRefresh) {
                        Glide.with(iv).load(info).into(iv);
                    }
                });
                break;
            case 4://Uri
                Glide.with(iv).load(Uri.parse(Global.BAIDU_LOGO)).into(iv);
                break;
            case 5://byte[]字节数组
                MyOkHttpUtils.getFile(Global.BAIDU_LOGO, null, null, new GetFileCallback(activity, "baidu_jgylogo3(1).gif") {
                    @Override
                    public void onOk(@NonNull File info, int requestId, boolean isRefresh) {
                        byte[] bytes = FileIOUtils.readFile2BytesByStream(info);
                        Glide.with(iv).load(bytes).into(iv);
                    }
                });
                break;
            case 6://raw
                Glide.with(iv).load(TextUtils2.getStringFormat("android.resource://%s/raw/%s", getContext().getPackageName(), "logo")).into(iv);
                break;
            case 7://raw
                Glide.with(iv).load(TextUtils2.getStringFormat("android.resource://%s/raw/%d", getContext().getPackageName(), R.raw.logo)).into(iv);
                break;
            case 8://ContentProvider
                //DownloadManagerUtils 查询下载: content://downloads/my_downloads/xxx
                Glide.with(iv).load(contentProvider).into(iv);
                break;
            default:
                break;
        }
    }
}
