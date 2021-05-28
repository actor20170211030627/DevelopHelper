package com.actor.androiddevelophelper.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.actor.androiddevelophelper.Global;
import com.actor.androiddevelophelper.R;
import com.actor.androiddevelophelper.info.GithubIssueDetailInfo;
import com.actor.androiddevelophelper.utils.ClipboardUtils;
import com.actor.myandroidframework.utils.okhttputils.BaseCallback;
import com.actor.myandroidframework.utils.okhttputils.MyOkHttpUtils;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description: issue 详情
 *
 * @author : 李大发
 * date       : 2020/12/7 on 15:55
 */
public class GithubIssueDetailActivity extends BaseActivity {

    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_user)
    TextView  tvUser;
    @BindView(R.id.tv_url)
    TextView  tvUrl;
    @BindView(R.id.tv_body)
    TextView  tvBody;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_issue_detail);
        ButterKnife.bind(this);

        setTitle("issue 详情");
        int number = getIntent().getIntExtra(Global.NUMBER, -1);

        url = getStringFormat(Global.GITHUB_ISSUE_DETAIL, "ButterAndButterfly", "GithubHost", number);
        tvUrl.setText("Github对应api地址:\n" + url);
        getDetail();
    }

    private void getDetail() {
        MyOkHttpUtils.get(url, null, new BaseCallback<GithubIssueDetailInfo>(this) {
            @Override
            public void onOk(@NonNull GithubIssueDetailInfo info, int requestId, boolean isRefresh) {
                dismissLoadingDialog();
                Glide.with(activity).load(info.user.avatar_url).into(ivAvatar);
                tvUser.setText(info.user.login);
                tvBody.setText(info.body);
            }
        });
    }

    @OnClick(R.id.btn_copy)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_copy://复制到剪贴板
                ClipboardUtils.copy2Clipboard(getStringFormat("# 打开电脑目录: %s, 然后将下方内容添加进去\n%s", Global.HOST_ADDRESS, tvBody.getText()));
                toast("copy复制成功!");
                break;
            default:
                break;
        }
    }
}