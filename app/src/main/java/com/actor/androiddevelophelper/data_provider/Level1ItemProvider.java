package com.actor.androiddevelophelper.data_provider;

import android.view.View;

import androidx.annotation.NonNull;

import com.actor.androiddevelophelper.R;
import com.actor.androiddevelophelper.adapter.ExpandableItemAdapter;
import com.actor.androiddevelophelper.info.Level1Item;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

/**
 * description: 描述
 * company    :
 * @author    : ldf
 * date       : 2022/2/24 on 18:19
 */
public class Level1ItemProvider extends BaseNodeProvider {

    @Override
    public int getItemViewType() {
        return ExpandableItemAdapter.TYPE_LEVEL_1;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_expandable_lv1;
    }

    @Override
    public void convert(@NonNull BaseViewHolder helper, BaseNode item) {
        // 数据类型需要自己强转
        Level1Item lv1 = (Level1Item) item;
        helper.setText(R.id.tv, lv1.title + ", " + lv1.subTitle)
                .setImageResource(R.id.iv, R.drawable.icon_add_gray_cdcdcd);
    }

    @Override
    public void onClick(@NonNull BaseViewHolder helper, @NonNull View view, BaseNode data, int position) {
        super.onClick(helper, view, data, position);
        getAdapter().expandOrCollapse(position);
    }
}
