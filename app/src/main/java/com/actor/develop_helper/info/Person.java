package com.actor.develop_helper.info;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.entity.node.BaseNode;

import java.util.List;

/**
 * Created by luoxw on 2016/8/10.
 */

//public class Person implements MultiItemEntity {
public class Person extends BaseNode {

    public String name;
    public int age;

    public Person(String name, int age) {
        this.age = age;
        this.name = name;
    }

//    @Override
//    public int getItemType() {
//        return ExpandableItemAdapter.TYPE_PERSON;
//    }



    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return null;
    }
}