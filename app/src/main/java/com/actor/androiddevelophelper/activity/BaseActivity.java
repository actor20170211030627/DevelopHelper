package com.actor.androiddevelophelper.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.actor.myandroidframework.activity.ActorBaseActivity;

/**
 * Description: 类的描述
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019-8-28 on 09:48
 *
 * @version 1.0
 */
public class BaseActivity extends ActorBaseActivity {

    /**
     * 复制内容到剪切板
     */
    protected void copy2Clipboard(String copyStr) {
        //获取剪贴板管理器
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", copyStr);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }
}
