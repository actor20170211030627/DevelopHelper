package com.actor.androiddevelophelper.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.actor.myandroidframework.utils.ConfigUtils;

/**
 * description: 描述
 *
 * @author : 李大发
 * date       : 2021/4/14 on 22
 * @version 1.0
 */
public class ClipboardUtils {

    /**
     * 复制内容到剪切板
     */
    public static void copy2Clipboard(CharSequence copyStr) {
        //获取剪贴板管理器
        ClipboardManager cm = (ClipboardManager) ConfigUtils.APPLICATION.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", copyStr);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }
}
