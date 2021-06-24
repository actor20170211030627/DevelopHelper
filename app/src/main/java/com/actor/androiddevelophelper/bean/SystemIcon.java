package com.actor.androiddevelophelper.bean;

/**
 * description: 描述
 *
 * @author : ldf
 * date       : 2021/4/14 on 23
 * @version 1.0
 */
public class SystemIcon {

    public int id;
    public String iconName;
    public String iconId;
    public String iconSize;

    public SystemIcon(int id, String iconName, String iconId) {
        this.id = id;
        this.iconName = iconName;
        this.iconId = iconId;
    }
}
