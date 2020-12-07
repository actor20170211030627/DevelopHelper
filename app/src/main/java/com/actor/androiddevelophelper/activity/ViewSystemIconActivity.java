package com.actor.androiddevelophelper.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.actor.androiddevelophelper.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description: 查看
 * Author     : 李大发
 * Date       : 2019-8-28 on 10:35
 */
public class ViewSystemIconActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private List<Item> items = new ArrayList<>();
    public static final int id=0x01080000;
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_system_icon);
        ButterKnife.bind(this);

        setTitle("查看系统资源图标");
        resources = getResources();
        for (int i = 0; i < 201; i++) {//152
            int id = ViewSystemIconActivity.id + i;
            try {
                items.add(new Item(id, resources.getResourceEntryName(id), "Id:0x"+Integer.toHexString(0x10800000 + i), null));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        recyclerView.setAdapter(new MyAdapter());
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = getLayoutInflater().inflate(R.layout.item_view_system_icon, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            Item item = items.get(i);
            ImageView iv = myViewHolder.iv;
            Glide.with(activity).load(item.id).fitCenter().into(iv);
            item.iconSize = getStringFormat("大小:%dx%d", iv.getWidth(), iv.getHeight());
            myViewHolder.tvName.setText(item.iconName);
            myViewHolder.tvId.setText(item.iconId);
            myViewHolder.tvSize.setText(item.iconSize);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv;
        TextView tvName;
        TextView tvId;
        TextView tvSize;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            tvName = itemView.findViewById(R.id.tv_name);
            tvId = itemView.findViewById(R.id.tv_id);
            tvSize = itemView.findViewById(R.id.tv_size);
        }
    }

    public class Item {
        int id;
        String iconName;
        String iconId;
        String iconSize;
        Item(int id, String iconName, String iconId, String iconSize) {
            this.id = id;
            this.iconName = iconName;
            this.iconId = iconId;
            this.iconSize = iconSize;
        }
    }
}
