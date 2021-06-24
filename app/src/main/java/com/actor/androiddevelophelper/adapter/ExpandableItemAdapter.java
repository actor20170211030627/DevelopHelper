package com.actor.androiddevelophelper.adapter;

import com.actor.androiddevelophelper.R;
import com.actor.androiddevelophelper.info.Level0Item;
import com.actor.androiddevelophelper.info.Level1Item;
import com.actor.androiddevelophelper.info.Person;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Description: HomeFragment主页多级可收缩的Adapter
 * 环境:
 * implementation 'com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.50'
 *
 * Author     : ldf
 * Date       : 2019/11/21 on 16:04
 *
 * @version 1.0
 */
public class ExpandableItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    public static final int TYPE_PERSON = 2;

    public ExpandableItemAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_expandable_lv0);
        addItemType(TYPE_LEVEL_1, R.layout.item_expandable_lv1);
        addItemType(TYPE_PERSON, R.layout.item_homemenu_layout);
        //item点击
        setOnItemClickListener((adapter, view, position) -> {
            MultiItemEntity item = getItem(position);
            if (item instanceof AbstractExpandableItem) {
                int itemType = item.getItemType();
                boolean expanded = ((AbstractExpandableItem) item).isExpanded();
                switch (itemType) {
                    case TYPE_LEVEL_0:
                        if (expanded) {
                            collapse(position);
                        } else {
                            expand(position);
                        }
                        break;
                    case TYPE_LEVEL_1:
                        if (expanded) {
                            collapse(position, true);
                        } else {
                            expand(position, true);
                        }
                        break;
                    default:
                        break;
                }
            } else {//叶子节点, 不能再缩放了
                ToastUtils.showShort(String.valueOf(position));
//              // 先获取到当前 item 的父 positon，再移除自己
//                int positionAtAll = getParentPositionInAll(position);
//                remove(position);
//                if (positionAtAll != -1) {
//                    IExpandable multiItemEntity = (IExpandable) getData().get(positionAtAll);
//                    if (!hasSubItems(multiItemEntity)) {
//                        remove(positionAtAll);
//                    }
//                }
            }
        });
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                Level0Item lv0 = (Level0Item) item;
                helper.setText(R.id.tv, lv0.title + ", " + lv0.subTitle)
                        .setImageResource(R.id.iv, R.drawable.logo);
                break;
            case TYPE_LEVEL_1:
                Level1Item lv1 = (Level1Item) item;
                helper.setText(R.id.tv, lv1.title + ", " + lv1.subTitle)
                        .setImageResource(R.id.iv, R.drawable.icon_add_gray_cdcdcd);
                break;
            case TYPE_PERSON:
                Person person = (Person) item;
                helper.setText(R.id.tv, person.name + ", " + person.age)
                        .setImageResource(R.id.iv, R.drawable.icon_minus_gray_cdcdcd);
                break;
            default:
                break;
        }
    }
}
