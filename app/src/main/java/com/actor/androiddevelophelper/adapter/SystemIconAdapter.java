package com.actor.androiddevelophelper.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.actor.androiddevelophelper.R;
import com.actor.androiddevelophelper.bean.SystemIcon;
import com.actor.myandroidframework.utils.TextUtils2;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * description: 查看系统资源图标
 *
 * @author : 李大发
 * date       : 2021/4/14 on 23
 * @version 1.0
 */
public class SystemIconAdapter extends BaseQuickAdapter<SystemIcon, BaseViewHolder> {

    public SystemIconAdapter(@Nullable List<SystemIcon> data) {
        super(R.layout.item_view_system_icon, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SystemIcon item) {
        ImageView iv = helper.setText(R.id.tv_name, item.iconName)
                .setText(R.id.tv_id, item.iconId)
                .getView(R.id.iv);
        Glide.with(iv).load(item.id).into(iv);
        item.iconSize = TextUtils2.getStringFormat("大小:%dx%d", iv.getWidth(), iv.getHeight());
        helper.setText(R.id.tv_size, item.iconSize);
    }
}
