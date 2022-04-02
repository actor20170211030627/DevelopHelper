package com.actor.develop_helper.data_provider;

import android.view.View;

import androidx.annotation.NonNull;

import com.actor.develop_helper.R;
import com.actor.develop_helper.adapter.ExpandableItemAdapter;
import com.actor.develop_helper.info.Person;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

/**
 * description: 描述
 * company    :
 * @author    : ldf
 * date       : 2022/2/24 on 18:19
 */
public class Level3PersonProvider extends BaseNodeProvider {

    @Override
    public int getItemViewType() {
        return ExpandableItemAdapter.TYPE_PERSON;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_homemenu_layout;
    }

    @Override
    public void convert(@NonNull BaseViewHolder helper, BaseNode item) {
        // 数据类型需要自己强转
        Person person = (Person) item;
        helper.setText(R.id.tv, person.name + ", " + person.age)
                .setImageResource(R.id.iv, R.drawable.icon_minus_gray_cdcdcd);
    }

    @Override
    public void onClick(@NonNull BaseViewHolder helper, @NonNull View view, BaseNode data, int position) {
        super.onClick(helper, view, data, position);
        ToastUtils.showShort(String.valueOf(position));
    }
}
