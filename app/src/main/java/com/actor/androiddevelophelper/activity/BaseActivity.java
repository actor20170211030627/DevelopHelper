package com.actor.androiddevelophelper.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;

import java.util.Locale;

/**
 * Description: 类的描述
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019-8-28 on 09:48
 *
 * @version 1.0
 */
public class BaseActivity extends AppCompatActivity {

    protected Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
    }

    protected boolean isNoEmpty(EditText... editTexts) {
        for (EditText editText : editTexts) {
            if (TextUtils.isEmpty(getText(editText))) {
                toast(editText.getHint());
                return false;
            }
        }
        return true;
    }

    protected String getText(EditText editText) {
        return editText.getText().toString().trim();
    }

    protected String getStringFormat(String format, Object... args) {
        return String.format(Locale.getDefault(), format, args);
    }

    protected void logError(String msg) {
        Log.e(getClass().getSimpleName(), msg);
    }

    protected void toast(CharSequence msg) {
        ToastUtils.showShort(msg);
    }

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
