package com.actor.androiddevelophelper.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.actor.androiddevelophelper.Global;
import com.actor.androiddevelophelper.R;
import com.actor.androiddevelophelper.adapter.GithubIssuesAdapter;
import com.actor.androiddevelophelper.info.GithubIssuesInfo;
import com.actor.myandroidframework.utils.okhttputils.BaseCallback;
import com.actor.myandroidframework.utils.okhttputils.MyOkHttpUtils;
import com.blankj.utilcode.util.ShellUtils;
import com.blankj.utilcode.util.ThreadUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * description: 获取 Github Host(用于配置电脑host,解决Github图片等无法访问等问题.)
 *
 * @author : ldf
 * date       : 2020/12/5 on 14:39
 */
public class GithubHostActivity extends BaseActivity {

    @BindView(R.id.tv_1)
    TextView     tv1;
    @BindView(R.id.btn_1)
    Button       btn1;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private GithubIssuesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_host);
        ButterKnife.bind(this);
        setTitle("issues 列表");

//        ping("223.5.5.5");

        logError(tv1.getText());
        logError(btn1.getText());

        recyclerView.setAdapter(mAdapter = new GithubIssuesAdapter());
        MyOkHttpUtils.get(getStringFormat(Global.GITHUB_ISSUES, "ButterAndButterfly", "GithubHost"),
                null, new BaseCallback<List<GithubIssuesInfo>>(this) {
                    @Override
                    public void onOk(@NonNull List<GithubIssuesInfo> info, int requestId, boolean isRefresh) {
                        dismissLoadingDialog();
                        mAdapter.setNewData(info);
                    }
                });
    }

    private void ping(String realIp/*, */) {
//        NetworkUtils.isAvailableByPingAsync();
        ThreadUtils.executeByIo(new ThreadUtils.Task<ShellUtils.CommandResult>() {
            @Override
            public ShellUtils.CommandResult doInBackground() throws Throwable {
                ShellUtils.CommandResult result = ShellUtils.execCmd(String.format("ping -c 3 %s", realIp), false);
                float timeMs = getTimeMs(result);
                return result;
            }

            @Override
            public void onSuccess(ShellUtils.CommandResult result) {
                logError(result);
            }

            @Override
            public void onCancel() {
                logError("取消了");
            }

            @Override
            public void onFail(Throwable t) {
                logError("失败了: " + t.toString());
            }
        });
    }

    /**
     * 从ping中获取最后一次返回的时间, ping -c 3 -w 5 www.baidu.com      //3: ping次数, 5: ping操作超时时间
     * result: 0
     * successMsg: PING 223.5.5.5 (223.5.5.5) 56(84) bytes of data.
     * 64 bytes from 223.5.5.5: icmp_seq=1 ttl=115 time=61.9 ms
     * 64 bytes from 223.5.5.5: icmp_seq=2 ttl=115 time=49.5 ms
     * 64 bytes from 223.5.5.5: icmp_seq=3 ttl=115 time=46.2 ms
     * <p>
     * --- 223.5.5.5 ping statistics ---
     * 3 packets transmitted, 3 received, 0% packet loss, time 2005ms
     * rtt min/avg/max/mdev = 46.266/52.599/61.995/6.779 ms
     * errorMsg:
     */
    private float getTimeMs(ShellUtils.CommandResult result) {
        if (result.result == 0) {
            String successMsg = result.successMsg;
            String[] time = successMsg.split("time=");//47.3 ms
            String[] split = time[time.length - 1].split(" ms");//[47.3, ms]
            return Float.parseFloat(split[0]);
        } else {
            toast("ping失败: " + result.errorMsg);
            return -1F;
        }
    }
}