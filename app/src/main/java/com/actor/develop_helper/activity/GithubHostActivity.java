package com.actor.develop_helper.activity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.actor.develop_helper.Global;
import com.actor.develop_helper.R;
import com.actor.develop_helper.api.GithubHostFileApi;
import com.actor.develop_helper.databinding.ActivityGithubHostBinding;
import com.actor.develop_helper.utils.ClipboardUtils;
import com.actor.myandroidframework.utils.LogUtils;
import com.actor.myandroidframework.utils.toaster.ToasterUtils;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.OnHttpListener;

/**
 * description: 获取 Github Host(用于配置电脑host,解决Github图片等无法访问等问题.)
 *
 * @author : ldf
 * date       : 2020/12/5 on 14:39
 */
public class GithubHostActivity extends BaseActivity<ActivityGithubHostBinding> {

    private TextView     tv1;
    private Button       btn1;
    private TextView     tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("获取Github Host");

        tv1 = viewBinding.tv1;
        btn1 = viewBinding.btn1;
        tvResult = viewBinding.tvResult;
        LogUtils.error(tv1.getText());
        LogUtils.error(btn1.getText());
        //设置tv内容可滑动
        tvResult.setMovementMethod(ScrollingMovementMethod.getInstance());
        EasyHttp.get(this)
                .api(GithubHostFileApi.class)
                .request(new OnHttpListener<String>() {
                    @Override
                    public void onHttpSuccess(String result) {
                        tvResult.setText(result);
                    }
                    @Override
                    public void onHttpFail(Throwable throwable) {
                    }
                });
    }

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_1://复制到剪贴板
                ClipboardUtils.copy2Clipboard(getStringFormat("# 1.Window电脑打开目录: %s\n" +
                        "# 2.将下方内容添加进去\n%s\n" +
                        "# 3.快捷键win+R, 输入cmd, 输入命令刷新dns: ipconfig /flushdns",
                        Global.HOST_ADDRESS, tvResult.getText()));
                ToasterUtils.success("copy复制成功!");
                break;
            default:
                break;
        }
    }
}