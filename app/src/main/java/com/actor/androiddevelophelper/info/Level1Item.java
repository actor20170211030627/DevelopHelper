package com.actor.androiddevelophelper.info;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luoxw on 2016/8/10.
 */

//public class Level1Item extends AbstractExpandableItem<Person> implements MultiItemEntity {
public class Level1Item extends BaseExpandNode {

    public String title;
    public String subTitle;

    public Level1Item(String title, String subTitle) {
        this.subTitle = subTitle;
        this.title = title;
        // 默认不展开
        setExpanded(false);
    }

//    @Override
//    public int getItemType() {
//        return ExpandableItemAdapter.TYPE_LEVEL_1;
//    }
//
//    @Override
//    public int getLevel() {
//        return getItemType();
//    }



    private final List<BaseNode> childNode = new ArrayList<>();

    public void addSubItem(Person child) {
        childNode.add(child);
    }


    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return childNode;
    }
}