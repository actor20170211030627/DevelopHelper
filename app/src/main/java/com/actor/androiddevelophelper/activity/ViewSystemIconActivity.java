package com.actor.androiddevelophelper.activity;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.actor.androiddevelophelper.adapter.SystemIconAdapter;
import com.actor.androiddevelophelper.bean.SystemIcon;
import com.actor.androiddevelophelper.databinding.ActivityViewSystemIconBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 查看系统资源图标
 * Author     : ldf
 * Date       : 2019-8-28 on 10:35
 */
public class ViewSystemIconActivity extends BaseActivity<ActivityViewSystemIconBinding> {

    private RecyclerView recyclerView;

    private final List<SystemIcon> items = new ArrayList<>();
    public static final int  ID    = 0x01080000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("查看系统资源图标");

        recyclerView = viewBinding.recyclerView;
        Resources resources = getResources();
        for (int i = 0; i < 201; i++) {//152
            int id = ViewSystemIconActivity.ID + i;
            try {
                items.add(new SystemIcon(id, resources.getResourceEntryName(id), "Id:0x"+Integer.toHexString(0x10800000 + i)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        recyclerView.setAdapter(new SystemIconAdapter(items));
    }
}
