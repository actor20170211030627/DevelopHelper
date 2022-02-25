package com.actor.androiddevelophelper.adapter;

import com.actor.androiddevelophelper.data_provider.Level0ItemProvider;
import com.actor.androiddevelophelper.data_provider.Level1ItemProvider;
import com.actor.androiddevelophelper.data_provider.Level3PersonProvider;
import com.actor.androiddevelophelper.info.Level0Item;
import com.actor.androiddevelophelper.info.Level1Item;
import com.actor.androiddevelophelper.info.Person;
import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.NotNull;

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
//public class ExpandableItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
public class ExpandableItemAdapter extends BaseNodeAdapter {

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    public static final int TYPE_PERSON = 2;

//    public ExpandableItemAdapter(List<MultiItemEntity> data) {
    public ExpandableItemAdapter(List<BaseNode> data) {
        super(data);
//        addItemType(TYPE_LEVEL_0, R.layout.item_expandable_lv0);
//        addItemType(TYPE_LEVEL_1, R.layout.item_expandable_lv1);
//        addItemType(TYPE_PERSON, R.layout.item_homemenu_layout);

        // 注册Provider，总共有如下三种方式
        // 需要占满一行的，使用此方法（例如section）
        addFullSpanNodeProvider(new Level0ItemProvider());
        // 普通的item provider
        addNodeProvider(new Level1ItemProvider());
        addNodeProvider(new Level3PersonProvider());
        // 脚布局的 provider
//        addFooterNodeProvider(new RootFooterNodeProvider());

        //item点击
//        setOnItemClickListener((adapter, view, position) -> {
//            MultiItemEntity item = getItem(position);
//            if (item instanceof AbstractExpandableItem) {
//                int itemType = item.getItemType();
//                boolean expanded = ((AbstractExpandableItem) item).isExpanded();
//                switch (itemType) {
//                    case TYPE_LEVEL_0:
//                        if (expanded) {
//                            collapse(position);
//                        } else {
//                            expand(position);
//                        }
//                        break;
//                    case TYPE_LEVEL_1:
//                        if (expanded) {
//                            collapse(position, true);
//                        } else {
//                            expand(position, true);
//                        }
//                        break;
//                    default:
//                        break;
//                }
//            } else {//叶子节点, 不能再缩放了
//                ToastUtils.showShort(String.valueOf(position));
////              // 先获取到当前 item 的父 positon，再移除自己
////                int positionAtAll = getParentPositionInAll(position);
////                remove(position);
////                if (positionAtAll != -1) {
////                    IExpandable multiItemEntity = (IExpandable) getData().get(positionAtAll);
////                    if (!hasSubItems(multiItemEntity)) {
////                        remove(positionAtAll);
////                    }
////                }
//            }
//        });
    }

//    @Override
//    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
//        switch (helper.getItemViewType()) {
//            case TYPE_LEVEL_0:
//                Level0Item lv0 = (Level0Item) item;
//                helper.setText(R.id.tv, lv0.title + ", " + lv0.subTitle)
//                        .setImageResource(R.id.iv, R.drawable.logo);
//                break;
//            case TYPE_LEVEL_1:
//                Level1Item lv1 = (Level1Item) item;
//                helper.setText(R.id.tv, lv1.title + ", " + lv1.subTitle)
//                        .setImageResource(R.id.iv, R.drawable.icon_add_gray_cdcdcd);
//                break;
//            case TYPE_PERSON:
//                Person person = (Person) item;
//                helper.setText(R.id.tv, person.name + ", " + person.age)
//                        .setImageResource(R.id.iv, R.drawable.icon_minus_gray_cdcdcd);
//                break;
//            default:
//                break;
//        }
//    }

    /**
     * 自行根据数据、位置等信息，返回 item 类型
     */
    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> data, int position) {
        BaseNode node = data.get(position);
        if (node instanceof Level0Item) {
            return TYPE_LEVEL_0;
        } else if (node instanceof Level1Item) {
            return TYPE_LEVEL_1;
        } else if (node instanceof Person) {
            return TYPE_PERSON;
        }
        return -1;
    }
}
